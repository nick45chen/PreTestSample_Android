package com.example.fuglepretestsample.views.main

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fuglepretestsample.R
import com.example.fuglepretestsample.databinding.ActivityMainBinding
import com.example.fuglepretestsample.repositories.StocksRepository
import com.example.fuglepretestsample.viewmodels.MainViewModel
import com.example.fuglepretestsample.views.base.BaseActivity

/**
 * 主頁 View
 * */
class MainActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val repository by lazy { StocksRepository() }
    private val adapter by lazy { StocksViewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.injectRepository(repository)
        //
        super.setUpToolbar(view = viewBinding.toolbar, title = "Symbols")
        setUpLoadingView(view = viewBinding.swipeRefreshView, viewModel = viewModel)
        setUpPullRefreshView(view = viewBinding.swipeRefreshView, viewModel = viewModel)
        setUpStocksListView(view = viewBinding, viewModel = viewModel)
        // 呼叫API
        viewModel.requestApi()
    }

    /**
     * Loading view
     * */
    private fun setUpLoadingView(view: SwipeRefreshLayout, viewModel: MainViewModel) {
        viewModel.isLoadingLiveData.observe(this,
            Observer<Boolean> {
                view.isRefreshing = it
            })
    }

    /**
     * 下拉刷新
     * */
    private fun setUpPullRefreshView(view: SwipeRefreshLayout, viewModel: MainViewModel) {
        view.setOnRefreshListener {
            viewModel.requestApi()
        }
    }

    /**
     * 股票列表
     * */
    private fun setUpStocksListView(view: ActivityMainBinding, viewModel: MainViewModel) {
        view.isShowAlert = true
        view.recyclerView.adapter = adapter
        viewModel.stocksLiveData.observe(this, Observer {
            view.isShowAlert = false
            adapter.updateData(it)
        })
    }
}
