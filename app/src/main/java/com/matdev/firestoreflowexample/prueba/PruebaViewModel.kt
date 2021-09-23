package com.matdev.firestoreflowexample.prueba

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matdev.firestoreflowexample.ResultPrueba
import com.matdev.firestoreflowexample.model.entities.Prueba
import com.matdev.firestoreflowexample.model.repositories.PruebaRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PruebaViewModel(private val pruebaRepository: PruebaRepository = PruebaRepository()): ViewModel() {
    val prueba = MutableLiveData<Prueba>()
    val error = MutableLiveData<Throwable>()

    fun getPruebas() {
        viewModelScope.launch {
            pruebaRepository.getPruebas().collect {
                when(it) {
                    is ResultPrueba.Success -> prueba.value = it.data!!
                    is ResultPrueba.Error -> error.value = it.error
                }
            }
        }
    }

    fun removeListener() {
        pruebaRepository.removeListener()
    }
}