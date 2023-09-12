package com.example.myapplication.di

import com.example.myapplication.repository.TaskRepository
import com.example.myapplication.repository.TaskRepositoryImpl
import com.example.myapplication.tasklist.mapper.TaskItemUiStateMapper
import com.example.myapplication.tasklist.mapper.TaskItemUiStateMapperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskRepository(): TaskRepository {
        return TaskRepositoryImpl()
    }

    @Provides
    fun provideTaskUiStateMapper(): TaskItemUiStateMapper {
        return TaskItemUiStateMapperImpl()
    }
}