package com.matdev.firestoreflowexample.model.entities

import com.google.firebase.firestore.Exclude

data class Prueba(@Exclude var id: String = "", var texto: String = "", @Exclude var type: TYPE = TYPE.ADD) {
    enum class TYPE {
        ADD, UPDATE, REMOVE
    }

    override fun equals(other: Any?): Boolean {
        return (other as Prueba).id == id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + texto.hashCode()
        result = 31 * result + type.hashCode()
        return result
    }
}