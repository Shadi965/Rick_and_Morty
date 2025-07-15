package ru.work_mate.rickandmorty.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ru.work_mate.rickandmorty.ui.screens.character_detail.CharacterDetailScreen
import ru.work_mate.rickandmorty.ui.screens.characters_list.CharacterListScreen

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = CharacterListRoute
    ) {
        composable<CharacterListRoute>(
            enterTransition = {
                scaleIn(
                    initialScale = 1.1f,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f,
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) {
            CharacterListScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(CharacterDetailRoute(characterId))
                }
            )
        }

        composable<CharacterDetailRoute>(
            enterTransition = {
                scaleIn(
                    initialScale = 0.9f,
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            exitTransition = {
                scaleOut(
                    targetScale = 1.1f,
                    animationSpec = tween(300)
                ) + fadeOut(animationSpec = tween(300))
            }
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<CharacterDetailRoute>()
            CharacterDetailScreen(
                characterId = route.characterId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
