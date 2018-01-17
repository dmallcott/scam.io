package com.dmallcott.scamio.di

import android.app.NotificationManager
import android.content.Context
import com.dmallcott.scamio.FirebaseManager
import com.dmallcott.scamio.LocalNotificationManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule() {

    @Provides
    @Singleton
    fun provideLocalNotificationManager(context: Context, notificationManager: NotificationManager): LocalNotificationManager {
        return LocalNotificationManager(context, notificationManager)
    }

    @Provides
    @Singleton
    fun provideFirebaseManager(): FirebaseManager = FirebaseManager()
}