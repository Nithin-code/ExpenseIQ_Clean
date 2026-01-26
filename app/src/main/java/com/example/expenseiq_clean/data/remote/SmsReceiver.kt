package com.example.expenseiq_clean.data.remote

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.example.expenseiq_clean.domain.usecases.ParseSmsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject

class SmsReceiver : BroadcastReceiver(){

    val parseSmsUseCase : ParseSmsUseCase by inject(ParseSmsUseCase::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent?.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        val pendingIntent = goAsync()

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                messages.forEach { smsMessage ->
                    val sender = smsMessage.originatingAddress ?: return@forEach
                    val body = smsMessage.messageBody ?: return@forEach
                    // now pass the data to logic
                    parseSmsUseCase.execute(sender,body)
                }
            }catch (e : Exception){
                Log.e("Exception","Error while parsing message ${e.message}")
            }finally {
                pendingIntent.finish()
            }
        }
    }
}