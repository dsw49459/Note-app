package org.crys.gymrat.noteList.model

data class Note(
    val id: Long?,
    val title: String,
    val content: String,
    val colorHex: Long
) {
    companion object {
        private val colors = listOf(RedOrangeHex, RedPinkHex, LightGreenHex, BabyBlueHex, VioletHex)

        fun generateRandomColor() = colors.random()
    }
}
