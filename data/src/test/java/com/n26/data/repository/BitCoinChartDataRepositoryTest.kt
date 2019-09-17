package com.n26.data.repository

import com.n26.data.factory.BitCoinChartFactoryMock
import com.n26.data.render.BitCoinChart
import com.n26.data.source.BitCoinChartDataStore
import com.n26.data.source.BitCoinChartDataStoreFactory
import com.n26.data.repository.BitCoinChartDataRepository
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BitCoinChartDataRepositoryTest {
    private val bitCoinChartCacheDataStore = mock<BitCoinChartDataStore>()
    private val bitCoinChartRemoteDataStore = mock<BitCoinChartDataStore>()
    private val bitCoinChartDataStoreFactory = mock<BitCoinChartDataStoreFactory>()

    private val bitCoinChartDataRepository = BitCoinChartDataRepository(bitCoinChartDataStoreFactory)

    @Before
    fun setUp() {
        stubBitCoinChartDataStoreFactoryRetrieveCacheDataStore()
        stubBitCoinChartDataStoreFactoryRetrieveRemoteDataStore()
    }

    //Test the bit coin chart completable completes after calling the clear method
    @Test
    fun clearBitCoinChartCompletes() {
        stubBitCoinChartCacheClearBitCoinChart(Completable.complete())
        val testObserver = bitCoinChartDataRepository.clearBitCoinChart().test()
        testObserver.assertComplete()
    }

    //Test that the cached store executes the clear method
    @Test
    fun clearBitCoinChartCallsCacheDataStore() {
        stubBitCoinChartCacheClearBitCoinChart(Completable.complete())
        bitCoinChartDataRepository.clearBitCoinChart().test()
        verify(bitCoinChartCacheDataStore).clearBitCoinChart()
    }

    //Test that the clear method is never executed in the remote data store
    @Test
    fun clearBitCoinChartNeverCallsRemoteDataStore() {
        stubBitCoinChartCacheClearBitCoinChart(Completable.complete())
        bitCoinChartDataRepository.clearBitCoinChart().test()
        verify(bitCoinChartRemoteDataStore, never()).clearBitCoinChart()
    }

    // Test saving the bit coin chart data completable completes
    @Test
    fun saveBitCoinChartCompletes() {
        stubBitCoinChartCacheSaveBitCoinChart(Completable.complete())
        val testObserver = bitCoinChartDataRepository.saveBitCoinChart(
            BitCoinChartFactoryMock.makeBitCoinChart()).test()
        testObserver.assertComplete()
    }

    //Test that the saving bit coin chart data is done in the local data store
    @Test
    fun saveBitCoinChartCallsCacheDataStore() {
        stubBitCoinChartCacheSaveBitCoinChart(Completable.complete())
        bitCoinChartDataRepository.saveBitCoinChart(BitCoinChartFactoryMock.makeBitCoinChart()).test()
        verify(bitCoinChartCacheDataStore).saveBitCoinChart(any())
    }

    //Test that the saving bit coin chart data method is never executed in the remote data store
    @Test
    fun saveBitCoinChartNeverCallsRemoteDataStore() {
        stubBitCoinChartCacheSaveBitCoinChart(Completable.complete())
        bitCoinChartDataRepository.saveBitCoinChart(BitCoinChartFactoryMock.makeBitCoinChart()).test()
        verify(bitCoinChartRemoteDataStore, never()).saveBitCoinChart(any())
    }

    //Test that the get bit coin chart data method completes
    @Test
    fun getBitCoinChartCompletes() {
        stubBitCoinChartCacheDataStoreIsCached(Single.just(true))
        stubBitCoinChartDataStoreFactoryRetrieveDataStore(bitCoinChartCacheDataStore)
        stubBitCoinChartCacheDataStoreGetBitCoinChart(Flowable.just(
            BitCoinChartFactoryMock.makeBitCoinChart()))
        stubBitCoinChartCacheSaveBitCoinChart(Completable.complete())
        val testObserver = bitCoinChartDataRepository.getBitCoinChart().test()
        testObserver.assertComplete()
    }

    // Test the getter bit coin chart data returns valid data
    @Test
    fun getBitCoinChartReturnsData() {
        stubBitCoinChartCacheDataStoreIsCached(Single.just(true))
        stubBitCoinChartDataStoreFactoryRetrieveDataStore(bitCoinChartCacheDataStore)
        stubBitCoinChartCacheSaveBitCoinChart(Completable.complete())
        val bitCoinchart = BitCoinChartFactoryMock.makeBitCoinChart()
        stubBitCoinChartCacheDataStoreGetBitCoinChart(Flowable.just(bitCoinchart))

        val testObserver = bitCoinChartDataRepository.getBitCoinChart().test()
        testObserver.assertValue(bitCoinchart)
    }

    // Test the saving using the local data store
    @Test
    fun getBitCoinChartSavedWhenFromCacheDataStore() {
        stubBitCoinChartDataStoreFactoryRetrieveDataStore(bitCoinChartCacheDataStore)
        stubBitCoinChartCacheSaveBitCoinChart(Completable.complete())
        bitCoinChartDataRepository.saveBitCoinChart(BitCoinChartFactoryMock.makeBitCoinChart()).test()
        verify(bitCoinChartCacheDataStore).saveBitCoinChart(any())
    }

    //Test saving is never done using the remote data store
    @Test
    fun getBitCoinChartNeverSavesBitCoinChartWhenFromRemoteDataStore() {
        stubBitCoinChartDataStoreFactoryRetrieveDataStore(bitCoinChartRemoteDataStore)
        stubBitCoinChartCacheSaveBitCoinChart(Completable.complete())
        bitCoinChartDataRepository.saveBitCoinChart(BitCoinChartFactoryMock.makeBitCoinChart()).test()
        verify(bitCoinChartRemoteDataStore, never()).saveBitCoinChart(any())
    }


    //Stub helper methods
    private fun stubBitCoinChartCacheSaveBitCoinChart(completable: Completable) {
        whenever(bitCoinChartCacheDataStore.saveBitCoinChart(any()))
            .thenReturn(completable)
    }

    private fun stubBitCoinChartCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(bitCoinChartCacheDataStore.isCached())
            .thenReturn(single)
    }

    private fun stubBitCoinChartCacheDataStoreGetBitCoinChart(single: Flowable<BitCoinChart>) {
        whenever(bitCoinChartCacheDataStore.getBitCoinChart())
            .thenReturn(single)
    }

    private fun stubBitCoinChartCacheClearBitCoinChart(completable: Completable) {
        whenever(bitCoinChartCacheDataStore.clearBitCoinChart())
            .thenReturn(completable)
    }

    private fun stubBitCoinChartDataStoreFactoryRetrieveCacheDataStore() {
        whenever(bitCoinChartDataStoreFactory.retrieveCacheDataStore())
            .thenReturn(bitCoinChartCacheDataStore)
    }

    private fun stubBitCoinChartDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(bitCoinChartDataStoreFactory.retrieveRemoteDataStore())
            .thenReturn(bitCoinChartCacheDataStore)
    }

    private fun stubBitCoinChartDataStoreFactoryRetrieveDataStore(dataStore: BitCoinChartDataStore) {
        whenever(bitCoinChartDataStoreFactory.retrieveDataStore(any()))
            .thenReturn(dataStore)
    }
}