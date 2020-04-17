package com.aryan.kootlinassignment.repository

import com.aryan.kootlinassignment.model.DataModel
import io.reactivex.Observable
import retrofit2.http.*




interface APIService
{

    /**

     * @Base APIService interface :  This interface contain the all the mehtods
        of apis (Communicate to  servers with prdefined parameters ).
     **/

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getListDate(): Observable<DataModel>

}