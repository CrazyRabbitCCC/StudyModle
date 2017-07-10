package czrbt.lzy.record.bean;
// @author: lzy  time: 2016/11/09.


import java.util.ArrayList;
import java.util.List;

public class VecHelper {
    public static final int TITLE=1<<1;
    public static final int MESSAGE=1<<2;
    public static final int Til_Msg=1<<3;
    public static final List<Save> list=new ArrayList<>();

    public static void add(Save save){
        list.add(save);
    }
    public static  Save getLast(){
        return list.get(list.size()-1);
    }

    public static Save get(int position){
       return list.get(position);
    }

    public  static void removeLast(){
        list.remove(list.size()-1);
    }
//
//    public static class yySave{
//        String function="";
//        String arg1;
//        String arg2;
//        int type =Til_Msg;
//
//        public String getArg1() {
//            return arg1;
//        }
//
//        public void setArg1(String arg1) {
//            this.arg1 = arg1;
//        }
//
//        public String getArg2() {
//            return arg2;
//        }
//
//        public void setArg2(String arg2) {
//            this.arg2 = arg2;
//        }
//
//        public String getFunction() {
//            return function;
//        }
//
//        public void setFunction(String function) {
//            this.function = function;
//        }
//
//        public int getType() {
//            return type;
//        }
//
//        public void setType(int type) {
//            this.type = type;
//        }
//    }
}
