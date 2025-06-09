package com.onion.core.platformhub.core

import com.onion.core.platformhub.core.models.LogInfo
import com.onion.core.platformhub.policy.model.PrivacySchemaModel
import com.onion.core.platformhub.core.util.AssetsLoader
import com.onion.core.platformhub.policy.model.PrivacySchemaModel.Mode.Merge
import com.onion.core.platformhub.policy.model.PrivacySchemaModel.Mode.Privilege.STRENGTHEN_AND_WEAKEN
import com.onion.core.platformhub.policy.model.PrivacySchemaModel.Mode.Privilege.STRENGTHEN_ONLY
import com.onion.core.platformhub.policy.model.PrivacySchemaModel.Privacy
import com.onion.core.platformhub.policy.model.PrivacySchemaModel.PropertyModel.ArrayModel
import com.onion.core.platformhub.policy.model.PrivacySchemaModel.PropertyModel.ObjectModel
import org.json.JSONArray
import org.json.JSONObject

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: Logger
 * Author: admin by 张琦
 * Date: 2024/6/29 14:36
 * Description:
 */
abstract class Logger(assetsLoader: AssetsLoader) {

    abstract val loggerLevel: Privacy
    open var privacySchemas: List<PrivacySchemaModel> = assetsLoader.loadPrivacySchemaFiles()
    abstract fun log(logInfo: LogInfo)

    fun filterPayload(
        json: JSONObject,
        messageName: String,
        pluginName: String,
        payloadType: PayloadType,
        logger: Logger,
        logInfo: LogInfo? = null,
    ): JSONObject {
        val regex = getRegex(messageName, pluginName, payloadType, logInfo)
        val overriddenSchemaModel = logger.privacySchemas.find {
            it.filename.matches(regex.toRegex()) && it.mode is PrivacySchemaModel.Mode.Override
        }?.let {
            Pair(
                (it.mode as PrivacySchemaModel.Mode.Override).privilege,
                it.privacySchema
            )
        }
        return privacySchemas
            .find { it.filename.matches(regex.toRegex()) }
            ?.let {
                filterObject(
                    JSONObject(json.toString()),
                    it.privacySchema,
                    logger.loggerLevel,
                    overriddenSchemaModel
                )
            }
            ?: JSONObject()
    }
    private fun filterObject(
        json: JSONObject,
        mergedObjectModel: ObjectModel,
        loggerLevel: Privacy,
        overriddenObjectModel: Pair<PrivacySchemaModel.Mode.Privilege, ObjectModel>? = null
    ): JSONObject {
        val strengthenOverriddenCondition =
            overriddenObjectModel?.first == STRENGTHEN_ONLY && overriddenObjectModel.second.privacy.priority <= loggerLevel.priority
        val weakenOverriddenCondition =
            overriddenObjectModel?.first == STRENGTHEN_AND_WEAKEN && overriddenObjectModel.second.privacy.priority <= loggerLevel.priority
        val baseCondition = mergedObjectModel.privacy.priority <= loggerLevel.priority
        val propsToRemove = mutableListOf<String>()

        return if (strengthenOverriddenCondition && baseCondition || weakenOverriddenCondition || baseCondition) {
            json.keys().forEach { prop ->
                val propModel = mergedObjectModel.properties[prop]!!
                val overriddenPropModel =
                    overriddenObjectModel?.second?.properties?.get(prop)?.let { Pair(overriddenObjectModel.first, it) }
                val propStrengthenOverriddenCondition =
                    overriddenPropModel?.first == STRENGTHEN_ONLY && overriddenObjectModel.second.privacy.priority > loggerLevel.priority
                val propWeakenOverriddenCondition =
                    overriddenPropModel?.first == STRENGTHEN_AND_WEAKEN && overriddenObjectModel.second.privacy.priority > loggerLevel.priority
                val propBaseCondition = propModel.privacy.priority > loggerLevel.priority
                if (propStrengthenOverriddenCondition && propBaseCondition || propWeakenOverriddenCondition || propBaseCondition) {
                    propsToRemove.add(prop)
                } else {
                    when (propModel) {
                        is ArrayModel -> filterList(json.getJSONArray(prop), propModel, loggerLevel)
                        is ObjectModel -> filterObject(json.getJSONObject(prop), propModel, loggerLevel)
                        else -> {}
                    }
                }
            }
            propsToRemove.forEach { json.remove(it) }
            json
        } else {
            JSONObject()
        }
    }

