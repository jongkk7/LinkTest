package com.test.linktest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.test.linktest.util.repository.YDataStoreRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ActivityTest : AppCompatActivity() {
    val TAG = "###" + javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        test()

    }

    fun test(){

        val key = "intValue"
        val repository : YDataStoreRepository = YDataStoreRepository(this)

        CoroutineScope(Dispatchers.Main).launch {
            val flow = repository.getInt(key)

        }



        CoroutineScope(Dispatchers.Main).launch {
            Log.d(TAG, "put 10")
            repository.setInt(key, 10)
            Log.d(TAG, "put 20")
            repository.setInt(key, 20)
            Log.d(TAG, "put 30")
            repository.setInt(key, 30)
        }

//        val value01 = repository.getInt(key)
//        Log.d(TAG, "value01 = $value01")
//        CoroutineScope(Dispatchers.Main).launch {
//            val value02 = repository.setInt(key, 10)
//            Log.d(TAG, "value02 = $value02")
//        }
//        val value03 = repository.getInt(key)
//        Log.d(TAG, "value03 = $value03")

    }

}
