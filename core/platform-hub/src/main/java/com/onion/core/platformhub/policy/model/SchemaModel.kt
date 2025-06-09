package com.onion.core.platformhub.policy.model

import kotlinx.serialization.Serializable

/**
 * Copyright (C), 2023-2024 Meta
 * FileName: SchemaModel
 * Author: admin by 张琦
 * Date: 2024/6/18 0:07
 * Description:
 */
@Serializable
class SchemaModel(
    val filename: String,
    val schema: PropertyModel.ObjectModel,
) {

    @Serializable
    sealed class PropertyModel{

        abstract val type: String

        @Serializable
        data class ObjectModel(
            override val type: String,
            val properties: Map<String, PropertyModel>,
            val required: List<String>
        ): PropertyModel()

        @Serializable
        data class ArrayModel(
            override val type: String,
            val items: PropertyModel
        ): PropertyModel()

        @Serializable
        data class PrimitiveModel(
            override val type: String
        ): PropertyModel()
    }

}