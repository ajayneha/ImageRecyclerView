package com.appentus.assessment.ui.home

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.appentus.assessment.BR
import com.appentus.assessment.R
import com.appentus.assessment.ViewModelFactory
import com.appentus.assessment.apiclient.RetrofitFactory
import com.appentus.assessment.base.BaseActivity
import com.appentus.assessment.base.BaseRepository
import com.appentus.assessment.databinding.ActivityHomeBinding
import com.appentus.assessment.ui.home.adapter.MediaAdapter
import com.appentus.assessment.utils.RecyclerViewPaginator
import com.appentus.assessment.utils.makeStatusBarTransparent
import com.appentus.assessment.utils.setOnApplyWindowInset
import kotlinx.android.synthetic.main.activity_home.*
import java.util.HashMap


class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(){

    private var mActivityBinding: ActivityHomeBinding? = null
    var mViewModel: HomeViewModel? = null

    private lateinit var recyclerViewPaging: RecyclerViewPaginator
    var mediaAdapter: MediaAdapter? = null

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home
    }

    override fun getViewModel(): HomeViewModel {
        return mViewModel!!
    }

    override fun onDestroy() {
        super.onDestroy()
        mShimmerView.stopShimmerAnimation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setViewModel()
        super.onCreate(savedInstanceState)
        mActivityBinding = getViewDataBinding()
        mActivityBinding?.lifecycleOwner = this
        mViewModel?.setActivityNavigator(this)
        mViewModel?.setVariable(mActivityBinding!!)
        setSupportActionBar(toolbar)

        makeStatusBarTransparent()
        setOnApplyWindowInset(toolbar, content_container)
        mShimmerView.startShimmerAnimation()
        //recViewMedia.addItemDecoration(EqualSpacingItemDecoration(20, EqualSpacingItemDecoration.VERTICAL))
        recViewMedia.layoutManager = GridLayoutManager(this@HomeActivity, 2)
        mediaAdapter = MediaAdapter()
        recViewMedia.adapter = mediaAdapter

        recyclerViewPaging = object : RecyclerViewPaginator(recViewMedia) {
            override val isLastPage: Boolean
                get() = mViewModel!!.isLastPage.value!!

            override fun loadMore(start: Int, count: Int) {
                mViewModel?.currentPage?.value = count
                val requestParams = HashMap<String, String>()
                requestParams["page_no"] = count.toString()
                mViewModel?.setupObservers(requestParams)
            }
        }

        recViewMedia.addOnScrollListener(recyclerViewPaging)

        mViewModel?.mediaListData?.observe(this@HomeActivity){
            mediaAdapter?.submitList(it)
        }

        val requestParams = HashMap<String, String>()
        requestParams["page_no"] = mViewModel?.currentPage?.value.toString()
        mViewModel?.setupObservers(requestParams)

        mSwipeRefresh.setOnRefreshListener {
            resetMediaList()
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory(HomeViewModel(BaseRepository(RetrofitFactory.getInstance(), this)))
        mViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    fun resetMediaList() {
        recyclerViewPaging.reset()
        mViewModel?.isLastPage!!.value = true
        mViewModel?.currentPage?.value = 1
        mViewModel?.mediaListData?.value = ArrayList()

        val requestParams = HashMap<String, String>()
        requestParams["page_no"] = mViewModel?.currentPage?.value.toString()
        mViewModel?.setupObservers(requestParams)
    }

}