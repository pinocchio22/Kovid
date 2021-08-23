package com.example.kovid

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
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


class MainActivity : AppCompatActivity() {

    private lateinit var recyclerAdapter : RecyclerAdapter
    var datas = mutableListOf<FacnameList>()
    var adress_list = arrayListOf<String>()
    var facname_list = arrayListOf<String>()
    var number_list = arrayListOf<String>()
    var sido_list = arrayListOf<String>()
    var sigungu_list = arrayListOf<String>()
    var total = 0
    var sido_item = arrayListOf<String>()
    var sigungu_item = arrayListOf<String>()
    val LayoutManager  = LinearLayoutManager(this)
    var obj = JSONObject()
    lateinit var sido : String
    lateinit var data : JSONArray
//    lateinit var fac1 : FacnameList

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
//        recyclerAdapter = RecyclerAdapter(this)
//        binding.recyclerView.adapter = recyclerAdapter

        init()
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
    }

    override fun onResume() {
        super.onResume()
        // 버튼을 누르면 쓰레드 동작
        binding.button.setOnClickListener {
//            Log.d("TQ", facname_list.toString())
//            Log.d("후", list.size.toString())
            binding.recyclerView.layoutManager = LayoutManager
//            val adapter = RecyclerAdapter(this)
//            binding.recyclerView.adapter = adapter
            recyclerAdapter = RecyclerAdapter(this)
            binding.recyclerView.adapter = recyclerAdapter

            recyclerAdapter.setOnItemClickListener(object : RecyclerAdapter.OnItemClickListener{
                override fun onItemClick(v: View, data: FacnameList, pos: Int) {
                    Intent(this@MainActivity,DetailActivity::class.java).apply {
                        putExtra("data", data)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { startActivity(this) }
                }
            })
            // 아이템 클릭
//            adapter.setOnItemClickListener(object  : RecyclerAdapter.ItemClickListener{
//                override fun onClick(view : View, position : Int) {
//                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
//                    intent.putExtra("data", fac1.name[position])
//                    startActivity(intent)
//                }
//            })

            sido_sigungu("서울특별시")
//            Log.d("시도시군구", sido_sigungu("서울특별시").toString())
            Log.d("스피너1", sido_item.toString())
            Log.d("스피너2", sigungu_item.toString())
            Log.d("팩네임", datas.toString())
        }
    }

    private fun init() {
        // 쓰레드 생성
        val thread = NetworkThread()
        thread.start()

        // 스피너 실행
        setSpinner1()
        setSpinner2()
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
                data = root.getJSONArray("data")
                var obj: JSONObject
                var address : String
                var facilityName: String
                var phoneNumber: String
                var sigungu: String
//                Log.d("data?", data.toString())
                // 리스트에 있는 데이터를 totalCount 만큼 가져옴
                for(i in 0..data.length()-1){
                    obj = data.getJSONObject(i)

                    address = obj.getString("address")
                    facilityName = obj.getString("facilityName")
                    phoneNumber = obj.getString("phoneNumber")
                    sido = obj.getString("sido")
                    sigungu = obj.getString("sigungu")

                    adress_list.add(address)
                    facname_list.add(facilityName)
                    number_list.add(phoneNumber)
                    sido_list.add(sido)
                    sigungu_list.add(sigungu)
                    datas.apply {
                        add(FacnameList(facilityName, phoneNumber, address))
                    }

                    recyclerAdapter.datas = datas
                    recyclerAdapter.notifyDataSetChanged()

//                    for (i in 0..obj.length()-1) {
//                        if (sido_list.contains(obj.get("sido"))){
//                            sigungu_item.add(sido_list[i])
//                            Log.d("시군구",sigungu_item.toString())
//                        }
//                    }
//                    // 화면에 출력
//                    runOnUiThread {
//                        binding.textView.append("이름 : ${facilityName}\n")
//                        binding.textView.append("주소 : ${address}\n")
//                        binding.textView.append("우편번호 : ${zipCode}\n")
//                        binding.textView.append("전화번호 : ${phoneNumber}\n")
//                    }
//                    Log.d("LogObj", adress_list.toString() + zipcode_list)
                }
//                Log.d("오브젝트", sido)
//                Log.d("시도", sido_list.toString())

                for (i in 0..data.length()-1) {
                    if (!sido_item.contains(sido_list[i])){
                        sido_item.add(sido_list[i])
                    }
                }

//                for (i in 0..data.length()-1) {
//                    if (!sigungu_item.contains(sigungu_list[i])){
//                        sigungu_item.add(sigungu_list[i])
//                    }
//                }
//                Log.d("시군구",sigungu_item.toString())

            }


//            fun <String> convert(sigungu_item : MutableSet<String>) : ArrayList<String> {
//                return ArrayList(sigungu_item)
//            }
//            sigungu_spinner  = convert(sigungu_item)
//            Log.d("시군구아이템2",sigungu_item.toString())
//            Log.d("리스트",sido_list.toString())
        }
    }

//    fun sigunguAdd() {
//
//        for (i in 0..sigungu_list.size) {
//            if (sido_list.contains(obj.get("sido"))){
//                sigungu_item.add(sido_list[i])
//                Log.d("시군구",sigungu_item.toString())
//            }
//        }
//    }


    fun sido_sigungu(sido_ : String) {
        for (i in 0..data.length()-1) {
            obj = data.getJSONObject(i)
            sido = obj.getString("sido")
            if (sido.equals(sido_)){
                sigungu_item.add(sigungu_list[i])
            }
        }
        Log.d("사이즈", sido_item.size.toString())
        Log.d("시도", sido)
        Log.d("시도아이템", sido_item.toString())
        Log.d("시도리스트", sido_list.toString())
    }

    // Spinner
    //스피너 셋팅1
    fun setSpinner1() {

        var arrayAdapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_dropdown_item, sido_item)
        spinner1.setAdapter(arrayAdapter)
        spinner1.setSelection(arrayAdapter.count)
        spinner1.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {//스피너가 선택 되었을때
//                Toast.makeText(applicationContext, sido_list[i].toString() + "가 선택되었습니다.", Toast.LENGTH_SHORT).show()
                spinner1.selectedItem.toString()

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })
    }
    //스피너 셋팅2
    fun setSpinner2() {

        var arrayAdapter = ArrayAdapter(applicationContext, R.layout.simple_spinner_dropdown_item, sigungu_item)
        spinner2.setAdapter(arrayAdapter)
        spinner2.setSelection(1)
        spinner2.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {//스피너가 선택 되었을때
//                Toast.makeText(applicationContext, sigungu_spinner.toString() + "가 선택되었습니다.", Toast.LENGTH_SHORT).show()
                spinner2.selectedItem.toString()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        })
    }
}