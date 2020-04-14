package com.lucasdias.factcatalog.data.mapper

import com.lucasdias.factcatalog.data.local.model.FactData
import com.lucasdias.factcatalog.data.remote.model.FactListResponse
import com.lucasdias.factcatalog.domain.model.Fact

internal class FactMapper {

    companion object {
        private const val EMPTY_STRING = ""
        private const val HTTP = "http://"
        private const val HTTPS = "https://"

        fun mapRemoteToLocal(factListResponse: FactListResponse): List<FactData> {

            val localFacts = mutableListOf<FactData>()
            factListResponse.facts.map { remoteFact ->
                val httpsUrl = makeSureTheUrlHasHttps(url = remoteFact.url)
                val hasCategories = remoteFact.categories.isNotEmpty()
                val categoryListAsString =
                    if (hasCategories) remoteFact.categories.joinToString()
                    else null

                val domainFact = FactData(
                    id = remoteFact.id,
                    value = remoteFact.value,
                    url = httpsUrl,
                    categoryListAsString = categoryListAsString
                )
                localFacts.add(domainFact)
            }
            return localFacts
        }

        fun mapLocalToDomain(factsLocal: List<FactData>): List<Fact> {
            val domainFacts = mutableListOf<Fact>()
            factsLocal.map { localFacts ->
                val httpsUrl = makeSureTheUrlHasHttps(url = localFacts.url)
                val categories = localFacts.getCategories()

                val domainFact = Fact(
                    id = localFacts.id,
                    value = localFacts.value,
                    url = httpsUrl,
                    categories = categories
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
