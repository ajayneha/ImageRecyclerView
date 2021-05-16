package com.appentus.assessment.apiclient

import com.appentus.assessment.enums.ENVIRONMENT

object Api {

    var BASE_URL = getBaseUrl(ENVIRONMENT.LIVE.ordinal)

    private const val COMMON_PATH = "search"
    const val GET_MEDIA_DATA = "list"

    private fun getBaseUrl(environmentType: Int): String {

        return when (environmentType) {

            ENVIRONMENT.LIVE.ordinal -> {
                "https://picsum.photos/v2/"
            }
            else -> {
                "https://picsum.photos/v2/"
            }
        }
    }
}