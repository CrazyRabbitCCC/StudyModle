package com.czrbc.gank


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import com.czrbc.gank.adapter.LoadData

class MainActivity : AppCompatActivity() ,ViewListener{

    val androidFragment:MainActivityFragment = MainActivityFragment()
    val iosFragment:MainActivityFragment = MainActivityFragment()
    val fuliFragment:MainActivityFragment = MainActivityFragment()
    val relaxFragment:MainActivityFragment = MainActivityFragment()
    val moreFragment:MainActivityFragment = MainActivityFragment()
    val foreEndFragment:MainActivityFragment = MainActivityFragment()
    val allFragment:MainActivityFragment = MainActivityFragment()
    //      0->福利 | 1->Android | 2->iOS | 3->休息视频 | 4->拓展资源 | 5->前端 | 6->all

    var appbar: AppBarLayout =null!!
    var toolbar: Toolbar =null!!
    var tabLayout: TabLayout  =null!!
    var viewPager: ViewPager  =null!!

    var pager:MyPager = null!!

    val list:ArrayList<Fragment> =  ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appbar =  findViewById(R.id.appbar) as AppBarLayout
        toolbar = findViewById(R.id.toolbar) as Toolbar
        tabLayout = findViewById(R.id.tab_layout) as TabLayout
        viewPager = findViewById(R.id.view_pager) as ViewPager

        list.add(fuliFragment)
        list.add(androidFragment)
        list.add(iosFragment)
        list.add(relaxFragment)
        list.add(moreFragment)
        list.add(foreEndFragment)
        list.add(allFragment)

        val loadData=object :LoadData{
            override fun load(rows:Int){

            }
            override fun loadMore(page:Int, pageSize:Int){

            }
        }
        for (i in list.indices){

        }

        pager = MyPager(supportFragmentManager,list)
        viewPager.adapter=pager
        tabLayout.setupWithViewPager(viewPager,true)

    }

    override fun clearData(type: Int) {
    }
    override fun showError(type: Int, msg: String) {
    }

    override fun addData(type: Int, data: Any) {

    }
    class MyPager(fm: FragmentManager?,list:ArrayList<Fragment>) : FragmentPagerAdapter(fm) {
        var list:ArrayList<Fragment> =  ArrayList()
        var title:ArrayList<String> = ArrayList()
        init {
            this.list.clear()
            this.list.addAll(list)
            title.add("福利")
            title.add("android")
            title.add("IOS")
            title.add("休息视频")
            title.add("拓展资源")
            title.add("前端")
            title.add("all")
        }

        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): android.support.v4.app.Fragment {
            return list[position]
        }

        override fun getPageTitle(position: Int): CharSequence {
            return title[position]
        }
    }
}
