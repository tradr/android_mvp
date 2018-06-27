package com.tradr.dagger.modules

import android.app.Application
import android.content.Context
import com.tradr.TradrApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: TradrApplication)  {

    //////////////////////////////////////////////////
    //////////////////// Android /////////////////////
    //////////////////////////////////////////////////
    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return this.application
    }

    @Provides
    @Singleton
    internal fun providesWhistleApplication(): TradrApplication {
        return this.application
    }

    @Provides
    @Singleton
    internal fun providesApplicationContext(): Context {
        return application.applicationContext
    }

}