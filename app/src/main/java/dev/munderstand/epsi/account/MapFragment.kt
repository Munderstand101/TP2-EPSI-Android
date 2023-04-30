package dev.munderstand.epsi.account

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dev.munderstand.epsi.R
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val STORES_URL = "https://www.ugarit.online/epsi/stores.json"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class MapFragment : Fragment() {

    private lateinit var googleMap: GoogleMap

    @SuppressLint("MissingPermission")
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                googleMap.isMyLocationEnabled = true
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                googleMap.isMyLocationEnabled = true
            }
            else -> {
                // No location access granted.
            }
        }
    }

    private fun fetchStores() {
        val storesRequest = JsonObjectRequest(
            Request.Method.GET,
            STORES_URL,
            null,
            { response ->
                val stores = response.getJSONArray("stores")
                val builder = LatLngBounds.Builder() // Create a builder to include all markers
                for (i in 0 until stores.length()) {
                    val store = stores.getJSONObject(i)
                    val name = store.getString("name")
                    val address = store.getString("address")
                    val longitude = store.getDouble("longitude")
                    val latitude = store.getDouble("latitude")
                    val location = LatLng(latitude, longitude)
                    val marker = googleMap.addMarker(
                        MarkerOptions().position(location).title(name).snippet(address)
                    )
                    marker?.tag = store // Attach the store data to the marker tag
                    builder.include(location) // Include the marker in the builder
                    googleMap.setOnInfoWindowClickListener { clickedMarker ->
                        val store = clickedMarker.tag as? JSONObject
                        if (store != null) {
                            val intent = Intent(requireContext(), StoreDetailsActivity::class.java)
                            intent.putExtra(
                                "store",
                                store.toString()
                            ) // Pass the store data as a string extra
                            startActivity(intent)
                        }
                    }
                    marker?.showInfoWindow() // Show the tooltip when the marker is clicked
                }
                val bounds = builder.build() // Build the bounds object from all markers
                val padding = resources.getDimensionPixelSize(R.dimen.map_padding) // Get padding from resources
                val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding) // Create a camera update to fit all markers with padding
                googleMap.moveCamera(cameraUpdate) // Move the camera to fit all markers
            },
            { error ->
                Toast.makeText(
                    requireContext(),
                    "Error fetching stores: ${error.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        )

        val queue = Volley.newRequestQueue(requireContext())
        queue.add(storesRequest)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(callback)
        return view
    }

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
        fetchStores()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}