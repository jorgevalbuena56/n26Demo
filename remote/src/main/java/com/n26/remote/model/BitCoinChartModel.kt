package com.n26.remote.model

data class BitCoinChartModel(val status: String, val name:String, val unit:String,
                             val period: String, val description:String, val values:List<BitCoinPriceModel>)