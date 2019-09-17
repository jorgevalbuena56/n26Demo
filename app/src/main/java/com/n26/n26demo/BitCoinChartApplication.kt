package com.n26.n26demo

import android.app.Application
import com.n26.n26demo.core.di.component.ApplicationComponent
import com.n26.n26demo.core.di.component.DaggerApplicationComponent
import com.n26.n26demo.core.di.module.ApplicationModule

class BitCoinChartApplication: Application() {
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}