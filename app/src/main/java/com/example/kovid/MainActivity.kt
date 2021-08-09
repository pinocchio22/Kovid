package com.example.kovid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kovid.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class MainActivity : AppCompatActivity() {


    var facList = arrayListOf<List_item>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val facAdapter = ListViewAdapter(this, facList)
        binding.listView.adapter = facAdapter

        // 버튼을 누르면 쓰레드 동작
        binding.button.setOnClickListener {
            // 쓰레드 생성
            val thread = NetworkThread()
            thread.start()
        }

        binding.textView.setOnClickListener {
            val Intent = Intent(this, DetailActivity::class.java)
            startActivity(Intent)
        }
    }

    // 네트워크를 이용할 때는 쓰레드를 사용해서 접근해야 함
    inner class NetworkThread: Thread(){
        override fun run() {
            // 접속할 페이지 주소: Site
            val site = "https://api.odcloud.kr/api/15077586/v1/centers?page=1&perPage=10&serviceKey=9PAc6aMn2DC3xdA7rYZn71Hxr3mT9V5E4qnnakQkwj44zVNrPfV%2FVLVnDsnf30wrZZ%2BD%2FS%2BWRTNinP7J8lMjeQ%3D%3D"
            var url = URL(site)
            var conn = url.openConnection()
            var input = conn.getInputStream()
            var isr = InputStreamReader(input)
            // br: 라인 단위로 데이터를 읽어오기 위해서 만듦
            var br = BufferedReader(isr)

            // Json 문서는 일단 문자열로 데이터를 모두 읽어온 후, Json에 관련된 객체를 만들어서 데이터를 가져옴
            var str: String?
            var buf = StringBuffer()

            do{
                str = br.readLine()

                if(str!=null){
                    buf.append(str)
                }
            }while (str!=null)

//            Log.d("why", buf.toString())
            // 전체가 객체로 묶여있기 때문에 객체형태로 가져옴
            var root = JSONObject(buf.toString())
            var totalCount : Int = root.getInt("totalCount")
            // totalCount 받아옴
            // 화면에 출력
            runOnUiThread {
//                binding.textView.append("totalCount: ${totalCount}\n\n\n")


                // 객체 안에 있는 data 이름의 리스트를 가져옴
                var data = root.getJSONArray("data")
//                Log.d("data?", data.toString())
                // 리스트에 있는 데이터를 totalCount 만큼 가져옴
                for(i in 0 until 10){
                    var obj = data.getJSONObject(i)

//                    var address: String = obj.getString("address")
//                    var zipCode: String = obj2.getString("zipCode")
                    var facilityName: String = obj.getString("facilityName")
//                    var phoneNumber: String = obj.getString("phoneNumber")

                    // 화면에 출력
                    runOnUiThread {
                        binding.textView.append("이름 : ${facilityName}\n")
//                        binding.textView.append("주소 : ${address}\n")
//                        binding.textView.append("우편번호 : ${zipCode}\n")
//                        binding.textView.append("전화번호 : ${phoneNumber}\n")
                    }
                }
            }
        }
    }
}