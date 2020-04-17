package com.lucasdias.home.data.userfirsttime

import com.lucasdias.home.data.userfirsttime.local.UserFirstTimeCache
import com.lucasdias.home.domain.repository.UserFirstTimeRepository

class UserFirstTimeRepositoryImpl(
    private var userFirstTimeCache: UserFirstTimeCache
) : UserFirstTimeRepository {
    override fun getIfIsUserFirstTime() = userFirstTimeCache.getIfIsUserFirstTime()
    override fun setThatIsNotTheUsersFirstTime() = userFirstTimeCache.setIsNotFirstTime()
}
