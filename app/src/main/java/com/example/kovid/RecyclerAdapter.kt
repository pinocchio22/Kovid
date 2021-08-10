package com.example.kovid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kovid.databinding.ListItemBinding
import java.lang.reflect.Member

/**
 * @author CHOI
 * @email vviian.2@gmail.com
 * @created 2021-08-10
 * @desc
 */
class RecyclerAdapter(private var items: ArrayList<ArrayList<String>>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    var listData = mutableListOf<Member>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        Log.d("tag1" , "onCreateViewHolder")
        return MyViewHolder(binding)
    }

    class MyViewHolder(val binding: ListItemBinding ): RecyclerView.ViewHolder(binding.root) {

        fun bind(listener : View.OnClickListener, item : KovidList) {
            binding.name.text = item.facilityName
            binding.name.setOnClickListener(listener)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener { // TODO: 2021-08-10
        }
        holder.apply {
//            bind(listener)
//            itemView.tag = item
        }

    }
}

