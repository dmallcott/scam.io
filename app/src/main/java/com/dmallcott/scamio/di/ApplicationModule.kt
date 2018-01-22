package com.dmallcott.scamio.di

import android.app.NotificationManager
import android.content.Context
import com.dmallcott.scamio.FirebaseManager
import com.dmallcott.scamio.LocalNotificationManager
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule() {

    @Provides
    @Singleton
    fun provideLocalNotificationManager(context: Context, notificationManager: NotificationManager):
            LocalNotificationManager = LocalNotificationManager(context, notificationManager)
    @Provides
    @Singleton
    fun provideFirebase() : FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseManager(database: FirebaseDatabase):
            FirebaseManager = FirebaseManager(database)
}