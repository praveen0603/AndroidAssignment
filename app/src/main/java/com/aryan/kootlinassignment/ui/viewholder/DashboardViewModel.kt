package com.aryan.kootlinassignment.ui.viewholder

import com.aryan.kootlinassignment.model.DataModel
import com.aryan.kootlinassignment.repository.ApiUtilis
import com.aryan.kootlinassignment.repository.ErrorModel
import com.aryan.kootlinassignment.repository.NetworkManager
import com.aryan.kootlinassignment.repository.ServiceListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aryan.kootlinassignment.data.CanadaDatabase
import com.aryan.kootlinassignment.data.RowItem

class DashboardViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<String>()
    var apiResponse = MutableLiveData<DataModel>()
    var dataFromDB = MutableLiveData<MutableList<RowItem>>()
    var loadDataToDB = MutableLiveData<Boolean>()

    fun getDataFromDB(canadaDatabase: CanadaDatabase) {
        dataFromDB.value = canadaDatabase.getDataItems().value
    }

    fun loadDataToDB(canadaDatabase: CanadaDatabase, dataModel: DataModel){
        canadaDatabase.deleteAllData()
        var updateToDb = false
        var notUpdateToDb = true

        dataModel.apply {
            canadaDatabase.addTitle(this.title)

            loop@for (item in this.rows){
                if (item.title != null) {
                    if (canadaDatabase.addRow(item) > -1) {
                        updateToDb = true
                    } else {
                        notUpdateToDb = false
                        break@loop
                    }
                }
            }

            loadDataToDB.value = updateToDb && notUpdateToDb
        }

    }

    fun getListData() {
        if (isLoading.value == false) {
            isLoading.value = true
            val manager = NetworkManager()
            manager.createApiRequest(ApiUtilis.getAPIService().getListDate(), object :
                ServiceListener<DataModel> {
                override fun getServerResponse(response: DataModel, requestcode: Int) {
                    apiResponse.value = response
                    apiError.value = response.title
                    isLoading.value = false
                }

                override fun getError(error: ErrorModel, requestcode: Int) {
                    apiError.value = error.error_message
                    isLoading.value = false
                }
            })

        }
    }
}