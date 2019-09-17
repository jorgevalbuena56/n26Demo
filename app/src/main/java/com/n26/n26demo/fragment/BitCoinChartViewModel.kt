package com.n26.n26demo.fragment

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.n26.data.render.interactor.GetBitCoinChart
import com.n26.n26demo.core.exception.Failure
import com.n26.n26demo.core.platform.BaseViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class BitCoinChartViewModel
@Inject constructor(private val getBitCoinChart: GetBitCoinChart) : BaseViewModel() {
    val bitCoinChartLiveData: MutableLiveData<RenderState> = MutableLiveData()
    private var disposable: Disposable? = null

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }

    fun getBitCoinChart(): LiveData<RenderState> {
        return bitCoinChartLiveData
    }
    fun fetBitCoinChart() {
        bitCoinChartLiveData.postValue(RenderState.Loading)
        disposable = getBitCoinChart.execute()
            .subscribe({
                bitCoinChartLiveData.postValue(RenderState.Success(it))
            }, {
                handleFailure(Failure.NetworkConnection)
            })
    }
}