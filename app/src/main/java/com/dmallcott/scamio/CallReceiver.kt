package com.dmallcott.scamio

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import timber.log.Timber
import java.util.*


class CallReceiver : BroadcastReceiver() {
    //The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations

    private var lastState = TelephonyManager.CALL_STATE_IDLE
    private var callStartTime: Date? = null
    private var isIncoming: Boolean = false
    private var savedNumber: String? = null  //because the passed incoming is only valid in ringing

    lateinit var notificationManager: LocalNotificationManager

    override fun onReceive(context: Context, intent: Intent) {

        // I believe I need to initialise it every time in case the context becomes unusable
        notificationManager = LocalNotificationManager(context,
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)

        //We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.action == "android.intent.action.PHONE_STATE") {
            savedNumber = intent.extras!!.getString("android.intent.extra.PHONE_NUMBER")
        } else if (intent.action == "android.intent.action.PROCESS_OUTGOING_CALLS") {
            val number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
            val state = let {
                when (intent.getStringExtra(TelephonyManager.EXTRA_STATE)) {
                    TelephonyManager.EXTRA_STATE_IDLE -> TelephonyManager.CALL_STATE_IDLE
                    TelephonyManager.EXTRA_STATE_OFFHOOK -> TelephonyManager.CALL_STATE_OFFHOOK
                    TelephonyManager.EXTRA_STATE_RINGING -> TelephonyManager.CALL_STATE_RINGING
                    else -> 0
                }
            }

            onCallStateChanged(state, number)
        }
    }

    //Deals with actual events

    //Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
    //Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
    private fun onCallStateChanged(state: Int, number: String?) {
        if (lastState == state) {
            //No change, debounce extras
            return
        }

        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncoming = true
                callStartTime = Date()
                savedNumber = number
                //onIncomingCallStarted(context, number, callStartTime)
                Timber.d("Incoming call started")

                // If it's spam -> Show notification
                notificationManager.notifyIncomingCall()
            }
            TelephonyManager.CALL_STATE_OFFHOOK ->
                // Transition of ringing-> off hook are pickups of incoming calls.  Nothing done on them
                if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncoming = false
                    callStartTime = Date()
                    //onOutgoingCallStarted(context, savedNumber, callStartTime)

                    Timber.d("Outgoing call started")
                }
            TelephonyManager.CALL_STATE_IDLE -> {

                notificationManager.hideIncomingCall()

                // Went to idle ->  this is the end of a call.  What type depends on previous state(s)
                when {
                    lastState == TelephonyManager.CALL_STATE_RINGING -> //Ring but no pickup-  a miss
                        Timber.d("Missed call started")
                    isIncoming -> // Destroy nitification if any
                        Timber.d("Incoming call ended")
                    else -> // Destroy nitification if any
                        Timber.d("Outgoing call ended")
                }
            }
        }
        lastState = state
    }
}
