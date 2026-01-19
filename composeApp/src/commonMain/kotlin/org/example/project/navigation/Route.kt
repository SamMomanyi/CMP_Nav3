package org.example.project.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {

    //navkey helps us define them as backstacks which we can pass data
    //serialization indicates they hold data
    @Serializable
    data object TodoList : Route,NavKey

    @Serializable
    data class  TodoDetail(val todo : String) : Route,NavKey

}