    private fun filterList(
        json: JSONArray,
        mergeArrayModel: ArrayModel,
        loggerLevel: Privacy,
        overriddenArrayModel: Pair<PrivacySchemaModel.Mode.Privilege, ArrayModel>? = null
    ): JSONArray {
        val strengthenOverriddenCondition =
            overriddenArrayModel?.first == STRENGTHEN_ONLY && overriddenArrayModel.second.privacy.priority > loggerLevel.priority
        val weakenOverriddenCondition =
            overriddenArrayModel?.first == STRENGTHEN_AND_WEAKEN && overriddenArrayModel.second.privacy.priority > loggerLevel.priority
        val baseCondition = mergeArrayModel.privacy.priority > loggerLevel.priority
        var i = 0
        while (i < json.length()) {
            val item = json.get(i)
            if (strengthenOverriddenCondition && baseCondition || weakenOverriddenCondition || baseCondition) {
                json.remove(i)
            } else {
                when (mergeArrayModel.items) {
                    is ArrayModel -> filterList(item as JSONArray, mergeArrayModel.items as ArrayModel, loggerLevel)
                    is ObjectModel -> filterObject(item as JSONObject, mergeArrayModel.items as ObjectModel, loggerLevel)
                    else -> {}
                }
                i++
            }
        }
        return json
    }

    enum class PayloadType {
        InputPayload, OutputPayload
    }

    fun mergeSchemas(privacySchemas: List<PrivacySchemaModel>) =
        privacySchemas.groupBy { it.filename }
            .map {
                if (it.value.size == 1) {
                    it.value.first()
                } else {
                    PrivacySchemaModel(
                        filename = it.key,
                        privacySchema = mergeObjectModel(it.value.map { schema -> schema.privacySchema }),
                        mode = Merge()
                    )
                }
            }
            .toMutableList().also { this.privacySchemas = it }

    private fun mergeObjectModel(objectModels: List<ObjectModel>): ObjectModel =
        ObjectModel(
            type = objectModels.first().type,
            privacy = objectModels.maxBy { it.privacy.priority }.privacy,
            properties = objectModels
                .asSequence()
                .map { obj -> obj.properties.toList() }
                .flatten()
                .groupBy { it.first }
                .map { props ->
                    Pair(
                        props.key,
                        when (props.value.first().second) {
                            is ObjectModel -> mergeObjectModel(props.value.map { it.second as ObjectModel })
                            is ArrayModel -> mergeListModel(
                                props.value.map { (it.second as ArrayModel).items },
                                props.value.map { it.second as ArrayModel }.maxBy { it.privacy }.privacy
                            )
                            is PrivacySchemaModel.PropertyModel.PrimitiveModel -> props.value.maxBy { it.second.privacy.priority }.second
                            else -> {
                                props.value.maxBy { it.second.privacy.priority }.second //TODO
                            }
                        }
                    )
                }
                .associate { it }
        )

    private fun mergeListModel(listModels: List<PrivacySchemaModel.PropertyModel>, privacy: Privacy): ArrayModel =
        ArrayModel(
            type = ARRAY,
            privacy = privacy,
            items = when (listModels.first()) {
                is ObjectModel -> mergeObjectModel(listModels.map { it as ObjectModel })
                is ArrayModel -> mergeListModel(
                    listModels.map { (it as ArrayModel).items },
                    listModels.map { it as ArrayModel }.maxBy { it.privacy }.privacy
                )

                is PrivacySchemaModel.PropertyModel.PrimitiveModel -> listModels.maxBy { it.privacy.priority }
                else -> {
                    listModels.maxBy { it.privacy.priority } //TODO
                }
            }
        )
    private fun getRegex(
        messageName: String,
        pluginName: String,
        payloadType: PayloadType,
        logInfo: LogInfo?
    ): String {
        return if (logInfo is LogInfo.InternalPluginAction) {
            "${logInfo.screenName}(\\.\\w+)*\\.$JSON_SCHEMA"
        } else "${pluginName}(\\.\\w+)*\\.${messageName}\\.${payloadType.name}.$JSON_SCHEMA"
    }

    private companion object {
        const val ARRAY = "array"
        const val JSON_SCHEMA = "PrivacySchema.json"
    }
}