package com.example.mvvm_note_app.feature_note.presentation.note_list

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner: AndroidJUnitRunner() {

    //override by writing new... to override our Application class for the test
    override fun newApplication( cl: ClassLoader?, className: String?, context: Context? ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}