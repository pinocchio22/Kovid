package com.example.kovid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kovid.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.net.URLEncoder
import java.util.*


class MainActivity : AppCompatActivity() {

//    var facList = arrayListOf<List_item>()
    var adress_list = arrayListOf<String>()
    var zipcode_list = arrayListOf<String>()
    var facname_list = arrayListOf<String>()
    var number_list = arrayListOf<String>()
    var sido_list = arrayListOf<String>()
    var sigungu_list = arrayListOf<String>()
    var total = 0

    val LayoutManager  = LinearLayoutManager(this)

//    fun encodeString(params: Properties): String? {
//        val sb = StringBuffer(256)
//        val names = params.propertyNames()
//        while (names.hasMoreElements()) {
//            val name = names.nextElement() as String
//            val value: String = params.getProperty(name)
//            sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value))
//            if (names.hasMoreElements()) sb.append("&")
//        }
//        return sb.toString()
//    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        val prop = Properties()
//        prop.setProperty("page", 1.toString())
//        prop.setProperty("perpage", 10.toString())
//        prop.setProperty("serviceKey","9PAc6aMn2DC3xdA7rYZn71Hxr3mT9V5E4qnnakQkwj44zVNrPfV%2FVLVnDsnf30wrZZ%2BD%2FS%2BWRTNinP7J8lMjeQ%3D%3D")
//        encodeString(prop)
//        val list = ArrayList<FacnameList>(facname_list.size)
//        for (i in 0..list.size) {
//            list.add(FacnameList("$i" + "번째"))
//        }
//        if (!list.isEmpty()){
//            list.add(facname_list[0])
//        }

        // 쓰레드 생성
        val thread = NetworkThread()
        thread.start()

        // 버튼을 누르면 쓰레드 동작
        binding.button.setOnClickListener {
            Log.d("TQ", facname_list.toString())
//            Log.d("후", list.size.toString())

            binding.recyclerView.layoutManager = LayoutManager
            val adapter = RecyclerAdapter(facname_list)
            binding.recyclerView.adapter = adapter
        }
    }

    // 네트워크를 이용할 때는 쓰레드를 사용해서 접근해야 함
    inner class NetworkThread: Thread(){
        override fun run() {
            val Key = "9PAc6aMn2DC3xdA7rYZn71Hxr3mT9V5E4qnnakQkwj44zVNrPfV%2FVLVnDsnf30wrZZ%2BD%2FS%2BWRTNinP7J8lMjeQ%3D%3D"
            var Page = 1
            var PerPage = 10
            // 접속할 페이지 주소: Site
            val site = "https://api.odcloud.kr/api/15077586/v1/centers"

            var url = URL(site + "?page=" + Page + "&perPage=" + PerPage + "&serviceKey=" + Key) // + "?" + encodeString())
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
            total = totalCount
            // totalCount 받아옴
            // 화면에 출력
            runOnUiThread {
//                binding.textView.append("totalCount: ${totalCount}\n\n\n")


                // 객체 안에 있는 data 이름의 리스트를 가져옴
                var data = root.getJSONArray("data")
//                Log.d("data?", data.toString())
                // 리스트에 있는 데이터를 totalCount 만큼 가져옴
                for(i in 0..data.length()-1){
                    var obj = data.getJSONObject(i)

                    var address: String = obj.getString("address")
                    var zipCode: String = obj.getString("zipCode")
                    var facilityName: String = obj.getString("facilityName")
                    var phoneNumber: String = obj.getString("phoneNumber")
                    var sido: String = obj.getString("sido")
                    var sigungu: String = obj.getString("sigungu")
                    adress_list.add(address)
                    zipcode_list.add(zipCode)
                    facname_list.add(facilityName)
                    number_list.add(phoneNumber)
                    sido_list.add(sido)
                    sigungu_list.add(sigungu)


//                    // 화면에 출력
//                    runOnUiThread {
//                        binding.textView.append("이름 : ${facilityName}\n")
//                        binding.textView.append("주소 : ${address}\n")
//                        binding.textView.append("우편번호 : ${zipCode}\n")
//                        binding.textView.append("전화번호 : ${phoneNumber}\n")
//                    }
//                    Log.d("LogObj", adress_list.toString() + zipcode_list)
                }
            }
        }
    }
}