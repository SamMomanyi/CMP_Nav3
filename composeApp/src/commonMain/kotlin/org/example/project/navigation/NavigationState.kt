package org.example.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.example.project.nav.Route

//a navigation state to maintain a couple of states
class NavigationState (
    val startRoute : NavKey,
    //the NavKey we are currently at
    topLevelRoute : MutableState<NavKey>,
    //for every NavKey we have a backStack
    val backStacks : Map<NavKey, NavBackStack<NavKey>>
){
  //  we get the advantages of a mutable state and get the TopLevel as a value itself
    var topLevelRoute by topLevelRoute

    val stacksInUse : List<NavKey>
        get() = if(topLevelRoute == startRoute){
            listOf(startRoute)
            //this helps implement the logic where we navigate back to homepage from another screen
        } else {
            listOf(
                startRoute, topLevelRoute
            )
        }
}

@Composable
fun rememberNavigationState(
    startRoute: NavKey,
    topLevelRoutes : Set<NavKey>
) :  NavigationState {
    //we need to persist the navigation

    val topLevelRoute = rememberSerializable(
        startRoute,
        topLevelRoutes,
        configuration = serializersConfig,
        serializer = MutableStateSerializer(
            PolymorphicSerializer(NavKey::class)
        )
    ){
        mutableStateOf(startRoute)
    }

    //we then need to construct the backStacks for every top level declarations
    val backStacks = topLevelRoutes.associateWith { key ->
        rememberNavBackStack(
            configuration = serializersConfig,
            //the first element to show when we get to a particular screen
            key
        )

    }
    return remember(
        startRoute,topLevelRoutes
    ){
        NavigationState(
            startRoute = startRoute,
            topLevelRoute = topLevelRoute,
            backStacks = backStacks
        )
    }
}

val serializersConfig =   SavedStateConfiguration{
    serializersModule = SerializersModule {
        polymorphic(
            NavKey::class
        ){
            subclass(Route.TodoList::class,Route.TodoList.serializer())
            subclass(Route.TodoDetail::class,Route.TodoDetail.serializer())
            subclass(Route.TodoFavorites::class,Route.TodoFavorites.serializer())
            subclass(Route.Settings::class,Route.Settings.serializer())
        }
    }
}

@Composable
fun NavigationState.toEntries(
    entryProvider :(NavKey) -> NavEntry<NavKey>
): SnapshotStateList<NavEntry<NavKey>> {
    //we also need to pass decoraters
    val decoratedEntries = backStacks.mapValues { (_,stack) ->
        val decorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            rememberViewModelStoreNavEntryDecorator()
        )
        rememberDecoratedNavEntries(
            backStack = stack,
            entryDecorators = decorators,
            entryProvider = entryProvider
        )
    }
    return stacksInUse
        .flatMap {
            decoratedEntries[it] ?: emptyList()
        }
        .toMutableStateList()
}