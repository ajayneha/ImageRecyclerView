package com.appentus.assessment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class MediaResponse(

    @Expose
    @SerializedName("id")
    val id: String = "",

    @Expose
    @SerializedName("author")
    val author: String = "",

    @Expose
    @SerializedName("width")
    val width: String = "",

    @Expose
    @SerializedName("height")
    val height: String = "",

    @Expose
    @SerializedName("url")
    val url: String = "",

    @Expose
    @SerializedName("download_url")
    val downloadUrl: String = "") : Serializable
