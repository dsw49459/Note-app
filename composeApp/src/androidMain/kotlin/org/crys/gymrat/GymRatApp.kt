package org.crys.gymrat

import android.app.Application
import com.russhwolf.settings.BuildConfig
import org.crys.gymrat.di.KoinInit
import org.crys.gymrat.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class GymRatApp : Application() {

    override fun onCreate() {
        super.onCreate()

        KoinInit().init {
            androidLogger(level = if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(androidContext = this@GymRatApp)
            modules(
                listOf(
                    androidModule,
                )
            )
        }
    }
}