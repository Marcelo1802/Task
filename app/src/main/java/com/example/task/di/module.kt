package com.example.task.di

import android.net.ConnectivityManager
import androidx.room.Room
import com.example.task.database.TaskDatabase
import com.example.task.model.TaskViewModel
import com.example.task.model.TasksRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val androidModule = module {
    single {
        androidContext()
            .getSystemService(ConnectivityManager::class.java)
                as ConnectivityManager
    }
}

val appModule = module {
    viewModelOf(::TaskViewModel)
}

val storageModule = module {
    singleOf(::TasksRepository)


    single {
        Room.databaseBuilder(
            androidContext(),
            TaskDatabase::class.java, "task_database"
        ).build()
    }
    single {
        get<TaskDatabase>().taskDao()
    }
}
val listModules =
    listOf(androidModule, appModule, storageModule)

