package com.appentus.assessment.ui.home

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.observe
import com.appentus.assessment.base.BaseRepository
import com.appentus.assessment.base.BaseViewModel
import com.appentus.assessment.databinding.ActivityHomeBinding
import com.appentus.assessment.enums.Status
import com.appentus.assessment.model.MediaResponse
import com.appentus.assessment.utils.Resource
import com.appentus.assessment.utils.plusAssign
import kotlinx.coroutines.Dispatchers
import java.util.HashMap

class HomeViewModel(private val baseRepository: BaseRepository) : BaseViewModel(){

    lateinit var mActivity: Activity
    lateinit var mBinding: ActivityHomeBinding

    var mediaListData = MutableLiveData<MutableList<MediaResponse>>()
    val currentPage = MutableLiveData(1)
    val totalOrderCount = MutableLiveData(0)
    val isLastPage = MutableLiveData(false)
    val isItemAvailable = MutableLiveData(false)

    fun setVariable(mBinding: ActivityHomeBinding) {
        this.mBinding = mBinding
        this.mActivity = getActivityNavigator()!!
    }


    private fun getMediaData(requestParams: HashMap<String, String>) = liveData(Dispatchers.Main) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = baseRepository.getMediaData(requestParams)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun setupObservers(requestParams: HashMap<String, String>) {
        getMediaData(requestParams).observe(mBinding.lifecycleOwner!!) {
            it.let { resource ->
                mBinding.mShimmerView.stopShimmerAnimation()
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { users ->
                            val response = users as MutableList<MediaResponse>
                            if(currentPage.value == 1){isItemAvailable.value = response.size > 0}
                            totalOrderCount.value = response.size
                            isLastPage.value = (currentPage.value == 34)
                             mediaListData.plusAssign(response)
                            (mActivity as HomeActivity).mediaAdapter!!.notifyDataSetChanged()
                        }
                    }

                    Status.ERROR -> {
                        baseRepository.callback.hideProgress()
                        Toast.makeText(mActivity, it.message!!, Toast.LENGTH_LONG).show()
                    }

                    Status.LOADING -> {
                        baseRepository.callback.showProgress()
                    }
                }
            }
        }
    }

}