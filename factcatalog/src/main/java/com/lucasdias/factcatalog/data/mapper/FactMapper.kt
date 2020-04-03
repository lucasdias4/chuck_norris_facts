package com.lucasdias.factcatalog.data.mapper

import com.lucasdias.base.typeconverter.TypeConverter
import com.lucasdias.factcatalog.data.remote.response.FactListResponse
import com.lucasdias.factcatalog.domain.model.Fact

internal class FactMapper {

    companion object {
        private const val EMPTY_STRING = ""
        private const val HTTP = "http://"
        private const val HTTPS = "https://"

        fun map(factListResponse: FactListResponse): ArrayList<Fact> {

            val domainFacts = ArrayList<Fact>()
            factListResponse.facts.forEach { fact ->
                val httpsUrl = makeSureTheUrlHasHttps(url = fact.url)
                val categoryListAsString = TypeConverter.arrayListToString(list = fact.categories)

                val domainFact = Fact(
                    id = fact.id,
                    value = fact.value,
                    url = httpsUrl,
                    categoryListAsString = categoryListAsString
                )
                domainFacts.add(domainFact)
            }
            return domainFacts
        }

        private fun makeSureTheUrlHasHttps(url: String?): String {
            val urlWithHttps = url?.replace(HTTP, HTTPS) ?: EMPTY_STRING
            return urlWithHttps
        }
    }
}
