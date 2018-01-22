package com.dmallcott.scamio

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val reference = FirebaseDatabase.getInstance().reference

        val phone = Phone("123456", 12, 20)
        reference.child("numbers").child(phone.number).setValue(phone)
    }
}
