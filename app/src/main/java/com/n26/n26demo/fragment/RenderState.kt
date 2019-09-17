package com.n26.n26demo.fragment

import com.n26.data.render.BitCoinChart
import com.n26.n26demo.model.ResourceState

sealed class RenderState(val resourceState: ResourceState,
                         val data: BitCoinChart? = null) {

    data class Success(private val bitCoinChart: BitCoinChart): RenderState(ResourceState.SUCCESS,
        bitCoinChart)

    object Loading: RenderState(ResourceState.LOADING)
}