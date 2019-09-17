package com.n26.data.render

import com.n26.data.executor.PostExecutionThread
import com.n26.data.executor.ThreadExecutor
import com.n26.data.factory.BitCoinChartFactoryMock
import com.n26.data.render.interactor.GetBitCoinChart
import com.n26.data.repository.BitCoinChartRepository
import com.n26.data.render.BitCoinChart
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import org.junit.Test

class GetBitCoinChartTest {

    private val mockThreadExecutor = mock<ThreadExecutor>()
    private val mockPostExecutionThread = mock<PostExecutionThread>()
    private val mockBitcoinChartRepository = mock<BitCoinChartRepository>()

    private val getBitcoinChart = GetBitCoinChart(mockBitcoinChartRepository, mockThreadExecutor,
        mockPostExecutionThread)

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getBitcoinChart.buildUseCaseObservable(null)
        verify(mockBitcoinChartRepository).getBitCoinChart()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubBitcoinChartRepositoryGetBitcoinChart(Flowable.just(BitCoinChartFactoryMock.makeBitCoinChart()))
        val testObserver = getBitcoinChart.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val bitcoinChart = BitCoinChartFactoryMock.makeBitCoinChart()
        stubBitcoinChartRepositoryGetBitcoinChart(Flowable.just(bitcoinChart))
        val testObserver = getBitcoinChart.buildUseCaseObservable(null).test()
        testObserver.assertValue(bitcoinChart)
    }

    private fun stubBitcoinChartRepositoryGetBitcoinChart(single: Flowable<BitCoinChart>) {
        whenever(mockBitcoinChartRepository.getBitCoinChart())
            .thenReturn(single)
    }
}