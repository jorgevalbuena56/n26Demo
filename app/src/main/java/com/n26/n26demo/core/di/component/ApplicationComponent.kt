package com.n26.n26demo.core.di.component

import android.app.Application
import com.n26.n26demo.MainActivity
import com.n26.n26demo.core.di.module.ApplicationModule
import com.n26.n26demo.core.di.module.ViewModelModule
import com.n26.n26demo.fragment.BitCoinChartFragment
import dagger.Component
import javax.inject.Singleton

@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: Application)
    fun inject(mainActivity: MainActivity)

    fun inject(bitCoinChartFragment: BitCoinChartFragment)
}