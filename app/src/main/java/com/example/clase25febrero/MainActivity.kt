package com.example.clase25febrero

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clase25febrero.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener,
    AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMainBinding
    var item = "0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner.onItemSelectedListener = this


        val json = JSONObject(MIscelanius.loadJSONFromAsset(baseContext,"paises.json"))
        val paisesJsonArray = json.getJSONArray("paises")
        val nombresPaises = arrayOfNulls<String>(paisesJsonArray.length())
        for (i in 0 until paisesJsonArray.length()) {
            val jsonObject = paisesJsonArray.getJSONObject(i)
            nombresPaises[i] = jsonObject.getString("nombre_pais")
        }

        val adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, nombresPaises)
        binding.lista.adapter = adapter


        binding.button2.setOnClickListener{
            startActivity(Intent(this,PantallaWeb::class.java))
        }
        binding.button.setOnClickListener{
            if(item != "0") {
                Toast.makeText(baseContext, item, Toast.LENGTH_LONG).show()
                startActivity(Intent(this,PantallaFrame::class.java))
            }
            else{
                Toast.makeText(baseContext, "No se ha seleccionado una opcion", Toast.LENGTH_LONG).show()
            }
        }

        when {
            ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED -> {
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                this, android.Manifest.permission.READ_CONTACTS) -> {
            }
            else -> {
                pedirPermiso(this, "android.Manifest.permission.READ_CONTACTS",
                    "", MIscelanius.PERMISSION_READ_CONTACTS)
            }
        }
    }

    private fun pedirPermiso(context: Activity, permiso: String, justificacion: String, idCode: Int){
        if (ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(permiso), idCode)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(baseContext, "Seleccion: " + parent?.selectedItemId + "," + parent?.selectedItem
            , Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Toast.makeText(baseContext, "Seleccion: " + parent?.selectedItemId + "," + parent?.selectedItem
            , Toast.LENGTH_LONG).show()
        item = parent?.selectedItem.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}