package `in`.aryan.kootlinassignment.ui.dashboard

import `in`.aryan.kootlinassignment.databinding.ItemListBinding
import `in`.aryan.kootlinassignment.model.DataModel
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DashboardItemAdapter(val context: Context): RecyclerView.Adapter<DashboardItemAdapter.ItemHolder>() {

    private var lstItems : MutableList<DataModel.Row> = ArrayList()

    fun addItems(lstItems: MutableList<DataModel.Row>){
        lstItems.addAll(lstItems)
        notifyDataSetChanged()
    }

    class ItemHolder(val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return lstItems.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

    }


}