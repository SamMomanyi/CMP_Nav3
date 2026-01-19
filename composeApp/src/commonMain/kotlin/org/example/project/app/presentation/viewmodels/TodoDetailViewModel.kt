package org.example.project.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoDetailViewModel(
    //with navigation three we pass the arguments(for the state) as a constructor parameter
    private val todo : String
): ViewModel() {

    private val _state = MutableStateFlow(TodoDetailState(todo))
    //asStateFlow means it is immutable
    val state =  _state.asStateFlow()

    init{
        println("TodoDetailViewModel initialized for ${todo}")
    }

    override fun onCleared() {
        super.onCleared()
        println("TodoViewModel cleared for ${todo}")
    }
}


data class TodoDetailState(
    val todo : String
)