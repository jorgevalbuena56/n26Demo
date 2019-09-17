package com.n26.data.source

import com.n26.data.source.BitCoinChartDataStore
import com.n26.data.source.BitCoinChartDataStoreFactory
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class BitCoinChartDataStoreFactoryTest {
    private val bitCoinChartCacheDataStore = mock<BitCoinChartDataStore>()
    private val bitCoinChartRemoteDataStore = mock<BitCoinChartDataStore>()

    private val bitCoinChartDataStoreFactory =
        BitCoinChartDataStoreFactory(bitCoinChartCacheDataStore, bitCoinChartRemoteDataStore)

    //Test retrieve remote data store when there's no cached data
    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubBitCoinChartCacheIsCached(Single.just(false))
        val bitCoinChartDataStore =
            bitCoinChartDataStoreFactory.retrieveDataStore(false)
        assertEquals(bitCoinChartDataStore , bitCoinChartRemoteDataStore)
    }

    //Test retrieve remote data store when there's a cache but is expired
    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubBitCoinChartCacheIsCached(Single.just(true))
        stubBitCoinChartCacheIsExpired(true)
        val bitCoinChartDataStore =
            bitCoinChartDataStoreFactory.retrieveDataStore(true)
        assertEquals(bitCoinChartDataStore, bitCoinChartRemoteDataStore)
    }

    //Test retrieve cached data store when is cached and not expired
    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubBitCoinChartCacheIsCached(Single.just(true))
        stubBitCoinChartCacheIsExpired(false)
        val bitCoinChartDataStore = bitCoinChartDataStoreFactory.retrieveDataStore(true)
        assertEquals(bitCoinChartDataStore , bitCoinChartCacheDataStore)
    }

    // Test to retrieve the remote data store
    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val bitCoinChartDataStore = bitCoinChartDataStoreFactory.retrieveRemoteDataStore()
        assertEquals(bitCoinChartDataStore,  bitCoinChartRemoteDataStore)
    }

    //stub method to mock isCached flag
    private fun stubBitCoinChartCacheIsCached(single: Single<Boolean>) {
        whenever(bitCoinChartCacheDataStore.isCached())
            .thenReturn(single)
    }

    //stub method to mock isExpired flag
    private fun stubBitCoinChartCacheIsExpired(isExpired: Boolean) {
        whenever(bitCoinChartCacheDataStore.isExpired())
            .thenReturn(isExpired)
    }
}