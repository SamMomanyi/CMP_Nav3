package org.example.project.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import navigation3_basics.composeapp.generated.resources.Res
import navigation3_basics.composeapp.generated.resources.compose_multiplatform
import org.example.project.Greeting
import org.example.project.navigation.NavigationRoot

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold{innerPadding ->

            NavigationRoot(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}