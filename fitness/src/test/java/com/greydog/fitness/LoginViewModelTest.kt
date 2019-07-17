package com.greydog.fitness

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.greydog.datamodels.NewUserRequest
import com.greydog.fitness.repository.remote.FitnessApiEndpoints
import com.greydog.network.ClientProvider
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.BeforeAll
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.TestInstance

@UseExperimental(ExperimentalCoroutinesApi::class, ObsoleteCoroutinesApi::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginViewModelTest {
    lateinit var viewModel: LoginViewModel
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
    fun checkLoginCallsAPI() {
        viewModel = LoginViewModel(clientProvider)
        viewModel.email = "ed@ed.com"
        viewModel.password = "ed"
        viewModel.login()

        coVerify(exactly = 1) { apiService.postLogin(NewUserRequest("ed@ed.com", "ed")) }
    }
}