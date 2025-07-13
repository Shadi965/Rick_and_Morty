package ru.work_mate.rickandmorty.ui.navigation

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
        composable<CharacterListRoute> {
            CharacterListScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(CharacterDetailRoute(characterId))
                }
            )
        }

        composable<CharacterDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<CharacterDetailRoute>()
            CharacterDetailScreen(
                characterId = route.characterId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

//        composable<FilterRoute> { backStackEntry ->
//            val route = backStackEntry.toRoute<FilterRoute>()
//            FilterScreen(
//                initialFilter = route.currentFilter,
//                onApplyFilter = { filter ->
//                    // Возвращаемся на главный экран и применяем фильтр
//                    navController.popBackStack<CharacterListRoute>(inclusive = false)
//                    // Передаем результат обратно
//                    navController.currentBackStackEntry
//                        ?.savedStateHandle
//                        ?.set("applied_filter", filter)
//                },
//                onBackClick = {
//                    navController.popBackStack()
//                }
//            )
//        }
    }
}
