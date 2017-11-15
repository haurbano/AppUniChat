package movil.unichat2.utils

import android.content.Context
import android.content.SharedPreferences
import movil.unichat2.communication.MySocket


/**
 * Created by haurbano on 13/11/2017.
 */

fun Context.setSocketURL(url : String){
    val editor = this.getSharedPreferences("MY_PREFERENCE",Context.MODE_PRIVATE).edit()
    editor.putString("SOCKET_URL",url).commit()
}

fun Context.getSocketURL() : String{
    val socket_url = this.getSharedPreferences("MY_PREFERENCE",Context.MODE_PRIVATE)
            .getString("SOCKET_URL", MySocket.URL_UNASIGNED)
    return socket_url
}

fun Context.getMyPreference(): SharedPreferences{
    return this.getSharedPreferences("MY_PREFERENCE",Context.MODE_PRIVATE)
}

fun Context.getUserName() : String{
    return this.getSharedPreferences("MY_PREFERENCE",Context.MODE_PRIVATE).getString("USERNAME","none")
}
fun Context.setUserName(username : String){
    val editor =  this.getSharedPreferences("MY_PREFERENCE",Context.MODE_PRIVATE).edit()
    editor.putString("USERNAME",username).commit()
}

