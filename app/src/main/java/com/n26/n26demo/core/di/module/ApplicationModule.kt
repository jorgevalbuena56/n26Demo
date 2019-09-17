package com.n26.n26demo.core.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.n26.cache.BitCoinChartImpl
import com.n26.cache.PreferencesManager
import com.n26.cache.db.BitCoinChartDatabase
import com.n26.cache.mapper.BitCoinChartEntityMapper
import com.n26.data.executor.JobExecutor
import com.n26.data.executor.PostExecutionThread
import com.n26.data.executor.ThreadExecutor
import com.n26.data.render.interactor.GetBitCoinChart
import com.n26.data.repository.BitCoinChartDataRepository
import com.n26.data.repository.BitCoinChartRepository
import com.n26.data.source.BitCoinChartDataStoreFactory
import com.n26.n26demo.BitCoinChartApplication
import com.n26.n26demo.BuildConfig
import com.n26.n26demo.UIThread
import com.n26.remote.service.BitCoinChartRemoteImpl
import com.n26.remote.service.BitCoinChartService
import com.n26.remote.service.BitCoinChartServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val application: BitCoinChartApplication) {
    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return this.application.applicationContext
    }

    @Provides
    @Singleton
    fun provideThreadExecutor(): ThreadExecutor {
        return JobExecutor()
    }

    @Provides
    @Singleton
    fun providePostExecutionThread(): PostExecutionThread {
        return UIThread()
    }

    @Provides
    @Singleton
    fun providesGetBitCoinChart(bitCoinChartRepository: BitCoinChartRepository,
                                threadExecutor: ThreadExecutor,
                                postExecutionThread: PostExecutionThread) : GetBitCoinChart {
        return GetBitCoinChart(bitCoinChartRepository, threadExecutor, postExecutionThread)
    }

    @Provides
    @Singleton
    fun providesBitCoinChartRepository(dataStoreFactory: BitCoinChartDataStoreFactory)
            : BitCoinChartRepository {
        return BitCoinChartDataRepository(dataStoreFactory)
    }

    @Provides
    @Singleton
    fun providesBitCoinChartDataStoreFactory(cachedDataStore: BitCoinChartImpl,
                                             remoteDataStore: BitCoinChartRemoteImpl): BitCoinChartDataStoreFactory {
        return BitCoinChartDataStoreFactory(cachedDataStore, remoteDataStore)
    }

    @Provides
    @Singleton
    fun providesBitCoinChartRemoteDataStore(service: BitCoinChartService,
                                            entityMapper: com.n26.remote.mapper.BitCoinChartEntityMapper)
            : BitCoinChartRemoteImpl {
        return BitCoinChartRemoteImpl(service, entityMapper)
    }

    @Provides
    @Singleton
    fun providesBitCoinChartRemoteService() : BitCoinChartService {
        return BitCoinChartServiceFactory.makeBitCoinChartService(BuildConfig.DEBUG)
    }

    @Provides
    @Singleton
    fun providesBitCoinChartCachedDataStore(database: BitCoinChartDatabase,
                                            entityMapper: BitCoinChartEntityMapper,
                                            preferencesManager: PreferencesManager) : BitCoinChartImpl {
        return BitCoinChartImpl(database, entityMapper, preferencesManager)
    }

    @Provides
    @Singleton
    fun providesBitCoinChartDatabase(context: Context): BitCoinChartDatabase {
        return Room.databaseBuilder(context,
               BitCoinChartDatabase::class.java, "bitCoinCharts.db")
               .build()
    }

    @Provides
    @Singleton
    fun providesEntityMapper(): BitCoinChartEntityMapper {
        return BitCoinChartEntityMapper()
    }


    @Provides
    @Singleton
    fun providesRemoteEntityMapper(): com.n26.remote.mapper.BitCoinChartEntityMapper {
        return com.n26.remote.mapper.BitCoinChartEntityMapper()
    }
    @Provides
    @Singleton
    fun providesPreferencesManager(context: Context) : PreferencesManager {
        return PreferencesManager(context)
    }
}