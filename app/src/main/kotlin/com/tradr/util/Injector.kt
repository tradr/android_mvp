package com.tradr.util

import android.content.Context
import com.tradr.TradrApplication
import com.tradr.dagger.components.BaseComponent

class Injector {
    private fun Injector() {
        throw AssertionError("Do not create instances of this class")
    }

    companion object {
        @SuppressWarnings("ResourceType")  // Using a custom service, so ignore the warning
        fun obtain(context: Context): BaseComponent {
            return context.getSystemService(TradrApplication.INJECTOR_SERVICE) as BaseComponent
        }

        fun matchesService(name: String): Boolean {
            return name == TradrApplication.INJECTOR_SERVICE
        }
    }
}