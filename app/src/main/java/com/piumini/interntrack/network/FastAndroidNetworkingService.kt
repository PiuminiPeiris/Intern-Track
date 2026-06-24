package com.piumini.interntrack.network

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

object FastAndroidNetworkingService {
    fun fetchSamplePost(
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        AndroidNetworking.get("https://jsonplaceholder.typicode.com/posts/1")
            .setTag("fast_android_networking_sample")
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {
                    val title = response?.optString("title", "No title")
                        ?: "No response"

                    val message = "Fast Android Networking loaded: $title"

                    Log.d("FastAndroidNetwork", message)
                    onSuccess(message)
                }

                override fun onError(anError: ANError?) {
                    val message = "Fast Android Networking error: ${anError?.message}"

                    Log.e("FastAndroidNetwork", message)
                    onError(message)
                }
            }
            )
        }
    }