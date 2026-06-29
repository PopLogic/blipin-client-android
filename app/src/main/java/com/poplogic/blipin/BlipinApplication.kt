package com.poplogic.blipin

import android.app.Application
import com.poplogic.blipin.di.onboardModule
import com.poplogic.blipin.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BlipinApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BlipinApplication)
            modules(onboardModule, profileModule)
        }
    }
}
