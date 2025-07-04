package com.paulrezzonico.dataProvider

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

@Component
class TextDataProvider : IDataProvider {
    override fun getData(path: String): List<String> {
        val data: MutableList<String> = ArrayList()
        try {
            val inputStream = javaClass.getResourceAsStream(path)
            val reader = BufferedReader(InputStreamReader(Objects.requireNonNull(inputStream)))

            var line: String
            while ((reader.readLine().also { line = it }) != null) {
                data.add(line)
            }
            reader.close()
        } catch (e: IOException) {
            logger.error("Error while reading quotes file", e)
        } catch (e: NullPointerException) {
            logger.error("Resource not found: $path", e)
        }

        return data
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(TextDataProvider::class.java)
    }
}
