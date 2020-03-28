package com.lucasdias.factcatalog.data.fact.mapper

import com.lucasdias.factcatalog.data.fact.remote.response.FactListResponse
import com.lucasdias.factcatalog.domain.model.Fact
import com.lucasdias.base.typeconverter.TypeConverter

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
