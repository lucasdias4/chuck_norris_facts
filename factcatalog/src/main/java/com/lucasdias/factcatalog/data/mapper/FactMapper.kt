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
                val httpsUrl = makeSureTheUrlHasHttps(fact.url)
                val categorieListAsString = TypeConverter.arrayListToString(fact.categories)

                val domainFact = Fact(
                    id = fact.id,
                    value = fact.value,
                    url = httpsUrl,
                    categorieListAsString = categorieListAsString
                )
                domainFacts.add(domainFact)
            }
            return domainFacts
        }

        private fun makeSureTheUrlHasHttps(urlWithHttp: String?): String {
            val urlWithHttps = urlWithHttp?.replace(HTTP, HTTPS) ?: EMPTY_STRING
            return urlWithHttps
        }
    }
}