package org.example.project.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey

@Composable
fun TodoNavigationBar(
    selectedKey : NavKey,
    modifier : Modifier = Modifier,
    onSelectKey : (NavKey) -> Unit,
){
    BottomAppBar(
        modifier = modifier,
    ){
        //for each destination we take the route and the data class NavItem
        TOP_LEVEL_DESTINATIONS.forEach { (route,data) ->
            NavigationBarItem(
                selected = route == selectedKey,
                onClick = {
                    onSelectKey(route)
                },
                icon = {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = data.title
                    )
                },
                label = {
                    Text(data.title)
                },

            )
        }
    }
}