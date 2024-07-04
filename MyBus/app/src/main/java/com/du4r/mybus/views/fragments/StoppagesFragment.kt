package com.du4r.mybus.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.du4r.mybus.R
import com.du4r.mybus.models.Stoppage
import com.du4r.mybus.utils.BitmapHelper
import com.du4r.mybus.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class StoppagesFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingTextView: TextView
    private lateinit var viewModel: MainViewModel
    private lateinit var mapFragment: SupportMapFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        mapFragment.getMapAsync(this)
        viewModel.getParadas(" ")
    }


    override fun onResume() {
        super.onResume()

        viewModel.stoppages.observe(viewLifecycleOwner) { s ->
            s.forEach {
                addMarker(it)
            }
        }

    }

    private fun addMarker(stoppage: Stoppage) {

        val pos = LatLng(stoppage.py, stoppage.px)

        map.addMarker(
            MarkerOptions()
                .title(stoppage.np)
                .snippet(stoppage.ed)
                .position(pos)
                .icon(
                    BitmapHelper.vectorToBitmap(
                        this.requireContext(),
                        R.drawable.baseline_fmd_good_24
                    )
                )
        )
        progressBar.visibility = View.GONE
        loadingTextView.visibility = View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.stoppages_fragment, container, false)
        progressBar = view.findViewById(R.id.progress_stoppage)
        loadingTextView = view.findViewById(R.id.tv_loading_stoppage)
        mapFragment = childFragmentManager.findFragmentById(R.id.map_frag_stoppages) as SupportMapFragment
        return view
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.setOnMapLoadedCallback {
            //iniciar o mapa no principal terminal rodoviario de sp -> TERMINAL TIETE
            val tiete = LatLng(-23.5173055, -46.6270004)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(tiete, 15f))
        }
    }
}