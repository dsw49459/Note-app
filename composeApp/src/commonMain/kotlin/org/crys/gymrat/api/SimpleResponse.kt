package org.crys.gymrat.api

import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
    val successful: Boolean,
    val message: String
)