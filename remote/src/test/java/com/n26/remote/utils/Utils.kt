package com.n26.remote.utils

import com.n26.remote.model.BitCoinChartModel
import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.net.URISyntaxException

/**
  * Reads the sample json and transform it into a T object
  * @param resourceName
  * @return
  * @throws URISyntaxException
  * @throws FileNotFoundException
  */
@Throws(URISyntaxException::class, FileNotFoundException::class)
  fun getClassFromResource(resourceName: String): BitCoinChartModel {
    val resourceFile = BitCoinChartModel::class.java.classLoader.getResource(resourceName)
        val f = File(resourceFile!!.toURI())
        val reader = JsonReader(FileReader(f))
        return GsonBuilder().create().fromJson(reader, BitCoinChartModel::class.java) as BitCoinChartModel
  }