package org.crys.gymrat.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.benasher44.uuid.uuid4
import org.crys.gymrat.auth.AuthScreen
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
        startDestination =  Destinations.Auth
    ) {
        composable<Destinations.Auth> {
            AuthScreen(navController = navController)
        }
        composable<Destinations.NoteList> {
            NoteListScreen(
                navController = navController
            )
        }
        composable<Destinations.AddNote> {
            NoteDetailScreen(
                noteId = uuid4().toString(),
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