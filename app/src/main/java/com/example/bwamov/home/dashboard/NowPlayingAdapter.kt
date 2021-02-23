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
import com.example.bwamov.R
import com.example.bwamov.model.Film
import kotlinx.android.synthetic.main.row_item_now_playing.view.*

//jangankan buat layout untuk handle adapter ini

class NowPlayingAdapter(private var data : List<Film>,
                        private val listener:(Film) -> Unit)
    : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    lateinit var contextAdapter : Context

//    bagian inti
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.row_item_now_playing, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter)
    }

    override fun getItemCount(): Int = data.size


//    karena viewholder belom ada buatlah classnya
//    layout
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
//        inisiasi viewnya
        private val tvTitle : TextView = view.findViewById(R.id.tv_kursi)
        private val tvGenre : TextView = view.findViewById(R.id.tv_genre)
        private val tvRate : TextView = view.findViewById(R.id.tv_rate)

//        imageview
        private val tvImage : ImageView = view.findViewById(R.id.iv_poster_image)

        fun bindItem(data:Film, listener: (Film) -> Unit, context: Context){
//            set data
            tvTitle.setText(data.judul)
            tvGenre.setText(data.genre)
            tvRate.setText(data.rating)

            Glide.with(context)
                    .load(data.poster)
                    .into(tvImage)

            itemView.setOnClickListener {
//                skalian ambil data
                listener(data)
            }
        }
    }


}
