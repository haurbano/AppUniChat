package movil.unichat2.communication

import android.content.Context
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import movil.unichat2.utils.getSocketURL

/**
 * Created by haurbano on 13/11/2017.
 */

// This is my first intent of making a singleton in kotlin, I know the ´object´ is a singleton implementation in
// JAVA, but the singleton I need is a little different because I need to maintain a unique instance of other class.
// TODO: verify if there are a better way to implement a singleton pattern

class MySocket(context: Context) {
    companion object {
        // Variables
        val URL_UNASIGNED = "0.0.0.0"
        val SEND_MESSAGE_EMMITER = "client_message"
    }

    // Instance
    val instance : Socket by lazy { getInstance(context) }


    // Method
    fun getInstance(context: Context) : Socket{
        val socker_url = context.getSocketURL()
        val instance = IO.socket("http://"+socker_url+":3000/")
        if (!instance.connected()) instance.connect()
        return instance
    }

}
