package com.matdev.firestoreflowexample.model.firebase

import com.google.firebase.firestore.FirebaseFirestore


fun getFirestorePrueba() = FirebaseFirestore.getInstance()
    .collection("Prueba")
