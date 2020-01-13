package com.example.fuglepretestsample.views.main

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.fuglepretestsample.R
import com.example.fuglepretestsample.databinding.ActivityMainBinding
import com.example.fuglepretestsample.viewmodels.MainViewModel
import com.example.fuglepretestsample.views.base.BaseActivity

/**
 * 主頁 View
 * */
class MainActivity : BaseActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        //
        setUpToolbar(view = viewBinding.toolbar, title = "Symbols")
    }

    private fun setUpToolbar(
        view: Toolbar,
        @Suppress("SameParameterValue") title: String) {
        super.setSupportActionBar(view)
        super.getSupportActionBar()?.title = title
    }
}
