package com.n26.remote

import com.n26.remote.factory.BitCoinChartFactoryMock
import com.n26.remote.mapper.BitCoinChartEntityMapper
import com.n26.remote.model.BitCoinChartModel
import com.n26.remote.service.BitCoinChartRemoteImpl
import com.n26.remote.service.BitCoinChartService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Test

class BitCoinRemoteImplTest {
    private val bitCoinChartService = mock<BitCoinChartService>()
    private val entityMapper = BitCoinChartEntityMapper()

    private val bitCoinChartRemoteImpl = BitCoinChartRemoteImpl(bitCoinChartService, entityMapper)

    //Test that the bit coin chart data method from remote completes
    @Test
    fun getBitCoinChartCompletes() {
        stubBitCoinChartServiceGetBitCoinChart(Flowable.just(BitCoinChartFactoryMock.makeBitCoinChart()))
        val testObserver = bitCoinChartRemoteImpl.getBitCoinChart().test()
        testObserver.assertComplete()
    }

    //Test that the bit coin chart data method from remote returns information
    @Test
    fun getBitCoinChartReturnsData() {
        val bitCoinChartResponse = BitCoinChartFactoryMock.makeBitCoinChart()
        stubBitCoinChartServiceGetBitCoinChart(Flowable.just(bitCoinChartResponse))
        val bitCoinChart = entityMapper.mapFromRemote(bitCoinChartResponse)

        val testObserver = bitCoinChartRemoteImpl.getBitCoinChart().test()
        testObserver.assertValue(bitCoinChart)
    }

    private fun stubBitCoinChartServiceGetBitCoinChart(observable:
                                                      Flowable<BitCoinChartModel>
    ) {
        whenever(bitCoinChartService.getBitCoinChart())
            .thenReturn(observable)
    }
}