package com.example.newtasklist

import android.app.Application
import androidx.room.Room
import com.example.newtasklist.db.TaskDAO
import com.example.newtasklist.db.TaskDatabase
import com.example.newtasklist.db.TaskRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val viewModelModule = module {
    single { TaskViewModel(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): TaskDatabase {
        return Room.databaseBuilder(application, TaskDatabase::class.java, "eds.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    fun provideDao(database: TaskDatabase): TaskDAO {
        return database.taskDAO
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideUserRepository(dao: TaskDAO): TaskRepository {
        return TaskRepository(dao)
    }

    single { provideUserRepository(get()) }
}