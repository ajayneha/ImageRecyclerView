package com.appentus.assessment.base

import com.appentus.assessment.apiclient.ApiRequest
import com.appentus.assessment.apiclient.ApiService
import com.appentus.assessment.callback.GeneralCallback
import com.appentus.assessment.model.BaseResponse
import com.appentus.assessment.model.MediaResponse

open class BaseRepository(private val myApi: ApiService, generalCallback: GeneralCallback) : ApiRequest(generalCallback) {

    val callback = generalCallback

    suspend fun getMediaData(request: Map<String, String>): MutableList<MediaResponse> {
        return apiRequest { myApi.getMediaData(request) }
    }
}