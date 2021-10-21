package com.test.linktest.util.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.test.linktest.BuildConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

/**
 * DataStore
 */
class YDataStoreRepository(private val context: Context) {
    private val TAG = "###" + javaClass.simpleName
    val DB_NAME = "TEST" + "_DB"

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    suspend fun getInt(key: String): Int {
        var result : Int = 0
        val key = intPreferencesKey(key)
        val flow: Flow<Int> = context.dataStore.data
                .map { pref ->
                    val value: Int = pref[key] ?: 0
//                    Log.d(TAG, "value: $value")
                    value
                }

        flow.collect {
            result = it
        }

        return result
    }

    suspend fun setInt(key: String, value: Int) {
        val key = intPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[key] = value
        }
    }

}