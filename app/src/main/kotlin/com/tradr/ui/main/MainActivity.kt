package com.tradr.ui.main

import android.databinding.DataBindingUtil
import com.tradr.R
import com.tradr.databinding.MainBinding
import com.tradr.ui.BaseActivity
import com.tradr.ui.main.viewmodel.MainViewModel
import com.tradr.util.Injector

class MainActivity : BaseActivity<MainBinding, MainViewModel>() {
    override fun onInjectDependencies() {
        Injector.obtain(applicationContext).inject(this)
    }

    override fun onBindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.main)
    }
}