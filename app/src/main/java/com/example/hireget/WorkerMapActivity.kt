package com.example.hireget

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.tvCurrentLocation
import kotlinx.android.synthetic.main.activity_worker_map.*
import java.util.*

class WorkerMapActivity : AppCompatActivity(), OnMapReadyCallback {

    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private lateinit var mMap: GoogleMap
    var currentLocation: Location? = null
    var currentMarker: Marker? = null

    var subCity: String? = null
    var city: String? = null
    var country: String? = null
    var markerMoved: Boolean = false

    private fun getSubCityName(lat: Double, long: Double): String {

        var cityName: String = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        cityName = address[0]?.subLocality.toString()
        return cityName
    }

    private fun getCityName(lat: Double, long: Double): String {

        var cityName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        cityName = address[0]?.locality.toString()
        return cityName
    }

    private fun getCountryName(lat: Double, long: Double): String {

        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        countryName = address[0]?.countryName.toString()
        return countryName
    }

    private fun fetchLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1000
            )
            return
        }
        val task = fusedLocationProviderClient?.lastLocation
        task?.addOnSuccessListener { location ->

            if (location != null) {

                this.currentLocation = location
                val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {

            1000 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                fetchLocation()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_map)

        title = "Confirm Location"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.workerBlue
                )
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(
                applicationContext,
                R.color.workerBlueStatusBar
            )
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        /* val mapFragment = supportFragmentManager
             .findFragmentById(R.id.map) as SupportMapFragment
         mapFragment.getMapAsync(this) */
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        //Add a marker in Sydney and move the camera
//        val sydney = LatLng(33.2, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))

        val latLng = LatLng(currentLocation?.latitude!!, currentLocation?.longitude!!)
        drawMarker(latLng)
        cityUpdate(latLng)
        mMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {

            override fun onMarkerDrag(p0: Marker?) {
                Log.i("onMarkerDrag", p0.toString())
            }

            override fun onMarkerDragStart(p0: Marker?) {

                Log.i("onMarkerStart", p0.toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onMarkerDragEnd(p0: Marker?) {

                if (currentMarker != null) {

                    currentMarker?.remove()
                }
                val newLatLng = LatLng(p0?.position!!.latitude, p0.position.longitude)
                //drawMarker(newLatLng)
                subCity = getSubCityName(p0?.position!!.latitude, p0.position.longitude)
                city = getCityName(p0?.position!!.latitude, p0.position.longitude)
                country = getCountryName(p0?.position!!.latitude, p0.position.longitude)
                tvCurrentLocation.text = "$subCity, $city, $country"
                markerMoved = true
            }
        })

        workerMapButton.setOnClickListener {

            if (markerMoved) {
                val i = Intent(this@WorkerMapActivity, WorkerProfileActivity::class.java)
                i.putExtra("locSubCity", subCity)
                i.putExtra("locCity", city)
                i.putExtra("locCountry", country)
                i.putExtra("regWindow", true)
                startActivity(i)
            } else {

                cityUpdateIntent(latLng)
            }
        }
    }

    private fun cityUpdateIntent(latLng: LatLng) {
        val subCity = getSubCityName(latLng.latitude, latLng.longitude)
        val city = getCityName(latLng.latitude, latLng.longitude)
        val country = getCountryName(latLng.latitude, latLng.longitude)

        val intent = Intent(this@WorkerMapActivity, WorkerProfileActivity::class.java)
        intent.putExtra("locSubCity", subCity)
        intent.putExtra("locCity", city)
        intent.putExtra("locCountry", country)
        intent.putExtra("regWindow", true)
        startActivity(intent)
    }

    @SuppressLint("SetTextI18n")
    private fun cityUpdate(latLng: LatLng) {
        val subCity = getSubCityName(latLng.latitude, latLng.longitude)
        val city = getCityName(latLng.latitude, latLng.longitude)
        val country = getCountryName(latLng.latitude, latLng.longitude)
        tvCurrentLocation.text = "$subCity, $city, $country"
    }


    private fun drawMarker(latLng: LatLng) {

        //val marker = MarkerOptions().position(latLng).title("Drag Me").snippet(getAddress(latLng.latitude, latLng.longitude)).draggable(true)
        val marker = MarkerOptions().position(latLng).title("Drag Me").draggable(true)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        mMap.addMarker(marker)
        currentMarker = mMap.addMarker(marker)
        currentMarker?.showInfoWindow()
    }


    /*  private fun getAddress(lat: Double, lon: Double): String {

          val geoCoder = Geocoder(this, Locale.getDefault())
          val address = geoCoder.getFromLocation(lat, lon, 1)
          return address[0].getAddressLine(0).toString()
      } */
}