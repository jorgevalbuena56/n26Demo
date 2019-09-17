package com.n26.cache.dao

import android.arch.persistence.room.Room
import com.n26.cache.db.BitCoinChartDatabase
import com.n26.cache.factory.BitCoinChartFactoryMock
import com.n26.cache.model.BitCoinPriceCached
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(RobolectricTestRunner::class)
open class BitCoinChartCachedDaoTest {
    private lateinit var bitCoinChartDatabase: BitCoinChartDatabase

    @Before
    fun initDb() {
        bitCoinChartDatabase = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.baseContext,
            BitCoinChartDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        bitCoinChartDatabase.close()
    }

    @Test
    fun getBitCoinChartCachedEmpty() {
        val bitCoinChartCachedStored = bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinChart()
        assertNull(bitCoinChartCachedStored)
    }

    @Test
    fun getBitCoinPricesCachedEmpty() {
        val bitCoinPriceCached = bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinPrices(15)
        assertEquals(bitCoinPriceCached.size, 0)
    }

    @Test
    fun insertBitCoinChartCachedSavesData() {
        val bitCoinChartCached = BitCoinChartFactoryMock.makeBitCoinChart()
        val id = bitCoinChartDatabase.bitCoinchartCachedDao().insertBitCoinChart(bitCoinChartCached)
        bitCoinChartDatabase.bitCoinchartCachedDao().insertBitCoinPrices(
            bitCoinChartCached.values.map { BitCoinPriceCached(bitCoinChartId = id, x = it.x, y = it.y) })

        val bitCoinChartCachedStored = bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinChart()
        assert(bitCoinChartCachedStored != null)
        val bitCoinPriceCached = bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinPrices(id)
        assert(bitCoinChartCached.values.size == bitCoinPriceCached.size)
    }

    @Test
    fun clearBitCoinChartCached() {
        val bitCoinChartCached = BitCoinChartFactoryMock.makeBitCoinChart()
        val id = bitCoinChartDatabase.bitCoinchartCachedDao().insertBitCoinChart(bitCoinChartCached)
        bitCoinChartDatabase.bitCoinchartCachedDao().insertBitCoinPrices(
            bitCoinChartCached.values.map { BitCoinPriceCached(bitCoinChartId = id, x = it.x, y = it.y) })

        bitCoinChartDatabase.bitCoinchartCachedDao().clearBitCoinChart()

        val bitCoinChartCachedStored = bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinChart()
        assertNull(bitCoinChartCachedStored)
        val bitCoinPriceCached = bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinPrices(id)
        assertEquals(bitCoinPriceCached.size, 0)

    }
}