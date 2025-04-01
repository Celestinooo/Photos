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

    private var mockCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.photosSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val photo = photoList[p2]
                    retrieveImages(photo)
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


    private fun retrieveImages(photo: Photo) {
        //testMocked()
        loadImageFromUrl(photo.url, amb.imgUrl)
        loadImageFromUrl(photo.thumbnailUrl, amb.imgThumb)
    }

    private fun loadImageFromUrl(url: String, imageView: ImageView) {
        ImageRequest(url, { response ->
            imageView.setImageBitmap(response)
        }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.ARGB_8888, {
            Toast.makeText(this, getString(R.string.request_problem), Toast.LENGTH_SHORT).show()
        }).also {
            JSONPlaceholderAPI.getInstance(this).addToRequestQueue(it)
        }
    }

    /**
     * URLs de imagem da API utilizada geram erro no request, portanto essa função foi usada para testar requests e troca das imagens.
     */
    private fun testMocked() {
        val mockUrl : String
        val mockThumb : String
        if(mockCounter == 0) {
            mockUrl = "https://fastly.picsum.photos/id/880/200/200.jpg?hmac=g5VV-eqqKk9TdTvkzKu6PzjRtzrqVhrj6v7H9ZT7PDo"
            mockThumb = "https://fastly.picsum.photos/id/1082/200/300.jpg?hmac=AaFCHuEst4e0Oy553UCibOtysEKByBAl3XsTR8n4e1c"
            mockCounter++
        } else {
            mockUrl = "https://fastly.picsum.photos/id/107/200/300.jpg?hmac=vq69VuAP_HhH4bpPD0bOs_QN_b-223QZ6RKKdu-Vv_I"
            mockThumb = "https://fastly.picsum.photos/id/693/200/200.jpg?hmac=7KcC6ytdAPoUzLmXyr1r5hDXHyYQL-W1P40rRURkouE"
            mockCounter--
        }
        loadImageFromUrl(mockUrl, amb.imgUrl)
        loadImageFromUrl(mockThumb, amb.imgThumb)
    }
}