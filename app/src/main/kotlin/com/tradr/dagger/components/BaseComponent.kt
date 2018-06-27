package com.tradr.dagger.components

import com.tradr.TradrApplication
import com.tradr.dagger.modules.ApplicationModule
import com.tradr.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface BaseComponent {
    fun inject(application: TradrApplication)

    fun inject(activity: MainActivity)
}