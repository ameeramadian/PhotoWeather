package com.app.photoweather.gallery.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.photoweather.R
import java.io.File

class GalleryAdapter(private val mediaList: MutableList<File>?, private val context: Context) :
    RecyclerView.Adapter<GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflatedView =
            LayoutInflater.from(context).inflate(R.layout.layout_gallery_item, parent, false)
        return GalleryViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        mediaList?.let {
            return it.size
        } ?: run {
            return 0
        }

    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        mediaList?.let {
            holder.binTo(mediaList[position].absoluteFile)
        }
    }
}