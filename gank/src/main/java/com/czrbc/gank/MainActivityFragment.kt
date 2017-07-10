package com.czrbc.gank

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {

    private var v:View? =null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (v==null){
            v=inflater!!.inflate(R.layout.fragment_main, container, false)
            initView()
        }
        if (v!!.parent!=null){
            val parent:ViewGroup = v!!.parent as ViewGroup
            parent.removeView(v)
        }
        return v
    }
    fun initView(){

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }
    var mAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder> = null!!

    private fun initData() {
        v!!.rv.layoutManager = LinearLayoutManager(activity)
    }
     fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        mAdapter = adapter
        v!!.rv.adapter =mAdapter
        mAdapter.notifyDataSetChanged()
    }

    fun hello(x: String, y: String) {
        println(x + ": hello , " + y)
    }
}
