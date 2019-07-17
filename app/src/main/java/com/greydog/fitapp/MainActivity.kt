package com.greydog.fitapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType.AGGREGATE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.data.DataType.TYPE_STEP_COUNT_DELTA
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.tasks.Task
import io.reactivex.internal.util.HalfSerializer.onComplete
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    //private val GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*val fitnessOptions = FitnessOptions.builder()
                .addDataType(TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build()

        if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    this, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE,
                    GoogleSignIn.getLastSignedInAccount(this),
                    fitnessOptions);
        } else {
            accessGoogleFit()
        }*/
    }

    /*private fun accessGoogleFit() {
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

        var historyClient = Fitness.getHistoryClient(this, GoogleSignIn.getLastSignedInAccount(this)!!)
                //.readData(readRequest)

        /*historyClient.addOnCompleteListener {
            var datasets = it.result?.dataSets

            var datapoints = datasets
        }*/

        var today = historyClient.readDailyTotal(TYPE_STEP_COUNT_DELTA)
        today.addOnCompleteListener {
            var datapoints = it.result?.dataPoints

            for (dp in datapoints!!) {
                println("********* " + dp.dataType)
                for (field in dp.dataType.fields) {
                    println("\tField: " + field.name + " Value: " + dp.getValue(field))
                }
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
    }*/
}
