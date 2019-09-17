package com.n26.data.render.interactor

import com.n26.data.executor.PostExecutionThread
import com.n26.data.executor.ThreadExecutor
import com.n26.data.interactor.FlowableUseCase
import com.n26.data.render.BitCoinChart
import com.n26.data.repository.BitCoinChartRepository
import io.reactivex.Flowable

open class GetBitCoinChart(
    private val bitCoinChartRepository: BitCoinChartRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) :
    FlowableUseCase<BitCoinChart, Void?>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Void?): Flowable<BitCoinChart> {
       return bitCoinChartRepository.getBitCoinChart()
    }
}