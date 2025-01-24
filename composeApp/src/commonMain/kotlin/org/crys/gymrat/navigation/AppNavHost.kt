package org.crys.gymrat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import org.crys.gymrat.noteDetail.NoteDetailScreen
import org.crys.gymrat.noteList.NoteListScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination =  Destinations.NoteList
    ) {
        composable<Destinations.NoteList> {
            NoteListScreen(
                navController = navController
            )
        }
        composable<Destinations.AddNote> {
            NoteDetailScreen(
                noteId = 15L,
                navController = navController
            )
        }
        composable<Destinations.NoteDetail> { details ->
            val noteId = details.toRoute<Destinations.NoteDetail>().noteId
            NoteDetailScreen(
                noteId = noteId,
                navController = navController
            )
        }
    }
}