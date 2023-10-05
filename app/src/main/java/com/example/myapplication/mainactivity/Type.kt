package com.example.myapplication.mainactivity

sealed class Type {
    data object AddFragment : Type()
    data object NoOp : Type()
}
