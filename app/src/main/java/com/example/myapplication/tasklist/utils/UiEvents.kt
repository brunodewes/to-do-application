package com.example.myapplication.tasklist.utils

sealed class UiEvents{

    data object PopBackStack: UiEvents()

    data class Navigate(
        val route: String
    ): UiEvents()

    data class ShowSnackBar(
        val message: String,
        val action: String? = null,
    ): UiEvents()

}