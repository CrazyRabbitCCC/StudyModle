package com.czrbc.gank.bean

/**
 * @author  Gavin
 * @date 2017/06/20.
 */
class GankResult<T>{

    var error: Boolean = true
    var results: List<T> = ArrayList<T>()
}