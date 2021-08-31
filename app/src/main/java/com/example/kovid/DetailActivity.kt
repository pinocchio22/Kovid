package com.example.kovid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*


@Suppress("CAST_NEVER_SUCCEEDS")
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
//        lateinit var datas : FacnameList
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var intent: Intent = getIntent()
        val datas: FacnameList? = intent.getParcelableExtra("data") as FacnameList?

//        val recieve = intent.getStringExtra("data")
//        text1.text = recieve
//        datas = FacnameList("", "", "")
//        datas = intent.getSerializableExtra("data") as FacnameList
//        datas = (intent.getSerializableExtra("data") as? FacnameList)!!
//        (intent.getSerializableExtra("data") as? FacnameList)
        Log.d("데이터2", datas.toString())

        if (datas != null) {
            text1.text = datas.name
        }
        if (datas != null) {
            text2.text = datas.num
        }
        if (datas != null) {
            text3.text = datas.add
        }

        text2.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + datas?.num)
            startActivity(intent)
        }

            back.setOnClickListener {
                finish()
            }
    }
}

