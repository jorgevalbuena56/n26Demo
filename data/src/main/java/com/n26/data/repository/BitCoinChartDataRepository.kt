package com.n26.data.repository

import com.n26.data.render.BitCoinChart
import com.n26.data.source.BitCoinChartDataStoreFactory
import io.reactivex.Completable
import io.reactivex.Flowable

class BitCoinChartDataRepository(private val bitCoinChartDataStoreFactory: BitCoinChartDataStoreFactory):
    BitCoinChartRepository {
    override fun clearBitCoinChart(): Completable {
        return bitCoinChartDataStoreFactory.retrieveCacheDataStore().clearBitCoinChart()
    }

    override fun saveBitCoinChart(bitCoinChart: BitCoinChart): Completable {
        return bitCoinChartDataStoreFactory.retrieveCacheDataStore().saveBitCoinChart(bitCoinChart)
    }

    override fun getBitCoinChart(): Flowable<BitCoinChart> {
        return bitCoinChartDataStoreFactory.retrieveCacheDataStore().isCached()
            .flatMapPublisher { isCached ->
                if (!isCached) {
                    bitCoinChartDataStoreFactory.retrieveDataStore(isCached).getBitCoinChart().flatMap {
                        saveBitCoinChart(it).toSingle { it }.toFlowable()
                    }
                } else {
                    bitCoinChartDataStoreFactory.retrieveCacheDataStore().getBitCoinChart()
                }
            }
    }
}