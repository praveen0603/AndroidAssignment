package `in`.aryan.kootlinassignment.ui.dashboard

import `in`.aryan.kootlinassignment.model.DataModel
import `in`.aryan.kootlinassignment.repository.ApiUtilis
import `in`.aryan.kootlinassignment.repository.ErrorModel
import `in`.aryan.kootlinassignment.repository.NetworkManager
import `in`.aryan.kootlinassignment.repository.ServiceListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var apiError = MutableLiveData<String>()
    var apiResponse = MutableLiveData<DataModel>()

    fun getListData(){
        //if (isLoading.value == false) {
            //isLoading.value = true
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

        //}
    }
}