package com.czrbc.gank.service

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
    fun getData(@Path("dataType") dataType:String, @Path("rows") rows:Int, @Path("page") page:Int) : Observable<GankResult>

}