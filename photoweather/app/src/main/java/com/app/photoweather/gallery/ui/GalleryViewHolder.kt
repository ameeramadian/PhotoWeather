package com.app.photoweather.gallery.ui

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.photoweather.R
import com.bumptech.glide.Glide
import java.io.File

class GalleryViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private var imageView: ImageView? = null

    init {
        imageView = view.findViewById(R.id.gallery_item_imageView)
    }

    fun binTo(imgFile: File) {
        imageView?.let {
            Glide.with(it).load(imgFile).into(it)
        }
    }

}