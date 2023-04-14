package com.example.dynaimage.views

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dynaimage.R
import com.example.dynaimage.databinding.AlbumCardBinding
import com.example.dynaimage.model.AlbumModelItem
import com.squareup.picasso.Picasso

class AlbumAdapter(
    private val albums: List<AlbumModelItem>,
    private var listener: RecordSelectListener
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>(){

    var recordSelectListener: RecordSelectListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val v = AlbumCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AlbumViewHolder(v)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bindItem(albums[position])
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    inner class AlbumViewHolder(private val itemBinding: AlbumCardBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(model: AlbumModelItem) {
            itemBinding.txtAlbumTitle.text = model.title
            Picasso.get()
                .load(model.thumbnailUrl)
                .fit()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(itemBinding.img)

            itemBinding.root.setOnClickListener {
                recordSelectListener.onRecordSelect(model)
            }
        }
    }

    interface RecordSelectListener {
        fun onRecordSelect(albumModel: AlbumModelItem)
    }
}