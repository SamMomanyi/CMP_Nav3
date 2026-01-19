package org.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.example.project.app.presentation.screens.TodoDetailScreen
import org.example.project.app.presentation.screens.TodoListScreen
import org.example.project.nav.Route

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
){
    val backStack = rememberNavBackStack(

        configuration = SavedStateConfiguration{
            serializersModule = SerializersModule {
                polymorphic(
                    NavKey::class
                ){
                    subclass(Route.TodoList::class,Route.TodoList.serializer())
                    subclass(Route.TodoDetail::class,Route.TodoDetail.serializer())
                }
            }
        },
        //intial/Start destination
        Route.TodoList

    )
    NavDisplay(
        backStack = backStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = {key ->
            when(key){
                is Route.TodoList -> {
                    NavEntry(key){
                        TodoListScreen(
                            //we add a backstack which the calls the backstack code with a key and changes the screen
                            onTodoClick = {
                            backStack.add(Route.TodoDetail(it))
                                 }
                        )
                    }
                }
                is Route.TodoDetail -> {
                    NavEntry(key){
                        TodoDetailScreen(
                            todo = key.todo
                        )
                    }
                }
                else -> error("Unknown NavKey : ${key}")
            }
        }
    )
}
