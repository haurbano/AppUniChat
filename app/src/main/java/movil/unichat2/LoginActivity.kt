package movil.unichat2

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.nkzawa.socketio.client.Ack
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_login.*
import movil.unichat2.communication.MySocket
import movil.unichat2.utils.getMyPreference
import movil.unichat2.utils.getSocketURL
import movil.unichat2.utils.setSocketURL
import movil.unichat2.utils.setUserName
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var socket : Socket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()

    }

    fun init(){
        if (this.getSocketURL() == MySocket.URL_UNASIGNED) setupSocketURL() else socket = MySocket(this).instance
        this.getMyPreference().registerOnSharedPreferenceChangeListener(this)
        btn_register_client.setOnClickListener {  registerClient(edit_username.text.toString()) }
    }

    fun setupSocketURL(){
        alert {
            title = "Configurar IP"
            customView {
                val edt = editText{ hint = "Ingrese IP" }
                yesButton { this@LoginActivity.setSocketURL(edt.text.toString())}
            }
        }.show()
    }

    fun registerClient(username : String){
        if (socket == null) socket = MySocket(this).instance
        socket.emit("register_client",username, Ack(
                fun (args){
                    this.setUserName(username)
                    startActivity(intentFor<MainActivity>())
                }
        ))
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals("SOCKET_URL")){
            socket = MySocket(this).instance
        }
    }

}
