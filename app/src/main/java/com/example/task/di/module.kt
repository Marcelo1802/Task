package com.example.task.di

import android.net.ConnectivityManager
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

//val androidModule = module {
//    single {
//        androidContext()
//            .getSystemService(ConnectivityManager::class.java)
//                as ConnectivityManager
//    }
//}
//
//val appModule = module {
//    viewModelOf(::TaskViewModel)
//    viewModelOf(::TasksListViewModel)
//    viewModelOf(::SignInViewModel)
//    viewModelOf(::SignUpViewModel)
//    viewModelOf(::AppViewModel)
//    viewModelOf(::NetworkConnectionViewModel)
//}
//
//val storageModule = module {
//    singleOf(::TasksRepository)
//    singleOf(::UsersRepository)
//    singleOf(::FirebaseAuthRepository)
//    single {
//        Room.databaseBuilder(
//            androidContext(),
//            MinhasTarefasDatabase::class.java, "minhas-tarefas.db"
//        ).build()
//    }
//    single {
//        get<MinhasTarefasDatabase>().taskDao()
//    }
//}
//
//}