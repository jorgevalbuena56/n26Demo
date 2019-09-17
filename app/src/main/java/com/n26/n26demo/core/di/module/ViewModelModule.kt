package com.n26.n26demo.core.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.n26.n26demo.core.di.module.viewModel.ViewModelFactory
import com.n26.n26demo.core.di.module.viewModel.ViewModelKey
import com.n26.n26demo.fragment.BitCoinChartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BitCoinChartViewModel::class)
    abstract fun bindsBitCoinChartViewModel(bitCoinChartViewModel: BitCoinChartViewModel): ViewModel
}