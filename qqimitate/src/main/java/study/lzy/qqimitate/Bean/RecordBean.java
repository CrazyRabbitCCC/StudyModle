package study.lzy.qqimitate.Bean;
// @author: lzy  time: 2016/09/26.


import java.util.List;

import study.lzy.qqimitate.Info.Record;

public class RecordBean {

    /**
     * chat : {"Other":"otherInfo","UserId":"1","UserName":"刘"}
     * chatWith : {"Other":"otherInfo","UserId":"2","UserName":"李"}
     * time : 2016/08/08
     * message : 你好4，我是刘
     * id : 1
     */

    private List<Record> RecordList;

    public List<Record> getRecordList() {
        return RecordList;
    }

    public void setRecordList(List<Record> RecordList) {
        this.RecordList = RecordList;
    }
}
