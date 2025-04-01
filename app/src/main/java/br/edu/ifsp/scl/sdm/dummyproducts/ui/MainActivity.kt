package br.edu.ifsp.scl.sdm.dummyproducts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import br.edu.ifsp.scl.sdm.dummyproducts.R
import br.edu.ifsp.scl.sdm.dummyproducts.databinding.ActivityMainBinding
import br.edu.ifsp.scl.sdm.dummyproducts.ui.adapter.PhotoAdapter
import br.edu.ifsp.scl.sdm.dummyproducts.ui.model.Photo

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

        amb.productsSp.apply {
            adapter = photoAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    retrieveUrlAndThumbnail()
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

    private fun retrievePhotos() {

    }

    private fun retrieveUrlAndThumbnail() {

    }
}