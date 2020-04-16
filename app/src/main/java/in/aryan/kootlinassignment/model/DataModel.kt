package `in`.aryan.kootlinassignment.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




open class DataModel {
    @SerializedName("title")
    public val title: String = ""

    @SerializedName("rows")
    public val rows: MutableList<Row> = ArrayList()

    inner class Row {
        @SerializedName("title")
        public val title: String = ""

        @SerializedName("description")
        public val description: String = ""

        @SerializedName("imageHref")
        public val imageHref: Any? = null
    }
}