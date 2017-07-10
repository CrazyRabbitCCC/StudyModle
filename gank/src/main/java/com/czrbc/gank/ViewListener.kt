package com.czrbc.gank

/**
 * @author Gavin
 * *
 * @date 2017/06/21.
 */

interface ViewListener {
    fun addData(type: Int, data: Any)
    fun clearData(type: Int)
    fun showError(type: Int, msg: String)

}
