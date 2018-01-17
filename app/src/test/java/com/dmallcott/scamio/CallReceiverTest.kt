package com.dmallcott.scamio

import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


@RunWith(RobolectricTestRunner::class)
class CallReceiverTest {

    lateinit var callReceiver: CallReceiver
    lateinit var context: Context

    val notificationManager = mock(LocalNotificationManager::class.java)

    val phoneNumber = "0123456789"
    val phoneStateAction = "android.intent.action.PHONE_STATE"
    val processCallAction = "android.intent.action.PROCESS_OUTGOING_CALLS"

    @Before
    fun setUp() {
        context = RuntimeEnvironment.application

        callReceiver = CallReceiver()
        callReceiver.notificationManager = notificationManager
    }

    @Test
    fun `When recieving phone state, we store the phone number`() {
        val intent = Intent(phoneStateAction)
        intent.putExtra("android.intent.extra.PHONE_NUMBER", phoneNumber)
        callReceiver.onReceive(context, intent)
    }

    @Test
    fun `When processing phone calls, `() {
        val intent = Intent(processCallAction)
        intent.putExtra(TelephonyManager.EXTRA_STATE, TelephonyManager.EXTRA_STATE_RINGING)
        intent.putExtra(TelephonyManager.EXTRA_INCOMING_NUMBER, phoneNumber)
        callReceiver.onReceive(context, intent)

        verify(notificationManager, times(1)).notifyIncomingCall()
    }
}