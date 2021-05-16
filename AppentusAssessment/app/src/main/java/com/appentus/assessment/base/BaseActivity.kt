package com.appentus.assessment.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.appentus.assessment.AssessmentApplication
import com.appentus.assessment.R
import com.appentus.assessment.callback.GeneralCallback
import com.appentus.assessment.utils.CommonUtils

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel> : AppCompatActivity(),
    GeneralCallback,
    BaseFragment.Callback{

    private var progressBar: View? = null
    private var mViewDataBinding: T? = null
    private var mViewModel: V? = null


    override fun onResume() {
        super.onResume()
        AssessmentApplication.getInstance().setCurrentActivity(this)
    }

    override fun onPause() {
        super.onPause()
        AssessmentApplication.getInstance().setCurrentActivity(null)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        performDataBinding()
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int

    fun getViewDataBinding(): T {
        return mViewDataBinding!!
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): V

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()
    }

    private fun init() {
        progressBar = layoutInflater.inflate(R.layout.layout_progress, null) as View
        val v = this.findViewById<View>(android.R.id.content).rootView
        val viewGroup = v as ViewGroup
        viewGroup.addView(progressBar)
    }

    override fun showProgress() {
        runOnUiThread {
            progressBar?.visibility = View.VISIBLE
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    override fun hideProgress() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        runOnUiThread {
            progressBar?.visibility = View.GONE
        }
    }

    fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }

    override fun hideKeyboard() {
        CommonUtils.hideSoftInput(this)
    }

    fun hideKeyboard(view: View) {
        CommonUtils.hideSoftInput(view)
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (progressBar!!.visibility == View.GONE) {
            super.onBackPressed()
        }
    }

}