package com.n26.n26demo.fragment

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.n26.n26demo.EspressoIdlingResource
import com.n26.n26demo.MainActivity
import com.n26.n26demo.R
import com.n26.n26demo.core.exception.Failure
import com.n26.n26demo.core.extension.failure
import com.n26.n26demo.core.extension.observe
import com.n26.n26demo.core.extension.viewModel
import com.n26.n26demo.core.platform.BaseFragment
import com.n26.n26demo.databinding.ContentMainBinding
import com.n26.n26demo.model.ResourceState
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class BitCoinChartFragment: BaseFragment() {
    private lateinit var bitCoinChartViewModel: BitCoinChartViewModel
    private lateinit var mBinding: ContentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.content_main, container, false)
        mBinding.bitCoinChartView.apply {
            setNoDataText("")
            setNoDataTextColor(Color.BLACK)
        }
        return mBinding.root
    }

    override fun layoutId() = R.layout.content_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        bitCoinChartViewModel = viewModel(viewModelFactory) {
            observe(bitCoinChartLiveData, ::renderBitCoinChart)
            failure(fail, ::handleFailure)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        EspressoIdlingResource.setFetchBeginning()
        loadBitCoinChartData()
    }

    private fun renderBitCoinChart(bitCoinChart: RenderState?) {
        if (bitCoinChart != null && bitCoinChart.resourceState != ResourceState.LOADING) {
            hideProgress()
            val entryList =
                bitCoinChart.data?.values?.map { Entry(it.x.toFloat(), it.y.toFloat()) }
            renderGraph(entryList, bitCoinChart.data?.name ?: "", bitCoinChart.data?.description ?: "")
        }
    }

    private fun renderGraph(entries: List<Entry>? , name: String, descriptionText: String) {
        val lineDataSet = LineDataSet(entries, name)
        mBinding.chartTitle.text = descriptionText
        val lineData = LineData(lineDataSet)
        mBinding.bitCoinChartView.apply {
            data = lineData
            description.text = ""
            scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
            setDrawGridBackground(false)
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            axisLeft.apply {
                isEnabled = true
                spaceTop = 40.0f
                spaceBottom = 40.0f
            }
            axisRight.isEnabled = false
            xAxis.apply {
                isEnabled = true
                position = XAxis.XAxisPosition.TOP_INSIDE
                textSize = 10f
                setDrawAxisLine(false)
                setDrawGridLines(true)
                textColor = Color.BLACK
                setCenterAxisLabels(true)
                valueFormatter = object: ValueFormatter() {
                    private val mFormat = SimpleDateFormat("dd MMM", Locale.ENGLISH)

                    override fun getFormattedValue(value: Float): String {
                        val millis = TimeUnit.DAYS.toMillis(value.toLong())
                        return mFormat.format(Date(millis))
                    }
                }
            }
            setNoDataText(getString(R.string.error_graph_render))
            setNoDataTextColor(Color.BLACK)
            animateX(2500)
            EspressoIdlingResource.setFetchDone()
        }
        mBinding.bitCoinChartView.invalidate()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadBitCoinChartData)
        EspressoIdlingResource.setFetchDone()
    }

    private fun loadBitCoinChartData() {
        showProgress()
        bitCoinChartViewModel.fetBitCoinChart()
    }
}