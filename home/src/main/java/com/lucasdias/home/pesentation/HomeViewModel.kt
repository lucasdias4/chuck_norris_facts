package com.lucasdias.home.pesentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lucasdias.home.domain.usecase.GetIfIsTheUsersFirstTime
import com.lucasdias.home.domain.usecase.SetThatIsNotTheUsersFirstTimeInTheCache

internal class HomeViewModel(
    val getIfIsTheUsersFirstTime: GetIfIsTheUsersFirstTime,
    val setThatIsNotTheUsersFirstTimeInTheCache: SetThatIsNotTheUsersFirstTimeInTheCache
) : ViewModel() {

    private var wasConnected = true
    private var showConnectivityOnSnackbar = MutableLiveData<Unit>()
    private var showConnectivityOffSnackbar = MutableLiveData<Unit>()
    private var isTheUserFirstTime = MutableLiveData<Unit>()
    private var isNotTheUserFirstTime = MutableLiveData<Unit>()

    fun showConnectivityOnSnackbar(): LiveData<Unit> = showConnectivityOnSnackbar
    fun showConnectivityOffSnackbar(): LiveData<Unit> = showConnectivityOffSnackbar
    fun isTheUserFirstTime(): LiveData<Unit> = isTheUserFirstTime
    fun isNotTheUserFirstTime(): LiveData<Unit> = isNotTheUserFirstTime

    fun mustShowConnectivitySnackbar(hasNetworkConnectivity: Boolean) {
        if (hasNetworkConnectivity.not()) {
            showConnectivityOffSnackbar.postValue(Unit)
            wasConnected = false
        } else if (wasConnected.not() && hasNetworkConnectivity) {
            showConnectivityOnSnackbar.postValue(Unit)
            wasConnected = true
        }
    }

    fun verifyIfItIsTheUsersFirstTimeOnCache() {
        val firstTime = getIfIsTheUsersFirstTime()

        if (firstTime) isTheUserFirstTime.postValue(Unit)
        else isNotTheUserFirstTime.postValue(Unit)
    }

    fun setThatIsNotTheUsersFirstTime() {
        setThatIsNotTheUsersFirstTimeInTheCache()
    }
}
