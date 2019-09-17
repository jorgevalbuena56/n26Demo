package com.n26.data.render

data class BitCoinChart(val status: String, val name:String, val unit:String,
                        val period: String, val description:String, val values:List<BitCoinPrice>)