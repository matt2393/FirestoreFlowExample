package com.matdev.firestoreflowexample.model.repositories

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.matdev.firestoreflowexample.ResultPrueba
import com.matdev.firestoreflowexample.model.entities.Prueba
import com.matdev.firestoreflowexample.model.firebase.getFirestorePrueba
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PruebaRepository {
    private var listener: ListenerRegistration? = null
    suspend fun getPruebas(): Flow<ResultPrueba<Prueba>> = channelFlow {
        listener = getFirestorePrueba().addSnapshotListener { value, error ->
            if (error != null) {
                launch {
                    send(ResultPrueba.Error(error))
                }
                Log.e("PruebaListError", error.toString())
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {

                val doc = it.document.toObject(Prueba::class.java)
                doc.id = it.document.id
                Log.e("PruebaList", doc.toString())
                when(it.type) {
                    DocumentChange.Type.ADDED -> doc.type = Prueba.TYPE.ADD
                    DocumentChange.Type.MODIFIED -> doc.type = Prueba.TYPE.UPDATE
                    DocumentChange.Type.REMOVED -> doc.type = Prueba.TYPE.REMOVE
                }
                launch {
                    send(ResultPrueba.Success(doc))
                }
            }
        }
        awaitClose {
            listener?.remove()
        }
    }//.flowOn(Dispatchers.IO)

    fun removeListener() {
        listener?.remove()
    }
}