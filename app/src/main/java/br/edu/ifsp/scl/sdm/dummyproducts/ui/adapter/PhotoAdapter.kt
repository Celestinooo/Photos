package br.edu.ifsp.scl.sdm.dummyproducts.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.sdm.dummyproducts.ui.model.Photo

class PhotoAdapter(private val activityContext: Context, private val photoList: MutableList<Photo>) : ArrayAdapter<Photo>(activityContext,android.R.layout.simple_list_item_1, photoList){

    private data class PhotoHolder(val photoTileTv: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val photoView = convertView ?: LayoutInflater.from(activityContext).inflate(android.R.layout.simple_list_item_1, parent, false).apply {
            tag = PhotoHolder(findViewById(android.R.id.text1))
        }
        (photoView.tag as PhotoHolder).photoTileTv.text = photoList[position].title
        return photoView
    }
}