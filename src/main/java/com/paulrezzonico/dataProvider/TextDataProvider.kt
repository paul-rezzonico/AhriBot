package com.paulrezzonico.dataProvider

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

@Component
class TextDataProvider : IDataProvider {
    override fun getData(path: String): List<String> {
        val data: MutableList<String> = ArrayList()
        try {
            val `in` = javaClass.getResourceAsStream(path)
            val reader = BufferedReader(InputStreamReader(Objects.requireNonNull(`in`)))

            var line: String
            while ((reader.readLine().also { line = it }) != null) {
                data.add(line)
            }
            reader.close()
        } catch (e: Exception) {
            logger.error("Error while reading quotes file", e)
        }

        return data
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TextDataProvider::class.java)
    }
}
