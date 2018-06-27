package com.tradr.mvvm.viewmodel

import android.databinding.BaseObservable
import io.reactivex.disposables.CompositeDisposable

abstract class ViewModel : BaseObservable() {
    protected val mDisposables = CompositeDisposable()

    abstract fun bind()

    fun unbind() {
        mDisposables.clear()
    }
}