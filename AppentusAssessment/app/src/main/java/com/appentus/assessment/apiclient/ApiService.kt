package com.appentus.assessment.apiclient

import com.appentus.assessment.model.BaseResponse
import com.appentus.assessment.model.MediaResponse
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    @GET(Api.GET_MEDIA_DATA)
    suspend fun getMediaData(@QueryMap params: Map<String, String>): Response<MutableList<MediaResponse>>

}