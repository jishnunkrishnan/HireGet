package com.example.hireget

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    private lateinit var mMap: GoogleMap
    private val PERMISSION_ID = 1000

    private fun getSubCityName(lat: Double, long: Double): String {

        var cityName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        cityName = address[0].subLocality

        return cityName
    }

    private fun getCityName(lat: Double, long: Double): String {

        var cityName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        cityName = address[0].locality

        return cityName
    }

    private fun getCountryName(lat: Double, long: Double): String {

        var countryName = ""
        var geoCoder = Geocoder(this, Locale.getDefault())
        var address = geoCoder.getFromLocation(lat, long, 1)
        countryName = address[0].countryName

        return countryName
    }

    @SuppressLint("SetTextI18n")
    private fun getLastLocation() {

        if(checkPermission()) {
            if(isLocationEnabled()) {

                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->

                    val location = task.result
                    if (location == null) {

                        getNewLocation()
                    } else {

                        Log.i("Log Your coordinates La", location.latitude.toString())
                        Log.i(" Log Your coortes Lo", location.longitude.toString())
                        var subCity = getSubCityName(location.latitude, location.longitude)
                        var city = getCityName(location.latitude, location.longitude)
                        var country = getCountryName(location.latitude, location.longitude)

                        Log.i("Log Sub City", subCity)
                        Log.i("Log City", city)
                        Log.i("Log Country", country)

                        tvCurrentLocation.text = "$subCity, $city, $country"

                        val sydney = LatLng(location.latitude, location.longitude)
                        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f))

                        Toast.makeText(this, location.latitude.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else {

                Toast.makeText(this, "Please enable Location", Toast.LENGTH_LONG).show()
            }
        } else {

            requestPermission()
        }
    }

    private fun getNewLocation () {

        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient!!.requestLocationUpdates(

            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object: LocationCallback() {

        @SuppressLint("SetTextI18n")
        override fun onLocationResult(p0: LocationResult) {

            var lastLocation = p0.lastLocation
            Log.i("Log Your Loc La", lastLocation.latitude.toString())
            Log.i("Log Your Loc Lo", lastLocation.longitude.toString())

            var subCity = getSubCityName(lastLocation.latitude, lastLocation.longitude)
            var city = getCityName(lastLocation.latitude, lastLocation.longitude)
            var country = getCountryName(lastLocation.latitude, lastLocation.longitude)
            Log.i("Log Sub City", subCity)
            Log.i("Log City", city)
            Log.i("Log Country", country)

            tvCurrentLocation.text = "$subCity, $city, $country"

            val sydney = LatLng(lastLocation.latitude, lastLocation.longitude)
            //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17f))
        }
    }

    private fun checkPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED) {

            return true
        }
        return false
    }

    private fun requestPermission () {

        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ), PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {

        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i("Log Debug", "You have the permision")

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        title = "Confirm Location"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getLastLocation()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
         //Add a marker in Sydney and move the camera
        //val sydney = LatLng(33.2, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
    }
}