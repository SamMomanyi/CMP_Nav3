package org.example.project.navigation

import androidx.navigation3.runtime.NavKey

//this explains how we navigate
class Navigator(val state: NavigationState) {

    fun navigate(route : NavKey){
        //if the navigation is a top level nav we add it to the topLevelroute
        if(route in state.backStacks.keys){
            state.topLevelRoute = route
        }
        //else e.g favoriteDetailScreen, we push the route on top of it

        else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack(){
        val currentStack = state.backStacks[state.topLevelRoute] ?: error("Back stack for ${state.topLevelRoute} doesn't exist ")
        //the specific screen we are in
        val currentRoute = currentStack.last()

        if(currentRoute == state.topLevelRoute){
            state.topLevelRoute = state.startRoute
            //we pop the currentScreen from the backStack
        } else {
            currentStack.removeLastOrNull()
        }
    }
}