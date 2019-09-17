package com.n26.data.source

open class BitCoinChartDataStoreFactory(private val bitCoinChartCacheDataStore: BitCoinChartDataStore,
                                        private val bitCoinChartRemoteDataStore: BitCoinChartDataStore
) {
    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): BitCoinChartDataStore {
        if (isCached && !bitCoinChartCacheDataStore.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): BitCoinChartDataStore {
        return bitCoinChartCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): BitCoinChartDataStore {
        return bitCoinChartRemoteDataStore
    }
}