package com.app.photoweather.gallery.ui

import android.app.Activity
import android.view.View
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.app.photoweather.R
import com.bumptech.glide.Glide
import java.io.File

class GalleryViewHolder(view: View, val activity: Activity) : RecyclerView.ViewHolder(view) {

    private var imageView: ImageView? = null

    init {
        imageView = view.findViewById(R.id.gallery_item_imageView)
    }

    fun bindTo(imgFile: File, position: Int) {
        imageView?.let {
            Glide.with(it).load(imgFile).into(it)
            it.setOnClickListener {
                Navigation.findNavController(
                    activity,
                    R.id.fragment_container
                ).navigate(
                    GalleryFragmentDirections.actionGalleryFragmentToWeatherFragment(
                        position
                    )
                )
            }
        }
    }

}