package com.czrbc.gank.service

import com.czrbc.gank.bean.DayResult
import com.czrbc.gank.bean.GankBean
import com.czrbc.gank.bean.GankResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author  Gavin
 * @date 2017/06/20.
 */
interface GankIoService {
//http://gank.io/api/data/数据类型/请求个数/第几页
    @GET("api/data/{dataType}/{rows}/{page}")
    fun getData(@Path("dataType") dataType:String, @Path("rows") rows:Int, @Path("page") page:Int) : Observable<GankResult<GankBean>>

    @GET("api/day/history")
    fun getHistory():Observable<GankResult<String>>

    @GET("api/day/{year}/{month}/{day}")
    fun getDayHistory(@Path("year")year:Int,@Path("month")month:Int,@Path("day")day:Int):Observable<DayResult>
   // http://gank.io/api/day/2015/08/06

}