package com.lucasdias.home.domain.usecase

import com.lucasdias.home.domain.repository.UserFirstTimeRepository

class SetThatIsNotTheUsersFirstTimeInTheCache(
    private var userFirstTimeRepository: UserFirstTimeRepository
) {
    operator fun invoke() = userFirstTimeRepository.setThatIsNotTheUsersFirstTime()
}
