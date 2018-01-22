package com.dmallcott.scamio

import com.google.firebase.database.FirebaseDatabase



class FirebaseManager(var database: FirebaseDatabase) {

    fun isScam(phoneNumber: String): Boolean {
        val phone = database.reference.child(phoneNumber)


        return false
    }
}