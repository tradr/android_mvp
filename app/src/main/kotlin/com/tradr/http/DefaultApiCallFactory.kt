package com.tradr.http

import io.reactivex.Observable
import io.reactivex.Scheduler
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type

// Based on SO answer: http://stackoverflow.com/questions/33454500/retrofit-default-thread
class DefaultApiCallAdapterFactory private constructor(private val mSubscribeOnScheduler: Scheduler, private val mObserveOnScheduler: Scheduler) : CallAdapter.Factory() {
    private val mRxFactory = RxJava2CallAdapterFactory.create()

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<Observable<*>, Observable<*>>? {
        val callAdapter = mRxFactory.get(returnType, annotations, retrofit) as CallAdapter<Observable<*>, Observable<*>>?
        return if (callAdapter != null) DefaultApiCallAdapter(callAdapter) else null
    }

    private inner class DefaultApiCallAdapter internal constructor(internal var mDelegateAdapter: CallAdapter<Observable<*>, Observable<*>>) : CallAdapter<Observable<*>, Observable<*>> {

        override fun responseType(): Type {
            return mDelegateAdapter.responseType()
        }

        override fun adapt(call: Call<Observable<*>>): Observable<*> {
            return mDelegateAdapter.adapt(call)
                    .subscribeOn(mSubscribeOnScheduler)
                    .observeOn(mObserveOnScheduler)
        }
    }

    companion object {
        fun createWithSchedulers(subscribeOnScheduler: Scheduler, observeOnScheduler: Scheduler): DefaultApiCallAdapterFactory {
            return DefaultApiCallAdapterFactory(subscribeOnScheduler, observeOnScheduler)
        }
    }
}
