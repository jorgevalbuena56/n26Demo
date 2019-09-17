package com.n26.cache

import android.arch.persistence.room.Room
import com.n26.cache.db.BitCoinChartDatabase
import com.n26.cache.mapper.BitCoinChartEntityMapper
import com.n26.data.render.BitCoinChart
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.io.File
import java.io.FileReader
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class BitCoinChartCacheImplTest {
    private var bitCoinChartDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                BitCoinChartDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    private var entityMapper = BitCoinChartEntityMapper()
    private var preferencesManager = PreferencesManager(RuntimeEnvironment.application)


    private val databaseHelper = BitCoinChartImpl(bitCoinChartDatabase,
        entityMapper, preferencesManager)

    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun getBiCoinChartMock(): BitCoinChart {
        val resourceFile = BitCoinChart::class.java.classLoader.getResource("bitcoinchart.json")
        val f = File(resourceFile!!.toURI())
        val reader = JsonReader(FileReader(f))
        return GsonBuilder().create().fromJson(reader, BitCoinChart::class.java) as BitCoinChart
    }

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearBitCoinChart().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveBitCoinChartCompletes() {
        val bitCoinChartEntity = getBiCoinChartMock()

        val testObserver = databaseHelper.saveBitCoinChart(bitCoinChartEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveBitCoinPricesSavesData() {
        val bitCoinChartEntity = getBiCoinChartMock()

        databaseHelper.saveBitCoinChart(bitCoinChartEntity).test()
        checkNumRowsInPriceTable(1483, 1)
    }

    @Test
    fun getBitCoinChartCompletes() {
        val testObserver = databaseHelper.getBitCoinChart().test()
        testObserver.assertComplete()
    }

    private fun checkNumRowsInPriceTable(expectedRows: Int, id: Long) {
        val numberOfRows = bitCoinChartDatabase.bitCoinchartCachedDao().getBitCoinPrices(id).size
        assertEquals(expectedRows, numberOfRows)
    }
}