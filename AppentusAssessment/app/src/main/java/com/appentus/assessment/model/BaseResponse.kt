package com.appentus.assessment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse<T> {

    @SerializedName("results")
    @Expose
    var data: T? = null
}
