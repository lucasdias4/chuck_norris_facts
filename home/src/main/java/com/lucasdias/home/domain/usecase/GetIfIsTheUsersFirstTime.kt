package com.lucasdias.home.domain.usecase

import com.lucasdias.home.domain.repository.UserFirstTimeRepository

class GetIfIsTheUsersFirstTime(
    private var userFirstTimeRepository: UserFirstTimeRepository
) {
    operator fun invoke(): Boolean = userFirstTimeRepository.getIfIsUserFirstTime()
}
