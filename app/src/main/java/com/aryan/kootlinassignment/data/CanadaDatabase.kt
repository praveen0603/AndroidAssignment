package com.aryan.kootlinassignment.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aryan.kootlinassignment.model.DataModel

class CanadaDatabase(
    context: Context
) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "CanadaDatabase"
        private const val TABLE_CONTENT = "AboutCanadaTable"
        private const val KEY_CONTENT_TITLE = "content_title"
        private const val KEY_TITLE = "title"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_IMAGE_URL = "image_url"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ABOUT_CANADA_TABLE = ("CREATE TABLE " + TABLE_CONTENT + "("
                + KEY_CONTENT_TITLE + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_IMAGE_URL + " TEXT"
                + ")")
        db?.execSQL(CREATE_ABOUT_CANADA_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTENT")
        onCreate(db)
    }

    //method to insert row
    fun addRow(item: DataModel.Row):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        val desc = if (item.description.isNullOrEmpty()){
            ""
        } else {
            item.description
        }

        val image = if (item.imageHref == null){
            ""
        } else {
            item.imageHref.toString()
        }
        contentValues.put(KEY_CONTENT_TITLE, "")
        contentValues.put(KEY_TITLE, item.title)
        contentValues.put(KEY_DESCRIPTION, desc)
        contentValues.put(KEY_IMAGE_URL, image)
        // Inserting Row
        val success = db.insert(TABLE_CONTENT, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to insert title
    fun addTitle(title: String):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_CONTENT_TITLE, title)
        contentValues.put(KEY_TITLE, "")
        contentValues.put(KEY_DESCRIPTION, "" )
        contentValues.put(KEY_IMAGE_URL, "" )
        // Inserting Row
        val success = db.insert(TABLE_CONTENT, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    //method to get data
    fun getDataItems():MutableLiveData<MutableList<RowItem>>{
        val resultList = MutableLiveData<MutableList<RowItem>>();
        val dataList: MutableList<RowItem> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_CONTENT"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return resultList
        }
        var contentTitle: String
        var title: String
        var description: String
        var imageUrl: String
        if (cursor.moveToFirst()) {
            do {
                contentTitle = cursor.getString(cursor.getColumnIndex("content_title"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                description = cursor.getString(cursor.getColumnIndex("description"))
                imageUrl = cursor.getString(cursor.getColumnIndex("image_url"))
                val emp= RowItem(contentTitle, title, description, imageUrl)
                dataList.add(emp)
            } while (cursor.moveToNext())
        }
        resultList.value = dataList
        return resultList
    }

    //method to delete data
    fun deleteAllData():Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_CONTENT, null, null)
        db.close()
        return success
    }


}