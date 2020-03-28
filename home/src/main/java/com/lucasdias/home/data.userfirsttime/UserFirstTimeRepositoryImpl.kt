package com.lucasdias.home.data.userfirsttime

import android.content.Context
import com.lucasdias.home.data.userfirsttime.local.UserFirstTimeCache
import com.lucasdias.home.domain.repository.UserFirstTimeRepository

class UserFirstTimeRepositoryImpl(
    private var userFirstTimeCache: UserFirstTimeCache,
    private var context: Context
) : UserFirstTimeRepository {
    override fun getIfIsUserFirstTime(): Boolean {
        return userFirstTimeCache.getIfIsUserFirstTime(context)
    }

    override fun setThatIsNotTheUsersFirstTime() {
        userFirstTimeCache.setIsNotFirstTime(context)
    }
}
