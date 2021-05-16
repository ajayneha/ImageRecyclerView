package com.appentus.assessment.apiclient

import com.appentus.assessment.callback.GeneralCallback
import org.json.JSONObject
import retrofit2.Response

@Suppress("BlockingMethodInNonBlockingContext")
abstract class ApiRequest(private val generalCallback: GeneralCallback) {

    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {

        generalCallback.showProgress()
        val response = call.invoke()
        generalCallback.hideProgress()
        if (response.isSuccessful) {
            return response.body()!!
        } else {

            val message = StringBuilder()
            var error = response.errorBody()?.string()
            val obj = JSONObject(error!!)
            obj.put("code", response.code())
            error = obj.toString()
            throw ApiException(error)
        }
    }
}