package br.edu.ifsp.scl.sdm.dummyproducts.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import br.edu.ifsp.scl.sdm.dummyproducts.R
import br.edu.ifsp.scl.sdm.dummyproducts.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.dummyproducts.ui.adapter.PhotoAdapter
import br.edu.ifsp.scl.sdm.dummyproducts.ui.model.JSONPlaceholderAPI
import br.edu.ifsp.scl.sdm.dummyproducts.ui.model.Photo
import com.android.volley.toolbox.ImageRequest

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val photoList : MutableList<Photo> = mutableListOf()

    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter(this, photoList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.photosSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val photo = photoList[p2]
                    retrieveUrl(photo)
                    retrieveThumbnail(photo)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }

        setSupportActionBar(amb.mainTb.apply {
            title = getString(R.string.app_name)
        })

        retrievePhotos()
    }

    private fun retrievePhotos() =
        JSONPlaceholderAPI.PhotoListRequest({ photoList ->
            photoAdapter.addAll(photoList.toMutableList())
        },{
            Toast.makeText(this, getString(R.string.request_problem),Toast.LENGTH_SHORT).show()
        }).also {
            JSONPlaceholderAPI.getInstance(this).addToRequestQueue(it)
        }


    private fun retrieveUrl(photo: Photo) {
        ImageRequest(photo.url, { response ->
            amb.imgUrl.setImageBitmap(response)
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, {
            Toast.makeText(this, getString(R.string.request_problem),Toast.LENGTH_SHORT).show()
        }).also {
            JSONPlaceholderAPI.getInstance(this).addToRequestQueue(it)
        }
    }

    private fun retrieveThumbnail(photo: Photo) {

    }
}