package com.example.kovid

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kovid.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    
    var datas = mutableListOf<FacnameList>()
    var sido_list = arrayListOf<String>()
    var sigungu_list = arrayListOf<String>()
    var total = 0
    var sido_item = arrayListOf<String>()
    var sigungu_item = arrayListOf<String>()
    val LayoutManager  = LinearLayoutManager(this)
    var obj = JSONObject()

    lateinit var latitude : String
    lateinit var longitute : String
    lateinit var address : String
    lateinit var facilityName: String
    lateinit var phoneNumber: String
    lateinit var sido : String
    lateinit var sigungu : String
    lateinit var data : JSONArray

    lateinit var a : String

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val thread1 = NetworkThread1()
        val thread2 = NetworkThread2()
        thread1.start()
        thread2.start()
        setSpinner1()

    }

    override fun onResume() {
        super.onResume()

        val thread = NetworkThread2()
        thread.start()
        setSpinner1()


        binding.button2.setOnClickListener{
            val thread2 = NetworkThread2()
            thread2.start()
            setSpinner1()
        }
        // ????????? ????????? ????????? ??????
        binding.button.setOnClickListener {

            binding.recyclerView.layoutManager = LayoutManager
            var recyclerAdapter = RecyclerAdapter(this)
            binding.recyclerView.adapter = recyclerAdapter
            recyclerAdapter.datas = datas
            recyclerAdapter.notifyDataSetChanged()

            recyclerAdapter.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener{
                override fun onItemClick(v: View, data: FacnameList, pos: Int) {
                    Intent(this@MainActivity,DetailActivity::class.java).apply {
                        putExtra("data", data)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { startActivity(this) }
                }
            })
        }
    }

    inner class NetworkThread1: Thread(){
        override fun run() {
            var url = URL("https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=10&serviceKey=9PAc6aMn2DC3xdA7rYZn71Hxr3mT9V5E4qnnakQkwj44zVNrPfV%2FVLVnDsnf30wrZZ%2BD%2FS%2BWRTNinP7J8lMjeQ%3D%3D")
            var conn = url.openConnection()
            var input = conn.getInputStream()
            var isr = InputStreamReader(input)
            // br: ?????? ????????? ???????????? ???????????? ????????? ??????
            var br = BufferedReader(isr)

            // Json ????????? ?????? ???????????? ???????????? ?????? ????????? ???, Json??? ????????? ????????? ???????????? ???????????? ?????????
            var str: String?
            var buf = StringBuffer()

            do{
                str = br.readLine()

                if(str!=null){
                    buf.append(str)
                }
            }while (str!=null)

            // ????????? ????????? ???????????? ????????? ??????????????? ?????????
            var root = JSONObject(buf.toString())
            var totalCount : Int = root.getInt("totalCount")
            total = totalCount
        }
    }


    // ??????????????? ????????? ?????? ???????????? ???????????? ???????????? ???
    inner class NetworkThread2: Thread(){
        override fun run() {
            val Key = "9PAc6aMn2DC3xdA7rYZn71Hxr3mT9V5E4qnnakQkwj44zVNrPfV%2FVLVnDsnf30wrZZ%2BD%2FS%2BWRTNinP7J8lMjeQ%3D%3D"
            var Page = 1
            var PerPage = total
            // ????????? ????????? ??????: Site
            val site = "https://api.odcloud.kr/api/15077586/v1/centers"

            var url = URL(site + "?page=" + Page + "&perPage=" + PerPage + "&serviceKey=" + Key) // + "?" + encodeString())
            var conn = url.openConnection()
            var input = conn.getInputStream()
            var isr = InputStreamReader(input)
            // br: ?????? ????????? ???????????? ???????????? ????????? ??????
            var br = BufferedReader(isr)

            // Json ????????? ?????? ???????????? ???????????? ?????? ????????? ???, Json??? ????????? ????????? ???????????? ???????????? ?????????
            var str: String?
            var buf = StringBuffer()

            do{
                str = br.readLine()

                if(str!=null){
                    buf.append(str)
                }
            }while (str!=null)

            // ????????? ????????? ???????????? ????????? ??????????????? ?????????
            var root = JSONObject(buf.toString())
            // ????????? ??????
            runOnUiThread {
                // ?????? ?????? ?????? data ????????? ???????????? ?????????
                data = root.getJSONArray("data")
                var obj: JSONObject
                // ???????????? ?????? ???????????? data.length ?????? ?????????
                for(i in 0..data.length()-1){
                    obj = data.getJSONObject(i)

                    sido = obj.getString("sido")
                    sigungu = obj.getString("sigungu")
                    sido_list.add(sido)
                    sigungu_list.add(sigungu)

                }

                for (i in 0..data.length()-1) {
                    if (!sido_item.contains(sido_list[i])){
                        sido_item.add(sido_list[i])
                    }
                }
            }
        }
    }

    fun sido_sigungu(sido_ : String) {
        sigungu_item.clear()
        for (i in 0..data.length()-1) {
            obj = data.getJSONObject(i)
            sido = obj.getString("sido")
            sigungu = obj.getString("sigungu")
            sido_list.add(sido)

            if (sido == sido_){
                sigungu_item.add(sigungu)
                a = sido_
            }
        }
    }

    fun sigungu_rec(a : String , sigungu_ : String) {

        datas.clear()
        for (i in 0..data.length()-1) {
            obj = data.getJSONObject(i)
            sigungu = obj.getString("sigungu")
            sido = obj.getString("sido")
            address = obj.getString("address")
            facilityName = obj.getString("facilityName")
            phoneNumber = obj.getString("phoneNumber")
            latitude = obj.getString("lat")
            longitute = obj.getString("lng")

            if (sido == a && sigungu == sigungu_){
                datas.apply {
                    add(FacnameList(facilityName, phoneNumber, address,latitude,longitute))
                }
            }
        }
    }

    // Spinner
    //????????? ??????1
    fun setSpinner1() {

        var arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, sido_item)
        spinner1.adapter = arrayAdapter
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {//???????????? ?????? ????????????
                sido_sigungu(sido_item[position])
                setSpinner2()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }
    //????????? ??????2
    fun setSpinner2() {

        var arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, sigungu_item)
        spinner2.adapter = arrayAdapter
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {//???????????? ?????? ????????????
                sigungu_rec(a, sigungu_item[position])
            }
            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }
}
