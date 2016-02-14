package pl.marchuck.majormallstrikesback.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * Created by Łukasz Marczak

 * @since 14.02.16
 */
class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val millis = System.currentTimeMillis();
        Log.d(TAG, "onReceive() called with: context = [$context],\n intent = [$intent]")

        val message = intent.action

        Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG).show()
        val time = System.currentTimeMillis() - millis;
        Log.d(TAG, "time elapsed : $time")
    }


    companion object {
        val TAG = "CallReceiver";
    }
}

