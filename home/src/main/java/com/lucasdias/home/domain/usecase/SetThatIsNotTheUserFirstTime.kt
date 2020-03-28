package com.lucasdias.home.domain.usecase

import com.lucasdias.home.domain.repository.UserFirstTimeRepository

class SetThatIsNotTheUserFirstTime(
    private var userFirstTimeRepository: UserFirstTimeRepository
) {
    operator fun invoke() {
        userFirstTimeRepository.setThatIsNotTheUsersFirstTime()
    }
}
