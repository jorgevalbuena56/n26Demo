package com.n26.remote.service

import com.n26.data.render.BitCoinChart
import com.n26.data.source.BitCoinChartDataStore
import com.n26.remote.mapper.BitCoinChartEntityMapper
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Remote implementation for retrieving Bufferoo instances. This class implements the
 * [BitCoinChartDataStore] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class BitCoinChartRemoteImpl constructor(private val bitCoinChartService: BitCoinChartService,
                                         private val bitCoinChartEntityMapper: BitCoinChartEntityMapper
)
    : BitCoinChartDataStore {
    override fun clearBitCoinChart(): Completable {
        throw Exception("implementation not applicable in this layer")
    }

    override fun saveBitCoinChart(bitCoinChart: BitCoinChart): Completable {
        throw Exception("implementation not applicable in this layer")
    }

    /**
     * Retrieve the details of [BitCoinChart] from the [BitCoinChartService].
     */
    override fun getBitCoinChart(): Flowable<BitCoinChart> {
        return bitCoinChartService.getBitCoinChart()
            .map { bitCoinChartEntityMapper.mapFromRemote(it) }
    }

    override fun isCached(): Single<Boolean> {
        throw Exception("implementation not applicable in this layer")
    }

    override fun setLastCacheTime(lastCache: Long) {
        throw Exception("implementation not applicable in this layer")
    }

    override fun isExpired(): Boolean {
        throw Exception("implementation not applicable in this layer")
    }
}