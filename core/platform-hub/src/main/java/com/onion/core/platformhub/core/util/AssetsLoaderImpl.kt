package com.onion.core.platformhub.core.util

import android.content.Context
import com.onion.core.platformhub.policy.model.PolicyModel
import com.onion.core.platformhub.policy.model.PrivacySchemaModel
import java.io.InputStream

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: AssetsLoaderImpl
 * Author: admin by 张琦
 * Date: 2024/6/23 22:54
 * Description:
 */

class AssetsLoaderImpl(private val context: Context): AssetsLoader{
    override fun loadAssetFile(fileName: String) = context.assets.open(fileName)

    override fun loadPolicyFile(policyFileName: String): PolicyModel = loadAssetFile(policyFileName)
        .use { source ->
            objectFromJson(source)
        }

    override fun loadPrivacySchemaFiles(): List<PrivacySchemaModel> =
        context.assets.list(PRIVACY_SCHEMAS)?.map { fileName ->
            loadAssetFile("${PRIVACY_SCHEMAS}/${fileName}").use { source ->
                objectFromJson(source)
            }
        } ?: emptyList()

    companion object{
        private const val PRIVACY_SCHEMAS = "privacySchemas"
    }
}

interface AssetsLoader {

    fun loadAssetFile(fileName: String): InputStream

    fun loadPolicyFile(policyFileName: String): PolicyModel

    fun loadPrivacySchemaFiles(): List<PrivacySchemaModel>

}