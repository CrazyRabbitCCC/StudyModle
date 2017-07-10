package udacity.lzy.test.bean;
// @author: lzy  time: 2016/11/09.


import java.util.ArrayList;
import java.util.List;

public class VecHelper {

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

    public static void clear() {
        list.clear();
    }
}
