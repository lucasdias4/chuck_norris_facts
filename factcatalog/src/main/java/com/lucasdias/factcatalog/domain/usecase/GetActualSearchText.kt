package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.factcatalog.domain.repository.SearchTextRepository

class GetActualSearchText(
    private var searchTextRepository: SearchTextRepository
) {
    operator fun invoke(): String = searchTextRepository.getActualSearchText()
}
