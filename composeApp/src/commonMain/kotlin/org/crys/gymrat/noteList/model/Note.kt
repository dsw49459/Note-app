package org.crys.gymrat.noteList.model

import kotlinx.serialization.Serializable
import org.crys.gymrat.db.NoteEntity

@Serializable
data class Note(
    val id: String,
    val title: String,
    val content: String,
    val color: Long,
    val owner: String,
    val isSynchronized: Boolean = false
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random()
    }
}

fun Note.toEntity(): NoteEntity = NoteEntity(
    id = id,
    title = title,
    content = content,
    color = color,
    isSynchronized = isSynchronized
)

fun NoteEntity.toNote(owner: String): Note = Note(
    id = id,
    title = title,
    content = content,
    color = color,
    owner = owner,
    isSynchronized = isSynchronized
)