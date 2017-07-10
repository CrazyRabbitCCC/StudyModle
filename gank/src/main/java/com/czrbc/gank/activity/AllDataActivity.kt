package com.czrbc.gank.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.czrbc.gank.R

class AllDataActivity : AppCompatActivity() {

    var rv: RecyclerView = null!!
    var refresh:SwipeRefreshLayout = null!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_data)
        rv=findViewById(R.id.rv) as RecyclerView
        refresh = findViewById(R.id.refresh) as SwipeRefreshLayout
    }




    internal class MyAdapter: RecyclerView.Adapter<MyHolder>() {
        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onBindViewHolder(holder: MyHolder?, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    internal class MyHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
        init {

        }

    }
}
