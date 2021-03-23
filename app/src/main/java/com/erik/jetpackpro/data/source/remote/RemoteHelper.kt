package com.erik.jetpackpro.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.erik.jetpackpro.data.source.remote.response.ApiResponse
import com.erik.jetpackpro.utils.EspressoIdlingResource
import retrofit2.Call
import java.util.concurrent.Executors

object RemoteHelper {
    private val TAG = RemoteHelper::class.java.simpleName

    fun <T> call(call: Call<T>): LiveData<ApiResponse<T>> {
        EspressoIdlingResource.increment()

        val returnVal = MutableLiveData<ApiResponse<T>>()

        Executors.newFixedThreadPool(5).execute {
            val response = call.execute()

            if (response.isSuccessful) {
                response.body()?.let {
                    returnVal.postValue(ApiResponse.success(it))
                }
            } else {
                Log.d(TAG, "call: Error " + response.errorBody()?.toString())
            }

            decrementIdlingResource()
        }

        return returnVal
    }

    private fun decrementIdlingResource() {
        if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
            EspressoIdlingResource.decrement()
        }
    }
}