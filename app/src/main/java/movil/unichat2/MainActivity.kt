package movil.unichat2

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_main.*
import movil.unichat2.communication.MySocket
import movil.unichat2.utils.getMyPreference
import movil.unichat2.utils.getSocketURL
import movil.unichat2.utils.getUserName
import movil.unichat2.utils.setSocketURL
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    lateinit var socket : Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init(){
        if (this.getSocketURL() == MySocket.URL_UNASIGNED) setupSocketURL() else socket = MySocket(this).instance
        this.getMyPreference().registerOnSharedPreferenceChangeListener(this)
        btn_send_message.setOnClickListener { sendMessage(edit_message.text.toString()) }
    }

    fun sendMessage(msg : String){
        socket.emit(MySocket.SEND_MESSAGE_EMMITER,msg)
    }

    fun setupSocketURL(){
        alert {
            title = "Configurar IP"
            customView {
                val edt = editText{ hint = "Ingrese IP" }
                yesButton { this@MainActivity.setSocketURL(edt.text.toString())}
            }
        }.show()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key.equals("SOCKET_URL")){
            socket = MySocket(this).instance
        }
    }

    override fun onDestroy() {
        if(socket != null) socket.disconnect()
        super.onDestroy()
    }

    override fun onBackPressed() {
        Toast.makeText(this,"Si sale se desconectará",Toast.LENGTH_LONG).show()
    }

    //region Menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.change_ip_menu -> setupSocketURL()
            R.id.info_ip_menu -> showInfo()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showInfo(){
        alert {
            customView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(30)
                    textView{ text = "Usuario: ${this@MainActivity.getUserName()}" }
                    textView{ text = "URL: ${this@MainActivity.getSocketURL()}" }
                    textView{ text = "Conectado: ${if (socket == null) "socket null" else "${socket.connected()}" } " }
                }
            }
            yesButton {}
        }.show()
    }

    //endregion
}
