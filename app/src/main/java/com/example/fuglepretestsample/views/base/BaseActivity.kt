package com.example.fuglepretestsample.views.base

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * 基底 Activity 類，寫一些常用到的 func ex: show/dismiss dialog
 * */
abstract class BaseActivity : AppCompatActivity() {

    protected fun setUpToolbar(view: Toolbar, title: String = "") {
        super.setSupportActionBar(view)
        super.getSupportActionBar()?.title = title
    }

    protected fun setHomeButtonEnable(@Suppress("SameParameterValue") enable: Boolean) {
        super.getSupportActionBar()?.setDisplayHomeAsUpEnabled(enable)
        super.getSupportActionBar()?.setHomeButtonEnabled(enable)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // 左側返回鍵的 id
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}