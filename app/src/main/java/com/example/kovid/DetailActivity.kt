package com.example.kovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var datas : FacnameList
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        val recieve = intent.getStringExtra("data")
//        text1.text = recieve

        datas = intent.getSerializableExtra("data") as FacnameList

        text1.text = datas.name
        text2.text = datas.num
        text3.text = datas.add
    }
}