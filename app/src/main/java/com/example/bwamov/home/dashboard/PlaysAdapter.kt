package com.example.bwamov.home.dashboard

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bwamov.R
import com.example.bwamov.model.Plays

//jangankan buat layout untuk handle adapter ini

class PlaysAdapter(private var data : List<Plays>,
                   private val listener:(Plays) -> Unit)
    : RecyclerView.Adapter<PlaysAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

//    bagian inti
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaysAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_played, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: PlaysAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size


//    karena viewholder belom ada buatlah classnya
//    layout
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
//        inisiasi viewnya
        private val tvNama : TextView = view.findViewById(R.id.tv_nama)
//        imageview
        private val tvImage : ImageView = view.findViewById(R.id.iv_played_image)

        fun bindItem(data:Plays, listener: (Plays) -> Unit, context: Context){
//            set data
            tvNama.setText(data.nama)

            Glide.with(context)
                    .load(data.url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(tvImage)

            itemView.setOnClickListener {
//                skalian ambil data
                listener(data)
            }
        }
    }


}
