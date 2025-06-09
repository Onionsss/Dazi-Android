package com.onion.core.platformhub.policy.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: PrivacySchemaModel
 * Author: admin by 张琦
 * Date: 2024/6/18 0:10
 * Description:
 */
@Serializable
class PrivacySchemaModel(
    val filename: String,
    val privacySchema: PropertyModel.ObjectModel,
    val mode: Mode
) {

    @Serializable
    sealed class Mode{

        @Serializable
        @SerialName("Merge")
        data class Merge(val type: String = "Merge"): Mode()

        @Serializable
        @SerialName("Override")
        data class Override(val privilege: Privilege): Mode()

        enum class Privilege{
            STRENGTHEN_ONLY,STRENGTHEN_AND_WEAKEN
        }
    }

    @Serializable
    sealed class PropertyModel{

        abstract val type: String
        abstract val privacy: Privacy

        @Serializable
        @SerialName("object")
        data class ObjectModel(
            override val type: String = "object",
            override val privacy: Privacy,
            val properties: Map<String, PropertyModel> = mapOf()
        ): PropertyModel()

        @Serializable
        @SerialName("array")
        data class ArrayModel(
            override val type: String = "array",
            override val privacy: Privacy,
            val items: PropertyModel
        ): PropertyModel()

        sealed class PrimitiveModel: PropertyModel(){

            @Serializable
            @SerialName("string")
            data class StringPrimitive(
                override val type: String,
                override val privacy: Privacy
            ): PropertyModel()

            @Serializable
            @SerialName("int")
            data class IntPrimitive(
                override val type: String = "int",
                override val privacy: Privacy
            ): PropertyModel()

            @Serializable
            @SerialName("long")
            data class LongPrimitive(
                override val type: String = "long",
                override val privacy: Privacy
            ): PropertyModel()

            @Serializable
            @SerialName("float")
            data class FloatPrimitive(
                override val type: String = "float",
                override val privacy: Privacy
            ): PropertyModel()

            @Serializable
            @SerialName("double")
            data class DoublePrimitive(
                override val type: String = "double",
                override val privacy: Privacy
            ): PropertyModel()

            @Serializable
            @SerialName("boolean")
            data class BooleanPrimitive(
                override val type: String = "boolean",
                override val privacy: Privacy
            ): PropertyModel()
        }
    }

    enum class Privacy(val priority: Int){
        PUBLIC(0),INTERNAL(1),RESTRICTED(2),HIGHLY_RESTRICTED(3),OMITTED(4)
    }
}