package br.edu.ifsp.scl.sdm.dummyproducts.ui.model

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class JSONPlaceholderAPI(context: Context) {

    companion object {
        const val PHOTOS_ENDPOINT = "https://jsonplaceholder.typicode.com/photos"

        @Volatile
        private var INSTANCE : JSONPlaceholderAPI? = null

        fun getInstance(context: Context)= INSTANCE  ?: synchronized(this) {
            INSTANCE  ?: JSONPlaceholderAPI(context).also {
                INSTANCE = it
            }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        requestQueue.add(request)
    }
}