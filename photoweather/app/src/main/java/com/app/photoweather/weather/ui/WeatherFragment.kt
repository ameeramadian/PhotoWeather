package com.app.photoweather.weather.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.app.photoweather.R
import com.app.photoweather.camera.CameraFragment.Companion.EXTENSION_WHITELIST
import com.app.photoweather.main.MainActivity
import com.app.photoweather.weather.model.WeatherResponse
import com.app.photoweather.weather.viewModel.WeatherResponseViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

interface iWeatherView {
    fun showLoading()
    fun hideLoading()
}

class WeatherFragment : Fragment(), iWeatherView {

    private val args: WeatherFragmentArgs by navArgs()
    private val weatherViewModel: WeatherResponseViewModel by viewModels()

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var mediaList: MutableList<File>? = null
    private var newImageContainerLayout: ConstraintLayout? = null
    private var oldImageView: ImageView? = null
    private var weatherConditionImageView: ImageView? = null
    private var shareButton: ImageButton? = null
    private var tempTextView: TextView? = null
    private var weatherConditionTextView: TextView? = null
    private var locationTextView: TextView? = null
    private var progressBar: ProgressBar? = null
    private var lon: Double? = null
    private var lat: Double? = null
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = args.position
        getMediaList()
        getLocationClient()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        newImageContainerLayout = view.findViewById(R.id.new_img_constraintlayout)
        oldImageView = view.findViewById(R.id.captured_imageview)
        shareButton = view.findViewById(R.id.share_button)
        tempTextView = view.findViewById(R.id.temperature_textView)
        weatherConditionTextView = view.findViewById(R.id.weather_condition_textView)
        weatherConditionImageView = view.findViewById(R.id.weather_condition_imageView)
        locationTextView = view.findViewById(R.id.location_textView)
        progressBar = view.findViewById(R.id.progressBar)
        oldImageView?.let { oldImageView ->
            position?.let { position ->
                Glide.with(oldImageView).load(mediaList?.get(position)?.absoluteFile)
                    .into(oldImageView)
            }
        }
        weatherViewModel.weatherResponse.observe(viewLifecycleOwner, Observer {
            setWeatherData(it)
        })
        getLocation()
        shareButton?.let { saveButton ->
            newImageContainerLayout?.let { newImageContainerLayout ->
                saveButton.setOnClickListener {
                    val img = convertViewToBitmap(newImageContainerLayout)
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_STREAM, getBitmapFromView(img))
                    startActivity(Intent.createChooser(intent, "share img"))
                }
            }
        }
        return view
    }

    /** Helper Methods **/

    private fun getMediaList() {
        val outputDirectory =
            MainActivity.getOutputDirectory(
                requireContext()
            )
        val rootDirectory = File(outputDirectory.absolutePath)
        mediaList = rootDirectory.listFiles { file ->
            EXTENSION_WHITELIST.contains(file.extension.toUpperCase(Locale.ROOT))
        }?.sortedDescending()?.toMutableList() ?: mutableListOf()
    }

    private fun getLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    private fun getLocation() {
        fusedLocationClient?.lastLocation
            ?.addOnSuccessListener { location: Location? ->
                lat = location?.latitude
                lon = location?.longitude
                lat?.let { lat ->
                    lon?.let { lon ->
                        weatherViewModel.fetchWeatherData(this, lat, lon)
                        locationTextView?.let {
                            it.text = getCity(lat, lon)
                        }
                    }
                }
            }
    }

    private fun setWeatherData(weatherResponse: WeatherResponse) {
        tempTextView?.text = weatherResponse.main.temp.toInt().toString()
        weatherConditionTextView?.text = weatherResponse.weather[0].description
        val imgURL = getImageURL(weatherResponse.weather[0].icon)
        weatherConditionImageView?.let {
            Glide.with(this).load(imgURL).into(it)
        }
    }

    private fun getImageURL(path: String): String {
        return "http://openweathermap.org/img/wn/${path}@2x.png"
    }

    private fun convertViewToBitmap(v: ConstraintLayout): Bitmap? {
        val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.RGB_565)
        val c = Canvas(b)
        v.draw(c)
        b.copy(Bitmap.Config.ARGB_8888, true)
        return b
    }

    private fun getBitmapFromView(bmp: Bitmap?): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                requireContext().externalCacheDir,
                System.currentTimeMillis().toString() + ".jpg"
            )

            val out = FileOutputStream(file)
            bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }

    private fun getCity(latitude: Double, longitude: Double): String {
        val addresses: List<Address>
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )
        val address: String = addresses[0]
            .getAddressLine(0)
        val city: String = addresses[0].locality
        val country: String = addresses[0].countryName
        return "${city}, ${country}"
    }

    override fun showLoading() {
        progressBar?.visibility = VISIBLE
        progressBar?.animate()

    }

    override fun hideLoading() {
        progressBar?.visibility = GONE
    }

}