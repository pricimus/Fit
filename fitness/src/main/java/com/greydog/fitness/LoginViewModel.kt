package com.greydog.fitness

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greydog.datamodels.NewUserRequest
import com.greydog.fitness.repository.remote.FitnessApiEndpoints
import com.greydog.network.ClientProvider
import kotlinx.coroutines.launch

class LoginViewModel(private val clientProvider: ClientProvider) : ViewModel() {

    var email = ""
    var password = ""
    val token: MutableLiveData<String> = MutableLiveData()
    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun login() {
        viewModelScope.launch {
            loginWithCreds()
        }
    }

    private suspend fun loginWithCreds() {
        val fitnessAPIService = clientProvider.client.create(FitnessApiEndpoints::class.java)

        val newUserResponse = try {
            fitnessAPIService.postLogin(NewUserRequest(email = email, password = password))
                    .await()
        } catch (t: Throwable) {
            return
        }

        token.value = newUserResponse.token
        loginSuccess.value = true
    }
}
