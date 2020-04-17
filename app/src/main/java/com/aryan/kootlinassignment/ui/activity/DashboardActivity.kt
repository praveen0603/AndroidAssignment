package com.aryan.kootlinassignment.ui.activity

import com.aryan.kootlinassignment.R
import com.aryan.kootlinassignment.databinding.ActivityMainBinding
import com.aryan.kootlinassignment.model.DataModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.aryan.kootlinassignment.data.CanadaDatabase
import com.aryan.kootlinassignment.data.RowItem
import com.aryan.kootlinassignment.ui.adapter.DashboardItemAdapter
import com.aryan.kootlinassignment.ui.viewholder.DashboardViewModel

class DashboardActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: DashboardViewModel
    private lateinit var canadaDatabase: CanadaDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = DashboardViewModel()
        canadaDatabase = CanadaDatabase(this@DashboardActivity)
        viewModel.isLoading.value = false

        setToolbartitle("")
        viewModel.getDataFromDB(canadaDatabase)
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

                    viewModel.loadDataToDB(canadaDatabase, it)
                
                }
            }
        })

        viewModel.loadDataToDB.observe(this@DashboardActivity, Observer {
            it.let {
                if (it is Boolean){
                    if (it == true){
                        viewModel.getDataFromDB(canadaDatabase)
                    }
                }
            }
        })

        viewModel.dataFromDB.observe(this@DashboardActivity, Observer {
            it.let {
                if (it.size> 0){
                    binding.tvNoDataAvailable.visibility = View.GONE
                    setToolbartitle(it[0].contentTitle)

                    val lstDataRows: MutableList<RowItem> = ArrayList()
                    for (i in 1 until it.size){
                        lstDataRows.add(it[i])
                    }

                    binding.rvItems.adapter =
                        DashboardItemAdapter(
                            this@DashboardActivity,
                            lstDataRows
                        )
                } else {
                    viewModel.getListData()
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

    private fun setToolbartitle(_title:String){
        supportActionBar.apply {
            title = _title
        }
    }
}
