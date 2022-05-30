package com.aaron.jmdns

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetAddress
import javax.jmdns.JmDNS
import javax.jmdns.ServiceEvent
import javax.jmdns.ServiceListener

class MainViewModel: ViewModel(), ServiceListener {

    fun discover() {
        Log.d("TEST", "[test] discover")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val jmdns: JmDNS = JmDNS.create(InetAddress.getLocalHost())
                jmdns.addServiceListener("_http._tcp.local.", this@MainViewModel)
            }
        }
    }

    override fun serviceAdded(event: ServiceEvent?) {
        Log.d("TEST", "[test] serviceAdded - $event")
    }

    override fun serviceRemoved(event: ServiceEvent?) {
        Log.d("TEST", "[test] serviceRemoved - $event")
    }

    override fun serviceResolved(event: ServiceEvent?) {
        Log.d("TEST", "[test] serviceResolved - event: $event")
        event?.let { e ->
            Log.d("TEST", "[test] serviceResolved - name: ${e.name}")
            Log.d("TEST", "[test] serviceResolved - type: ${e.type}")
            event.info.inet4Addresses.forEach {
                Log.d("TEST", "[test] serviceResolved - \thostAddress: ${it.hostAddress}")
            }
        }
    }
}