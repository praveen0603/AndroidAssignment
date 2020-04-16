package `in`.aryan.kootlinassignment.ui.dashboard

import `in`.aryan.kootlinassignment.R
import `in`.aryan.kootlinassignment.databinding.ActivityMainBinding
import `in`.aryan.kootlinassignment.model.DataModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class DashboardActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DashboardViewModel

    lateinit var adapter: DashboardItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = DashboardViewModel();
        viewModel.isLoading.postValue(false)

        adapter = DashboardItemAdapter(this@DashboardActivity)
        binding.rvItems.adapter = adapter
        viewModel.getListData()

        attachObserver()
    }

    private fun attachObserver() {
        viewModel.apiResponse.observe(this@DashboardActivity, Observer {
            it.let {
                if (it is DataModel) {
                    adapter.apply {
                        addItems(it.rows)
                    }
                }
            }
        })
    }

    override fun onRefresh() {
        viewModel.getListData()
    }
}
