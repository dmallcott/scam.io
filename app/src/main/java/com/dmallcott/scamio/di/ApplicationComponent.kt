package com.dmallcott.scamio.di

import com.dmallcott.scamio.ScamIoApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: ScamIoApplication)
}