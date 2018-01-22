package com.dmallcott.scamio

import com.google.firebase.database.Exclude

data class Phone(@get:Exclude val number: String, val scamVotes: Int, val totalVotes: Int)