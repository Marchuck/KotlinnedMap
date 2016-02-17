package pl.marchuck.majormallstrikesback.service

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

/**
 * Created by Łukasz Marczak

 * @since 14.02.16
 */
class BroadcastReceiver : android.content.BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("CallReceiver", "onReceive() called with: context = [$context],\n intent = [$intent]")

        val message = intent.action
        Toast.makeText(context.applicationContext, message, Toast.LENGTH_LONG).show()
    }
}

