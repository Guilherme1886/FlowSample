package com.gui.toledo.flowsample

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    suspend fun generateFlow() = flow {
        var count = 0
        while (true) {
            delay(1000)
            if (count > 10) {
                throw Exception()
            }
            emit(count++)
        }
    }
}