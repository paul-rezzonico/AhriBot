package com.paulrezzonico.util

import com.paulrezzonico.dataProvider.IDataProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class QuoteManager {
    @Autowired
    private val dataProvider: IDataProvider? = null

    val randomQuote: String
        get() {
            val quotes = dataProvider!!.getData("/voiceLines.txt")

            if (quotes.isNotEmpty()) {
                val random = Random()
                return quotes[random.nextInt(quotes.size)]
            }

            return "No quotes found"
        }
}
