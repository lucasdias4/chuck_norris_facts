package com.lucasdias.home.domain.repository

interface UserFirstTimeRepository {
    fun getIfIsUserFirstTime(): Boolean
    fun setThatIsNotTheUsersFirstTime()
}
