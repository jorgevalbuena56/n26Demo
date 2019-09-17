package com.n26.cache.utils

import com.n26.cache.model.BitCoinChartCached
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
@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Throws(URISyntaxException::class, FileNotFoundException::class)
  fun getClassFromResource(resourceName: String): BitCoinChartCached {
    val resourceFile = BitCoinChartCached::class.java.classLoader.getResource(resourceName)
        val f = File(resourceFile!!.toURI())
        val reader = JsonReader(FileReader(f))
        return GsonBuilder().create().fromJson(reader, BitCoinChartCached::class.java) as BitCoinChartCached
  }