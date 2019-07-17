package com.greydog.fitness

import com.greydog.datamodels.Steps
import com.greydog.fitness.repository.remote.FitnessApiEndpoints
import com.greydog.network.ClientProvider
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance


@UseExperimental(ExperimentalCoroutinesApi::class, ObsoleteCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StepsViewModelTest {
    lateinit var viewModel: StepsViewModel
    lateinit var apiService: FitnessApiEndpoints
    lateinit var clientProvider: ClientProvider

    @BeforeAll
    fun setupThread() = Dispatchers.setMain(TestCoroutineDispatcher())

    @Before
    fun setUp() {
        apiService = mockk(relaxed = true)
        clientProvider = mockk(relaxed = true)
    }

    @Test
    fun checkSaveStepsCallsAPI() {
        viewModel = StepsViewModel(clientProvider)
        viewModel.steps.value = "9768"
        viewModel.saveSteps()

        coVerify(exactly = 1) { apiService.postSteps(12345, Steps(9768)) }
    }
}