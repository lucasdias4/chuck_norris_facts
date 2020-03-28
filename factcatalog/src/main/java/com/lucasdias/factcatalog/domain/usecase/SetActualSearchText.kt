package com.lucasdias.factcatalog.domain.usecase

import com.lucasdias.factcatalog.domain.repository.SearchTextRepository

class SetActualSearchText(
    private var searchTextRepository: SearchTextRepository
) {
    operator fun invoke(searchText: String) {
        searchTextRepository.setActualSearchText(searchText)
    }
}
