package net.softglobe.androidservices

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Context
import android.os.Build

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS), 200)
        }

        findViewById<Button>(R.id.btn_start_service).setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            intent.action = Actions.START.toString()
            if (prefs.getString("componentName", null) == null) {
                val serviceComponentname = startService(intent)
                val editor = prefs.edit()
                editor.putString("componentName", serviceComponentname.toString())
                editor.apply()
            }
        }

        findViewById<Button>(R.id.btn_stop_service).setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            intent.action = Actions.STOP.toString()
            if (prefs.getString("componentName", null) != null) {
                startService(intent)
                val editor = prefs.edit()
                editor.clear()
                editor.apply()
            }
        }
    }
}