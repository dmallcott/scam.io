package com.dmallcott.scamio

import android.app.Application
import com.dmallcott.scamio.di.ApplicationComponent
import com.dmallcott.scamio.di.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree



class ScamIoApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder().build()
    }

    override fun onCreate() {
        super.onCreate()

        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}