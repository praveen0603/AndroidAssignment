package `in`.aryan.kootlinassignment.ui.dashboard

import `in`.aryan.kootlinassignment.R
import `in`.aryan.kootlinassignment.databinding.ActivityMainBinding
import `in`.aryan.kootlinassignment.model.DataModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.*

class DashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DashboardViewModel

    lateinit var adapter: DashboardItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = DashboardViewModel();
        viewModel.isLoading.value = false

        setToolbartitle("")


        viewModel.getListData()

        attachObserver()

        binding.swiperefresh.setOnRefreshListener {
            viewModel.getListData()
            binding.swiperefresh.isRefreshing = false
        }
    }

    private fun attachObserver() {
        viewModel.apiResponse.observe(this@DashboardActivity, Observer {
            it.let {
                if (it is DataModel) {
                    setToolbartitle(it.title)
                    val lstDataRows: MutableList<DataModel.Row> = ArrayList()
                    for (item in it.rows){
                        if (item.title !=null){
                            lstDataRows.add(item)
                        }
                    }
                    binding.rvItems.adapter = DashboardItemAdapter(this@DashboardActivity, lstDataRows)
                }
            }
        })

        viewModel.apiError.observe(this@DashboardActivity, Observer {
            it.let {
                if (it is String){
                    Toast.makeText(this@DashboardActivity, it, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun setToolbartitle(_title:String){
        supportActionBar.apply {
            title = _title
        }
    }
}
