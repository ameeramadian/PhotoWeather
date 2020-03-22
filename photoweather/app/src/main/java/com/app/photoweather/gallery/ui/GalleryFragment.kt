package com.app.photoweather.gallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.app.photoweather.R
import com.app.photoweather.camera.CameraFragment
import com.app.photoweather.weather.ui.WeatherFragmentArgs
import java.io.File
import java.util.*

class GalleryFragment : Fragment() {
    private var mediaList: MutableList<File>? = null
    private var recyclerView: RecyclerView? = null
    private val args: WeatherFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootDirectory = File(args.rootDirectory)
        mediaList = rootDirectory.listFiles { file ->
            CameraFragment.EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
        }?.sortedDescending()?.toMutableList() ?: mutableListOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        recyclerView = view.findViewById(R.id.gallery_recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GalleryAdapter(mediaList, requireContext())
        recyclerView?.adapter = adapter
    }
}