package com.n26.cache.db

object Constant {
    const val version = 1
    const val dbName = "bitCoinChart.db"
    const val TABLE_CHART_NAME = "BitCoinChart"
    const val TABLE_PRICE_NAME = "BitCoinPrice"
    const val QUERY_BIT_COIN_CHART = "SELECT * FROM $TABLE_CHART_NAME"
    const val QUERY_BIT_COIN_PRICE = "SELECT * FROM $TABLE_PRICE_NAME WHERE bitCoinChartId = :bitCoinChartId"
    const val DELETE_ALL_BIT_COIN_CHART = "DELETE FROM $TABLE_CHART_NAME"
}