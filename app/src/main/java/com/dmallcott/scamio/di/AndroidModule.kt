package com.dmallcott.scamio.di

import android.app.NotificationManager
import android.content.Context
import com.dmallcott.scamio.ScamIoApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule(private val application: ScamIoApplication) {

    @Provides
    @Singleton
    fun provideApplication(): ScamIoApplication = application

    @Provides
    @Singleton
    fun provideContext(): Context = application.baseContext

    @Provides
    @Singleton
    fun provideNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}