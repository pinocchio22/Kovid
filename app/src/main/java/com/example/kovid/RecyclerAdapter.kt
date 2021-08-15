package com.example.kovid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kovid.databinding.ListItemBinding
import org.w3c.dom.Text
import java.lang.reflect.Member

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-08-10
 * @desc
 */
//class RecyclerAdapter(private var items: ArrayList<String>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {
//
//    var listData = mutableListOf<Member>()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
////        Log.d("tag1" , "onCreateViewHolder")
//        return MyViewHolder(binding)
//    }
//
//    class MyViewHolder(val binding: ListItemBinding ): RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(listener : View.OnClickListener, item : KovidList) {
//            binding.name.text = item.facilityName
//            binding.name.setOnClickListener(listener)
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return items.size
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item = items[position]
//        val listener = View.OnClickListener { // TODO: 2021-08-10
//        }
//        holder.apply {
////            bind(listener)
////            itemView.tag = item
//        }
//
//    }
//}
//
class RecyclerAdapter( private val items: ArrayList<String>) :
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    var datas = mutableListOf<FacnameList>()


    interface OnItemClickListener{
        fun onItemClick(v:View, data: FacnameList, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    //RecyclerView 초기화때 호출된다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)	//아이템 뷰 xml설정
        return ViewHolder(inflatedView)
    }

    //생성된 View에 보여줄 데이터를 설정
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val listener = View.OnClickListener {it -> }
        holder.bind(datas[position])
        holder.name.text = items[position]

    }

    //보여줄 아이템 개수가 몇개인지 알려줍니다
    override fun getItemCount(): Int = items.size


    //ViewHolder 단위 객체로 View의 데이터를 설정합니다
    inner class ViewHolder(private var v: View) : RecyclerView.ViewHolder(v){

        val name : TextView = itemView.findViewById(R.id.name)

        fun bind( item: FacnameList){
            name.text = item.name

            val pos = adapterPosition
            if(pos!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,pos)
                }
            }
//            itemView.setOnClickListener{
//                Intent(this, DetailActivity::class.java).apply {
//                    putExtra("data", item)
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }.run {context.startActivity(this)}
//            }
        }
    }
}