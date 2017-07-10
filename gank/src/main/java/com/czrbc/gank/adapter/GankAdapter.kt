package com.czrbc.gank.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.czrbc.gank.R

import com.czrbc.gank.bean.GankBean
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_foot.view.*
import kotlinx.android.synthetic.main.item_gank.view.*

import java.util.ArrayList

/**
 * @author Gavin
 * *
 * @date 2017/06/21.
 */

public class GankAdapter(var mContext:Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var type:Int=0
    var page:Int = 0
    var pageSize:Int =10
    private val mGankList = ArrayList<GankBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==0){
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_gank,parent,false)
            return GankHolder(v)
        }
           val v = LayoutInflater.from(parent.context).inflate(R.layout.item_foot,parent,false)
        return FootHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GankHolder){
            val gank:GankBean  = getItem(position)
            if (gank.images!=null && gank.images!!.isNotEmpty()){
                holder.image.loadImageFromUrl(gank.images!![0])
            }
        }else if (holder is FootHolder){
            holder.progress.visibility =View.GONE
        }
    }

    fun getItem(position: Int): GankBean {
        return mGankList[position]
    }

    override fun getItemCount(): Int {
        return mGankList.size
    }

    internal class GankHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.image
        val text = itemView.text
    }

    internal class FootHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val progress = itemView.progress
    }
}
interface LoadData{
    fun load(rows:Int)
    fun loadMore(page:Int,pageSize:Int)

}

fun ImageView.loadImageFromUrl(url:String){
    Picasso.with(this.context).load(url).into(this)
}