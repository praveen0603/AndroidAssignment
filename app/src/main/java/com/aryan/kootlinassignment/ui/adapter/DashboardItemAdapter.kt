package com.aryan.kootlinassignment.ui.adapter

import com.aryan.kootlinassignment.databinding.ItemListBinding
import com.aryan.kootlinassignment.model.DataModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aryan.kootlinassignment.data.RowItem
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class DashboardItemAdapter(private val context: Context, private val lstItems : MutableList<RowItem>): RecyclerView.Adapter<DashboardItemAdapter.ItemHolder>() {

    class ItemHolder(private val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(binding.root.context))
        }

        fun setData(item:RowItem){
            item.apply {
                binding.tvTitle.text = title
                binding.tvDetails.text = description
                if (!imageUrl.isNullOrEmpty()) {
                    ImageLoader.getInstance().displayImage(imageUrl, binding.ivItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ItemListBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return lstItems.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        lstItems[position].apply {
            holder.setData(this)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}