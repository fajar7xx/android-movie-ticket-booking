package com.example.bwamov.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.bwamov.R
import com.example.bwamov.model.Checkout
import com.example.bwamov.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {

    var statusA3 : Boolean = false
    var statusA4 : Boolean = false
    var total : Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

//        ambil data dari intent
        val data = intent.getParcelableExtra<Film>("data")
        tv_judul.text = data?.judul

        a3.setOnClickListener {
            if(statusA3){
                a3.setImageResource(R.drawable.ic_rectangle_empty)
                statusA3 = false
                total -= total

//                fungsi
                beliTiket(total)
            }else{
                a3.setImageResource(R.drawable.ic_rectangle_red)
                statusA3 = true
                total += total

//                fungsi
                beliTiket(total)

                val data = Checkout("A3", "25000")
                dataList.add(data)
            }
        }

        a4.setOnClickListener {
            if(statusA4){
                a4.setImageResource(R.drawable.ic_rectangle_empty)
                statusA4 = false
                total -= total

//                fungsi
                beliTiket(total)
            }else{
                a4.setImageResource(R.drawable.ic_rectangle_red)
                statusA4 = true
                total += total

//                fungsi
                beliTiket(total)

                val data = Checkout("A4", "25000")
                dataList.add(data)

                //TODO baru sampai 34:16 https://youtu.be/nyhTmFwd4SM?t=2056
            }
        }
    }

    private fun beliTiket(total: Int) {
//        untuk pengecekan apakah button disable atau tidak
        if(total === 0){
            btn_beli_tiket.setText("Beli Tiket")

//            set visibility nya tidak kelihatan
            btn_beli_tiket.visibility = View.INVISIBLE
        }else{
            btn_beli_tiket.setText("Beli Tiket (" + total + ")")

//            set visibility nya tidak kelihatan
            btn_beli_tiket.visibility = View.VISIBLE
        }
    }
}