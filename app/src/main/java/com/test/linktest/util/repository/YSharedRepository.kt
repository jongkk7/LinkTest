package com.test.linktest.util.repository

import android.content.Context
import android.content.SharedPreferences
import com.test.linktest.BuildConfig

/**
 * SharedPreferences
 */
class YSharedRepository{

    companion object {
        val DB_NAME = BuildConfig.APPLICATION_ID + "_DB"

        fun getPreferences(context : Context) : SharedPreferences{
            return  context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE)
        }

        fun setString(context: Context, key : String, value : String) {
            val sharedPreferences = getPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putString(key, value)
            editor.apply()
        }

        fun setBoolean(context: Context, key : String, value : Boolean) {
            val sharedPreferences = getPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun setFloat(context: Context, key : String, value : Float) {
            val sharedPreferences = getPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putFloat(key, value)
            editor.apply()
        }

        fun setInt(context: Context, key : String, value : Int) {
            val sharedPreferences = getPreferences(context)
            val editor = sharedPreferences.edit()
            editor.putInt(key, value)
            editor.apply()
        }

        fun getString(context: Context, key : String) : String{
            val sharedPreferences = getPreferences(context)
            val value = sharedPreferences.getString(key, "")
            return value!!
        }

        fun getBoolean(context: Context, key : String) : Boolean{
            val sharedPreferences = getPreferences(context)
            val value = sharedPreferences.getBoolean(key, false)
            return value
        }

        fun getInt(context: Context, key : String) : Int{
            val sharedPreferences = getPreferences(context)
            val value = sharedPreferences.getInt(key, 0)
            return value
        }

        fun getFloat(context: Context, key : String) : Float{
            val sharedPreferences = getPreferences(context)
            val value = sharedPreferences.getFloat(key, 0f)
            return value
        }

    }




}