package com.greydog.fitness

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.data.DataType.TYPE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.request.DataReadRequest
import com.greydog.extensions.inflateBinding
import com.greydog.fitness.databinding.StepsFragmentBinding
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import java.util.concurrent.TimeUnit

class StepsFragment : Fragment() {

    private val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 101

    private val viewModel: StepsViewModel by viewModel()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        return inflateBinding<StepsFragmentBinding>(R.layout.steps_fragment, container) { it.vm = viewModel }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fitnessOptions = FitnessOptions.builder()
                .addDataType(TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build()

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(requireContext()), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(requireContext()),
                    fitnessOptions);
        } else {
            accessGoogleFit()
        }
    }

    private fun accessGoogleFit() {
        val cal = Calendar.getInstance()
        cal.setTime(Date())
        val endTime = cal.getTimeInMillis()
        cal.add(Calendar.YEAR, -1)
        val startTime = cal.getTimeInMillis()

        val readRequest = DataReadRequest.Builder()
                .aggregate(TYPE_STEP_COUNT_DELTA, AGGREGATE_STEP_COUNT_DELTA)
                .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                .bucketByTime(1, TimeUnit.DAYS)
                .build()

        var historyClient = Fitness.getHistoryClient(this.requireActivity(), GoogleSignIn.getLastSignedInAccount(requireContext())!!)
        //.readData(readRequest)

        /*historyClient.addOnCompleteListener {
            var datasets = it.result?.dataSets

            var datapoints = datasets
        }*/

        var today = historyClient.readDailyTotal(TYPE_STEP_COUNT_DELTA)
        today.addOnCompleteListener {
            var datapoints = it.result?.dataPoints

            datapoints.let {
                if (it?.count() == 0) {
                    viewModel.steps.value = "0"
                } else {
                    for (dp in it!!) {
                        println("********* " + dp.dataType)
                        for (field in dp.dataType.fields) {
                            println("\tField: " + field.name + " Value: " + dp.getValue(field))
                        }
                    }
                }
                viewModel.steps.value = "9768"
                viewModel.saveSteps()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GOOGLE_FIT_PERMISSIONS_REQUEST_CODE) {
                accessGoogleFit()
            }
        }
    }

}