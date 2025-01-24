package org.crys.gymrat.api

import kotlinx.serialization.Serializable

@Serializable
data class DeleteNoteRequest(
    val id: String
)