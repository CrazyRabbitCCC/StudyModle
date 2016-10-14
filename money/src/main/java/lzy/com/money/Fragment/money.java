package lzy.com.money.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lzy.com.money.Application.moneyApplication;
import lzy.com.money.Bean.Bill;
import lzy.com.money.Bean.Function;
import lzy.com.money.DateBase.DbHelper;
import lzy.com.money.R;

/**
 * Created by Administrator on 2016/7/12.
 */
public class money extends Fragment {
    View view;
    Spinner type;
    TextView tv;
    moneyApplication mApplication;
    boolean model = true;
    private EditText et;
    private Button btnChange;
    private String time;
    private int yyyy = 0, MM = 0, dd = 0, hh = 0, mm = 0, ss = 0;
    private NumberPicker year, month, day, hours, minute;
    private Calendar mMinDate, mMaxDate, mCurrentDate, tempDate;

    private DbHelper dbHelper;
    String[] mShortMonths;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.money, container, false);
            initView();

        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData();

    }

    void initView() {
        dbHelper = new DbHelper(getActivity());
        mApplication = (moneyApplication) getActivity().getApplication();
        tv = (TextView) view.findViewById(R.id.tv);
        type = (Spinner) view.findViewById(R.id.type);
        et = (EditText) view.findViewById(R.id.et);
        year = (NumberPicker) view.findViewById(R.id.year);
        month = (NumberPicker) view.findViewById(R.id.month);
        day = (NumberPicker) view.findViewById(R.id.day);
        hours = (NumberPicker) view.findViewById(R.id.hours);
        minute = (NumberPicker) view.findViewById(R.id.minute);

        year.setOnValueChangedListener(new ValueChange());
        month.setOnValueChangedListener(new ValueChange());
        day.setOnValueChangedListener(new ValueChange());
        hours.setOnValueChangedListener(new ValueChange());
        minute.setOnValueChangedListener(new ValueChange());

        mMinDate = Calendar.getInstance();
        mMaxDate = Calendar.getInstance();
        mCurrentDate = Calendar.getInstance();
        tempDate = Calendar.getInstance();


        view.findViewById(R.id.btn0).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn1).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn2).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn3).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn4).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn5).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn6).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn7).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn8).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btn9).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btnC).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btnT).setOnClickListener(new MyOnClick());
        btnChange = (Button) view.findViewById(R.id.btnChange);
        btnChange.setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btnDot).setOnClickListener(new MyOnClick());
        view.findViewById(R.id.btnSave).setOnClickListener(new MyOnClick());

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        yyyy = c.get(Calendar.YEAR);
        MM = c.get(Calendar.MONTH);
        dd = c.get(Calendar.DAY_OF_MONTH);
        hh = c.get(Calendar.HOUR_OF_DAY);
        mm = c.get(Calendar.MINUTE);
        ss=c.get(Calendar.SECOND);
        mShortMonths = new String[]{"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月","10月", "11月", "12月"};
    }

    void initData() {
        List<Function> functionList = mApplication.getFunction(dbHelper);
        List<String> adapterString = new ArrayList<>();
        for (Function function : functionList) {
            adapterString.add(function.get(1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, adapterString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        mMaxDate.setTimeInMillis(c.getTimeInMillis());
        mCurrentDate.setTimeInMillis(c.getTimeInMillis());
        c.add(Calendar.MINUTE, -mm);
        c.add(Calendar.HOUR_OF_DAY,-hh);
        c.add(Calendar.DAY_OF_MONTH,10-dd);
        c.add(Calendar.MONTH,8-MM);
        c.add(Calendar.YEAR,2016-yyyy);
        mMinDate.setTimeInMillis(c.getTimeInMillis());

//        mMaxDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),0);
//        mCurrentDate.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),0);


        updateSpinners();


    }

    class MyOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String s = tv.getText().toString();
            switch (v.getId()) {
                case R.id.btn0:
                    if (!s.isEmpty())
                        s += "0";
                    else s = "0";
                    break;
                case R.id.btn1:
                    s += "1";
                    break;
                case R.id.btn2:
                    s += "2";
                    break;
                case R.id.btn3:
                    s += "3";
                    break;
                case R.id.btn4:
                    s += "4";
                    break;
                case R.id.btn5:
                    s += "5";
                    break;
                case R.id.btn6:
                    s += "6";
                    break;
                case R.id.btn7:
                    s += "7";
                    break;
                case R.id.btn8:
                    s += "8";
                    break;
                case R.id.btn9:
                    s += "9";
                    break;
                case R.id.btnC:
                    s = "";
                    break;
                case R.id.btnT:
                    if (s.length() > 1)
                        s = s.substring(0, s.length() - 1);
                    else s = "";
                    break;
                case R.id.btnChange:
                    if (model) {
                        btnChange.setText("选择");
                        et.setVisibility(View.VISIBLE);
                        type.setVisibility(View.GONE);

                    } else {
                        btnChange.setText("自定义");
                        type.setVisibility(View.VISIBLE);
                        et.setVisibility(View.GONE);
                    }
                    model = !model;
                    break;
                case R.id.btnDot:
                    if (s.isEmpty()) {
                        s = "0.";
                    } else if (!(s.indexOf(".") > 0))
                        s += ".";

                    break;
                case R.id.btnSave:
                    if (tv.getText() == null || tv.getText().toString().isEmpty())
                        break;
                    Bill h;
                    if (model)
                        h = new Bill(type.getSelectedItem().toString(), tv.getText().toString() + " 元", time);
                    else {
                        h = new Bill(et.getText().toString(), tv.getText().toString() + " 元", time);
                        Function function = new Function(et.getText().toString());
                        try {
                            function.insert(dbHelper);
                            h.insert(dbHelper);
                            new AlertDialog.Builder(getActivity()).setTitle("保存").setMessage("添加成功")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "失败", Toast.LENGTH_SHORT).show();
                            new AlertDialog.Builder(getActivity()).setTitle("保存").setMessage("添加失败")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create().show();
                        }
                    }
                    break;
            }
            tv.setText(s);
        }
    }

    private void getTime(int yyyy, int MM, int dd, int hh, int mm) {
        time = "";
        if (MM < 10)
            time += yyyy + "/0" + MM;
        else
            time += yyyy + "/" + MM;

        if (dd < 10)
            time += "/0" + dd;
        else
            time += "/" + dd;
        if (hh < 10)
            time += " 0" + hh;
        else
            time += " " + hh;
        if (mm < 10)
            time += ":0" + mm;
        else
            time += ":" + mm;
        time = time.substring(2);
    }

    private class ValueChange implements NumberPicker.OnValueChangeListener {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            tempDate.setTimeInMillis(mCurrentDate.getTimeInMillis());

            switch (picker.getId()) {

                case R.id.minute:
                    if (oldVal == 59 && newVal == 0) {
                        tempDate.add(Calendar.MINUTE, 1);
                    } else if (oldVal == 0 && newVal == 59) {
                        tempDate.add(Calendar.MINUTE, -1);
                    } else {
                        tempDate.add(Calendar.MINUTE, newVal - oldVal);
                    }
                    break;
                case R.id.hours:
                    if (oldVal == 23 && newVal == 0) {
                        tempDate.add(Calendar.HOUR, 1);
                    } else if (oldVal == 0 && newVal == 23) {
                        tempDate.add(Calendar.HOUR, -1);
                    } else {
                        tempDate.add(Calendar.HOUR, newVal - oldVal);
                    }
                    break;
                case R.id.day:
                    int maxDayOfMonth = tempDate.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (oldVal == maxDayOfMonth && newVal == 1) {
                        tempDate.add(Calendar.DAY_OF_MONTH, 1);
                    } else if (oldVal == 1 && newVal == maxDayOfMonth) {
                        tempDate.add(Calendar.DAY_OF_MONTH, -1);
                    } else {
                        tempDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
                    }
                    break;
                case R.id.month:
                    if (oldVal == 11 && newVal == 0) {
                        tempDate.add(Calendar.MONTH, 1);
                    } else if (oldVal == 0 && newVal == 11) {
                        tempDate.add(Calendar.MONTH, -1);
                    } else {
                        tempDate.add(Calendar.MONTH, newVal - oldVal);
                    }
                    break;
                case R.id.year:
                    tempDate.set(Calendar.YEAR, newVal);
                    break;
                default:
                    throw new IllegalArgumentException();
            }


//            setDate(tempDate.get(Calendar.YEAR), tempDate.get(Calendar.MONTH), tempDate.get(Calendar.DAY_OF_MONTH), tempDate.get(Calendar.HOUR_OF_DAY), tempDate.get(Calendar.MINUTE));
            setDate(tempDate);
            updateSpinners();

        }


    }
    private void setDate(Calendar calendar) {
        mCurrentDate.setTimeInMillis(calendar.getTimeInMillis());
        if (mCurrentDate.before(mMinDate)) {
            mCurrentDate.setTimeInMillis(mMinDate.getTimeInMillis());
        } else if (mCurrentDate.after(mMaxDate)) {
            mCurrentDate.setTimeInMillis(mMaxDate.getTimeInMillis());
        }
    }

    private void setDate(int year, int month, int dayOfMonth, int hour, int minute) {
        mCurrentDate.set(year, month, dayOfMonth, hour, minute,ss);
        if (mCurrentDate.before(mMinDate)) {
            mCurrentDate.setTimeInMillis(mMinDate.getTimeInMillis());
        } else if (mCurrentDate.after(mMaxDate)) {
            mCurrentDate.setTimeInMillis(mMaxDate.getTimeInMillis());
        }
    }


    private void updateSpinners() {
        if (mCurrentDate.equals(mMinDate)) {
            minute.setMinValue(mCurrentDate.get(Calendar.MINUTE));
            minute.setMaxValue(mCurrentDate.getActualMaximum(Calendar.MINUTE));
            minute.setWrapSelectorWheel(false);
            hours.setMinValue(mCurrentDate.get(Calendar.HOUR_OF_DAY));
            hours.setMaxValue(mCurrentDate.getActualMaximum(Calendar.HOUR_OF_DAY));
            hours.setWrapSelectorWheel(false);
            day.setMinValue(mCurrentDate.get(Calendar.DAY_OF_MONTH));
            day.setMaxValue(mCurrentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
            day.setWrapSelectorWheel(false);
            month.setDisplayedValues(null);
            month.setMinValue(mCurrentDate.get(Calendar.MONTH));
            month.setMaxValue(mCurrentDate.getActualMaximum(Calendar.MONTH));
            month.setWrapSelectorWheel(false);
        } else if (mCurrentDate.equals(mMaxDate)) {
            minute.setMinValue(mCurrentDate.getActualMinimum(Calendar.MINUTE));
            minute.setMaxValue(mCurrentDate.get(Calendar.MINUTE));
            minute.setWrapSelectorWheel(false);
            hours.setMinValue(mCurrentDate.getActualMinimum(Calendar.HOUR_OF_DAY));
            hours.setMaxValue(mCurrentDate.get(Calendar.HOUR_OF_DAY));
            hours.setWrapSelectorWheel(false);
            day.setMinValue(mCurrentDate.getActualMinimum(Calendar.DAY_OF_MONTH));
            day.setMaxValue(mCurrentDate.get(Calendar.DAY_OF_MONTH));
            day.setWrapSelectorWheel(false);
            month.setDisplayedValues(null);
            month.setMinValue(mCurrentDate.getActualMinimum(Calendar.MONTH));
            month.setMaxValue(mCurrentDate.get(Calendar.MONTH));
            month.setWrapSelectorWheel(false);
        } else {
            minute.setMinValue(0);
            minute.setMaxValue(59);
            minute.setWrapSelectorWheel(true);
            hours.setMinValue(0);
            hours.setMaxValue(23);
            hours.setWrapSelectorWheel(true);
            day.setMinValue(1);
            day.setMaxValue(mCurrentDate.getActualMaximum(Calendar.DAY_OF_MONTH));
            day.setWrapSelectorWheel(true);
            month.setDisplayedValues(null);
            month.setMinValue(0);
            month.setMaxValue(11);
            month.setWrapSelectorWheel(true);
        }

        // make sure the month names are a zero based array
        // with the months in the month spinner
        String[] displayedValues = Arrays.copyOfRange(mShortMonths, month.getMinValue(), month.getMaxValue() + 1);
        month.setDisplayedValues(displayedValues);

        // year spinner range does not change based on the current date
        year.setMinValue(mMinDate.get(Calendar.YEAR));
        year.setMaxValue(mMaxDate.get(Calendar.YEAR));
        year.setWrapSelectorWheel(false);

        // set the spinner values
        year.setValue(mCurrentDate.get(Calendar.YEAR));
        month.setValue(mCurrentDate.get(Calendar.MONTH));
        day.setValue(mCurrentDate.get(Calendar.DAY_OF_MONTH));
        hours.setValue(mCurrentDate.get(Calendar.HOUR_OF_DAY));
        minute.setValue(mCurrentDate.get(Calendar.MINUTE));

    }


}
