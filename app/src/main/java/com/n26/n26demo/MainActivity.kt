package com.n26.n26demo

import com.n26.n26demo.core.platform.BaseActivity
import com.n26.n26demo.core.platform.BaseFragment
import com.n26.n26demo.fragment.BitCoinChartFragment


class MainActivity : BaseActivity() {
    override fun fragment(): BaseFragment = BitCoinChartFragment()
}
