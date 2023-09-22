package com.example.myapplication.navigation

sealed class NavigationModel {
    data object CreateTask : NavigationModel()
    data object NavigateBack : NavigationModel()
}