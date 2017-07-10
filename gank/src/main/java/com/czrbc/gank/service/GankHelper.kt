package com.czrbc.gank.service

import com.czrbc.gank.ViewListener
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author  Gavin
 * @date 2017/06/20.
 */
class GankHelper(var viewListener: ViewListener) {
    private val baseUrl = "http://gank.io/"
    private val gankIoService:GankIoService = createRetrofitService(GankIoService::class.java)
    private val map:Map<Int,String> = initMap();


    /**
     * @param dataType 数据类型： 0->福利 | 1->Android | 2->iOS | 3->休息视频 | 4->拓展资源 | 5->前端 | 6->all
     * @param row 请求个数： 数字，大于0
     * @param page 第几页：数字，大于0
     * @return
     */
    fun getData(dataType:Int , row:Int , page:Int ){
         var s:String? = map[dataType]
        if (s==null)
            s="all"
        gankIoService.getData(s,row,page)
                .filter{  gankResult->!gankResult.error }
                .flatMap { gankResult -> Observable.fromIterable(gankResult.results) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({gankBean->
                    viewListener.addData(1,gankBean)
                },{throwable->
                    viewListener.showError(1,throwable.message!!)
                    Logger.e(throwable,"ERROR")
                })
    }


     private fun <T> createRetrofitService(service: Class<T>): T {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
        val retrofit = Retrofit.Builder()
                .client(addBuilder(builder).build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
        return retrofit.create(service)
    }

    fun addBuilder( builder: OkHttpClient.Builder?): OkHttpClient.Builder {
        var builder:OkHttpClient.Builder? = builder
        if (builder == null) {
            builder = OkHttpClient.Builder()
        }
        return builder
    }

    private fun initMap(): Map<Int, String> {
        val map = HashMap<Int, String>()
//      0->福利 | 1->Android | 2->iOS | 3->休息视频 | 4->拓展资源 | 5->前端 | 6->all
        map.put(0, "福利")
        map.put(1, "Android")
        map.put(2, "iOS")
        map.put(3, "休息视频")
        map.put(4, "拓展资源")
        map.put(5, "前端")
        map.put(6, "all")
        return map
    }

}