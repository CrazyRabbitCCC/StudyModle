package lzy.com.money.Fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import lzy.com.money.Application.moneyApplication;
import lzy.com.money.Bean.Bill;
import lzy.com.money.Bean.Function;
import lzy.com.money.DateBase.DbHelper;
import lzy.com.money.R;
import lzy.com.money.SelfVIew.DividerDecoration;

/**
 * Created by Administrator on 2016/7/18.
 */
public class show extends Fragment {
    private EditText et_add;
    private Button btn_add;
    private RecyclerView bill_list;
    private Adapter adapter;
    private moneyApplication m;
    private TextView tv_gn, tv_money;
    private static int gn = 0, money = 0;

    private View view;
    private DbHelper dbHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.show, container, false);
            initView();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private void initView() {
        dbHelper = new DbHelper(getActivity());
        et_add = (EditText) view.findViewById(R.id.et_add);
        btn_add = (Button) view.findViewById(R.id.btn_add);
        bill_list = (RecyclerView) view.findViewById(R.id.his_list);
        tv_gn = (TextView) view.findViewById(R.id.tv_gn);
        tv_money = (TextView) view.findViewById(R.id.tv_money);
        m = (moneyApplication) getActivity().getApplication();


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_add.getText() != null && !et_add.getText().toString().isEmpty()) {
                    String message = "";
                    try {
                        Function function = new Function(et_add.getText().toString());
                        function.insert(dbHelper);
                        et_add.setText("");
                        message = "添加成功";
                    } catch (Exception e) {
                        message = "添加失败\n" +
                                "失败原因：已有该选项";
                    }
                    new AlertDialog.Builder(getActivity()).setTitle("添加选项").setMessage(message)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });

//        adapter = new BillAdapter(m.getBill(dbHelper));

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        bill_list.setLayoutManager(manager);

        adapter = new Adapter(moneyApplication.getBill(dbHelper));
        bill_list.setAdapter(adapter);

        bill_list.addItemDecoration(new StickyRecyclerHeadersDecoration(adapter));
        bill_list.addItemDecoration(new DividerDecoration(getActivity()));
        bill_list.setAdapter(adapter);


        tv_gn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowByGn();

            }
        });

//        tv_money.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShowByMoney();
//
//            }
//        });

    }

//    private void ShowByMoney() {
//
//        List<String> list = new ArrayList<>();
//        list.add("金额");//← ↑ → ↓
//        list.add("金额 ↓");
//        list.add("金额 ↑");
//
//        List<Bill> hisList = new ArrayList<>();
//        try {
//            hisList = m.getBill(dbHelper);
//        } catch (Exception e) {
//
//        }
//        if (money < list.size() - 1) {
//            money++;
//        } else {
//            money = 0;
//        }
//        List<Bill> hisShowList = getListByGn(hisList);
//        hisShowList = GroupByMoney(hisShowList);
//        tv_money.setText(list.get(money));
//        adapter.setList(hisShowList);
//        adapter.notifyDataSetChanged();
//    }

    private void ShowByGn() {
        List<Bill> hisList = new ArrayList<>();
        List<Bill> hisShowList = new ArrayList<>();
        List<Function> fList = new ArrayList<>();
        List<String> list = new ArrayList<>();

        try {
            hisList = m.getBill(dbHelper);
            fList = m.getFunction(dbHelper);
            for (Function f : fList) {
                list.add(f.get(1));
            }

        } catch (Exception e) {

        }
        list.add(0, "用途");
        if (gn < list.size() - 1) {
            gn++;
        } else {
            gn = 0;
        }
        tv_gn.setText(list.get(gn));
        hisShowList = getListByGn(hisList);
//        hisShowList = GroupByMoney(hisShowList);
        adapter.setList(hisShowList);
        adapter.notifyDataChanged();
    }

    private List<Bill> getListByGn(List<Bill> hisList) {
        List<Bill> hisShowList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        try {
            for (Function function : m.getFunction(dbHelper))
                list.add(function.get(1));

        } catch (Exception e) {

        }
        list.add(0, "用途");
        if (gn < list.size() && gn > 0) {
            for (Bill h : hisList) {
                if (h.get(1).equals(list.get(gn))) {
                    hisShowList.add(h);
                }
            }
        } else {
            hisShowList.addAll(hisList);
        }
        return hisShowList;
    }

//    private List<Bill> GroupByMoney(List<Bill> hisList) {
//        if (money == 1) {
//            for (int i = 1; i < hisList.size(); i++)
//                for (int j = 0; j < i; j++)
//                    if (hisList.get(i).getFloat() > hisList.get(j).getFloat()) {
//                        Bill h = hisList.get(i);
//                        hisList.remove(i);
//                        hisList.add(j, h);
//                        break;
//                    }
//        } else if (money == 2) {
//            for (int i = 1; i < hisList.size(); i++)
//                for (int j = 0; j < i; j++)
//                    if (hisList.get(i).getFloat() < hisList.get(j).getFloat()) {
//                        Bill h = hisList.get(i);
//                        hisList.remove(i);
//                        hisList.add(j, h);
//                        break;
//                    }
//        }
//        return hisList;
//
//    }

    class Adapter extends RecyclerView.Adapter<Holder>  implements StickyRecyclerHeadersAdapter<HeadHolder>{

        private List<Bill> list;
        private HashMap<String,Integer> header;

        public Adapter() {
            this.list=new ArrayList<>();
            header=new HashMap<>();
        }

        public Adapter(List<Bill> list) {
            this.list=list;
            header=new HashMap<>();
            HeaderChanged();
        }

        private void HeaderChanged(){
            int i=0;
            for (Bill bill:list){
                String s=bill.get(3);
                s=s.substring(0,10);
                if (!header.containsKey(s)){
                    header.put(s,i);
                    i++;
                }
            }

        }
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.his_list_item,null);
            return new Holder(itemView);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.tvGn.setText(list.get(position).get(1));
            holder.tvMoney.setText(list.get(position).get(2));
            holder.tvTime.setText(list.get(position).get(3));
        }

        @Override
        public long getHeaderId(int position) {
            return header.get(list.get(position).get(3).substring(0,10));
        }

        @Override
        public HeadHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            return new HeadHolder(itemView);
        }

        @Override
        public void onBindHeaderViewHolder(HeadHolder holder, int position) {
            // 先获取key的值
            Set<String> setKey = header.keySet();
            Iterator<String> iterator = setKey.iterator();
            String key="时间";
            while(iterator.hasNext()){
                key=iterator.next();
                if (header.get(key)==position){
                   break;
                }
            }
            holder.head.setText(key);
        }

        public void notifyDataChanged(){
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setList(List<Bill> list) {
            this.list.clear();
            this.list.addAll(list);
        }
    }

    private class Holder extends RecyclerView.ViewHolder{
        private TextView tvGn;
        private TextView tvMoney;
        private TextView tvTime;
        public Holder(View itemView) {
            super(itemView);
            tvGn = (TextView) itemView.findViewById(R.id.tv_gn);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return true;
                }
            });
        }
    }

    private class HeadHolder extends RecyclerView.ViewHolder {
        private TextView head;
        public HeadHolder(View itemView) {
            super(itemView);
            head= (TextView) itemView.findViewById(R.id.head);
        }
    }
}

