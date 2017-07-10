package com.czrbc.gank.bean

import com.google.gson.annotations.SerializedName

// @author: lzy  time: 2017/07/10.


class DayResult{
    var error:Boolean = false
    var category:List<String> = null!!
    var results:String = null!!


    internal class Result {
        @SerializedName("Android")
        var androids: List<GankBean>? = null
        @SerializedName("iOS")
        var ioss: List<GankBean>? = null
        @SerializedName("休息视频")
        var relax: List<GankBean>? = null
        @SerializedName("拓展资源")
        var expand: List<GankBean>? = null
        @SerializedName("瞎推荐")
        var recommend: List<GankBean>? = null
        @SerializedName("福利")
        var welfare: List<GankBean>? = null
    }
}
