package com.example.myapplication.di

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.navigation.NavigationPerformer
import com.example.myapplication.navigation.NavigationPerformerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped

class NavigationModule {
    @Module
    @InstallIn(FragmentComponent::class)
    class FragmentModule {
        @Provides
        @FragmentScoped
        fun provideNavigationPerformer(activity: Activity): NavigationPerformer {
            return NavigationPerformerImpl(activity as FragmentActivity)
        }
    }
}