package org.example.project.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoListViewModel: ViewModel() {
    private val _todos = MutableStateFlow(
        (1..100).map {
            "Todo ${it}"
        }
    )
    //asStateFlow means it is immutable
    val todos =  _todos.asStateFlow()
}