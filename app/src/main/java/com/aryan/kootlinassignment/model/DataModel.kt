package com.aryan.kootlinassignment.model

import com.google.gson.annotations.SerializedName

class DataModel {
    @SerializedName("title")
    val title: String = ""

    @SerializedName("rows")
    val rows: MutableList<Row> = ArrayList()

    class Row {
        @SerializedName("title")
        val title: String = ""

        @SerializedName("description")
        val description: String = ""

        @SerializedName("imageHref")
        val imageHref: Any? = null
    }
}