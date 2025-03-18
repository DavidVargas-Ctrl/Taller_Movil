package com.example.clase25febrero

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.clase25febrero.databinding.ActivityGpsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlin.math.roundToInt

class gps : AppCompatActivity() {
    lateinit var binding: ActivityGpsBinding
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    lateinit var mLocationRequest: LocationRequest
    lateinit var mLocationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGpsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mLocationRequest = createLocationRequest()
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                Log.i("LOCATION", "Location update in the callback: $location")
                if (location != null) {
                    binding.textView7.text = location.altitude.toString()
                    binding.textView8.text = location.latitude.toString()
                    binding.textView9.text = location.longitude.toString()


                    val distancia = distance(location.latitude, location.longitude)
                    binding.textView10.text = "\nDistancia: $distancia km"
                }
            }
        }


        // Verificar permisos
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                MIscelanius.PERMISSION_LOCATION
            )
            return
        }

        obtenerUbicacion()
        startLocationUpdates()


    }

    private fun createLocationRequest(): LocationRequest =
        // New builder
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,10000).apply {
            setMinUpdateIntervalMillis(5000)
        }.build()


    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion() {
        mFusedLocationProviderClient.lastLocation.addOnSuccessListener(this) { location ->
            if (location != null) {
                val latitud = "Latitud: ${location.latitude}"
                val longitud = "Longitud: ${location.longitude}"
                val altitud = "Altitud: ${location.altitude}"

                binding.textView7.text = latitud
                binding.textView8.text = longitud
                binding.textView9.text = altitud
            } else {
                binding.textView7.text = "No se pudo obtener la ubicación"
                binding.textView8.text = ""
                binding.textView9.text = ""
            }
        }.addOnFailureListener {
            binding.textView7.text = "Error al obtener ubicación"
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mFusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                null
            )
        }
    }
    private fun stopLocationUpdates() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MIscelanius.PERMISSION_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerUbicacion()
            } else {
                binding.textView7.text = "Permiso de ubicación denegado"
            }
        }
    }


    fun distance(lat2: Double, long2: Double): Double {
        val lat1=4.70145
        val long1=-74.14606
        val RADIUS_OF_EARTH_KM = 6378.0
        val latDistance = Math.toRadians(lat1 - lat2)
        val lngDistance = Math.toRadians(long1 - long2)
        val a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val result = RADIUS_OF_EARTH_KM * c
        return (result * 100.0).roundToInt() / 100.0
    }
}
