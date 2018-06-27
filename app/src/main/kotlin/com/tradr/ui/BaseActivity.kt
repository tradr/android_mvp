package com.tradr.ui

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.tradr.mvvm.viewmodel.ViewModel
import javax.inject.Inject

abstract class BaseActivity<T: ViewDataBinding, V: ViewModel>: AppCompatActivity() {
    @Inject lateinit var viewModel: V
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInjectDependencies()
        onBindView()
        onInitViewModel(savedInstanceState)
    }

    abstract fun onInjectDependencies()
    abstract fun onBindView()

    fun onInitViewModel(savedInstanceState: Bundle?) {}

    override fun onStart() {
        super.onStart()
        viewModel.bind()
    }

    override fun onStop() {
        super.onStop()
        viewModel.unbind()
    }
}