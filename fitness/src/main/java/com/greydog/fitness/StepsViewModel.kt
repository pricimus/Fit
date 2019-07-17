package com.greydog.fitness

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greydog.datamodels.NewUserRequest
import com.greydog.datamodels.Steps
import com.greydog.fitness.repository.remote.FitnessApiEndpoints
import com.greydog.network.ClientProvider
import kotlinx.coroutines.launch

class StepsViewModel(private val clientProvider: ClientProvider) : ViewModel() {

    val steps: MutableLiveData<String> = MutableLiveData("0")
    val errorMessageVisible: MutableLiveData<Boolean> = MutableLiveData()

    init {
        errorMessageVisible.value = false
    }

    fun hideErrorMessage() {
        if (errorMessageVisible.value == true)
            errorMessageVisible.value = false
    }

    fun saveSteps() {
        hideErrorMessage()

        viewModelScope.launch {
            saveStepsAsync()
        }
    }

    private suspend fun saveStepsAsync() {
        val fitnessAPIService = clientProvider.client.create(FitnessApiEndpoints::class.java)

        try {
            //hardcoding userid but would pull out of preferences
            fitnessAPIService.postSteps(12345, Steps(step_count = steps.value!!.toInt()))
                    .await()
        } catch (t: Throwable) {
            errorMessageVisible.value = true
            return
        }

        errorMessageVisible.value = false
    }
}