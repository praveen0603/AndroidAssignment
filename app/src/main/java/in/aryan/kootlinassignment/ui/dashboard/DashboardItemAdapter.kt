package `in`.aryan.kootlinassignment.ui.dashboard

import `in`.aryan.kootlinassignment.databinding.ItemListBinding
import `in`.aryan.kootlinassignment.model.DataModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader

class DashboardItemAdapter(private val context: Context, private val lstItems : MutableList<DataModel.Row>): RecyclerView.Adapter<DashboardItemAdapter.ItemHolder>() {

    class ItemHolder(private val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root) {
        fun setData(item:DataModel.Row){
            item.apply {
                binding.tvTitle.text = title
                binding.tvDetails.text = description
                if (imageHref != null) {
                    ImageLoader.getInstance().displayImage(imageHref.toString(), binding.ivItem)
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
        holder.setData(lstItems[position])
    }


}