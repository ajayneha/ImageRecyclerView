package com.appentus.assessment

import android.app.Activity
import android.app.Application
import android.content.Context

class AssessmentApplication : Application() {

    private var mCurrentActivity: Activity? = null

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    companion object {

        private var application: AssessmentApplication? = null
        fun getInstance(): AssessmentApplication {
            return application!!
        }
    }

    fun getCurrentActivity(): Activity? {
        return mCurrentActivity
    }

    fun setCurrentActivity(mCurrentActivity: Activity?) {
        this.mCurrentActivity = mCurrentActivity
    }

}