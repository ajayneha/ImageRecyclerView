package com.appentus.assessment.apiclient

import com.appentus.assessment.AssessmentApplication
import com.appentus.assessment.utils.CryptLib
import com.appentus.assessment.utils.NetworkConnectionInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private var apiService: ApiService? = null
    private var googleApiService: ApiService? = null

    fun getInstance(): ApiService {
        return apiService ?: makeRetrofitService("")
    }

    private fun makeRetrofitService(baseUrl : String): ApiService {
        return Retrofit.Builder()
                .baseUrl(if(baseUrl.isEmpty()) Api.BASE_URL else baseUrl)
                .client(if(baseUrl.isEmpty()) makeOkHttpClient() else makeGoogleOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(ApiService::class.java)
    }

    private fun makeGoogleOkHttpClient(): OkHttpClient {
        val tlsSocketFactory = TLSSocketFactory()
        val networkConnectionInterceptor = NetworkConnectionInterceptor(AssessmentApplication.getInstance())
        return OkHttpClient.Builder()
                .addInterceptor(makeLoggingInterceptor())
                .addInterceptor(networkConnectionInterceptor)
                .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.trustManager)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build()
    }

    private fun makeOkHttpClient(): OkHttpClient {
        val tlsSocketFactory = TLSSocketFactory()
        val networkConnectionInterceptor = NetworkConnectionInterceptor(AssessmentApplication.getInstance())
        return OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(makeLoggingInterceptor())
                .addInterceptor(networkConnectionInterceptor)
                .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.trustManager)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private val authInterceptor = Interceptor { chain ->

        val cryptLib = CryptLib()
        //val cipherText = cryptLib.encryptPlainTextWithRandomIV(Date().time.toString(), "945]Y3x[aRJS}DxE")

        val newRequest = chain.request()
            .newBuilder()
            .build()
        chain.proceed(newRequest)

    }
}