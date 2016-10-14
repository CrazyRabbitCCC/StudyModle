package lzy.com.money.Fragment;// @author: lzy  time: 2016/09/07.

import android.app.Activity;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import lzy.com.money.Application.moneyApplication;
import lzy.com.money.LockView.lockActivities.CreateGesturePasswordActivity;
import lzy.com.money.LockView.lockActivities.GuideGesturePasswordActivity;
import lzy.com.money.R;

public class system extends Fragment {
    private static final int RESULT_OK = 0;
    private View view;
    private Switch floatShow;
    private Switch homeOnly;
    private Switch barResident;
    private Switch timingAlerts;
    private LinearLayout timeLayout;
    private TextView time;
    private TextView password;
    private moneyApplication mApp;
    private TimePickerDialog TPD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.system_config, container, false);
            initView();

        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    private void initView() {
        mApp = (moneyApplication) getActivity().getApplication();
        floatShow = (Switch) view.findViewById(R.id.float_show);
        homeOnly = (Switch) view.findViewById(R.id.home_only);
        barResident = (Switch) view.findViewById(R.id.bar_resident);
        timingAlerts = (Switch) view.findViewById(R.id.timing_alerts);
        timeLayout = (LinearLayout) view.findViewById(R.id.time_layout);
        time = (TextView) view.findViewById(R.id.time);
        password = (TextView) view.findViewById(R.id.password);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initData();

    }

    private void initData() {
        floatShow.setChecked(mApp.getSystemConfig(0));
        homeOnly.setChecked(mApp.getSystemConfig(4));
        barResident.setChecked(mApp.getSystemConfig(1));
        timingAlerts.setChecked(mApp.getSystemConfig(2));
        setPassword();
        floatShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mApp.setSystemConfig(0, isChecked);
                if (isChecked) {
                    homeOnly.setVisibility(View.VISIBLE);
                } else
                    homeOnly.setVisibility(View.GONE);
            }
        });
        homeOnly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mApp.setSystemConfig(4, isChecked);
            }
        });
        barResident.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mApp.setSystemConfig(1, isChecked);
            }
        });
        timingAlerts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mApp.setSystemConfig(2, isChecked);
                if (isChecked) {
                    timeLayout.setVisibility(View.VISIBLE);
                } else
                    timeLayout.setVisibility(View.GONE);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),CreateGesturePasswordActivity.class);
                startActivityForResult(i, 9651);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TPD != null && TPD.isShowing())
                    return;
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int HH = Calendar.HOUR_OF_DAY;
                int mm = Calendar.MINUTE;
                String[] a = time.getText().toString().split(":");
                if (a.length > 1) {
                    HH = Integer.parseInt(a[0]);
                    mm = Integer.parseInt(a[1]);
                }
                TPD = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        DecimalFormat format = new DecimalFormat("#,#0.0");
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        numberFormat.getCurrency();
                        format.setMinimumIntegerDigits(4);
                        format.setMaximumFractionDigits(0);
                        String sTime = format.format(hourOfDay * 100 + minute);
                        sTime = sTime.replace(',', ':');
                        time.setText(sTime);

                        mApp.setSystemConfig("2-1", sTime);
                    }
                }, HH, mm, true);
                TPD.show();
            }
        });

    }

    public void setPassword() {
        String pwText = "密码保护";
        LinearLayout.LayoutParams params;
        if (mApp.getSystemConfig(3)) {
            pwText += "\n\n已启用";
            params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,72);
            switch (mApp.getSystemConfig("3")) {
                case "1":
                    pwText += "\t数字密码";
                    break;
                case "2":
                    pwText += "\t图案密码";
                    break;
                case "3":
                    pwText += "\t图片隐藏密码";
                    break;
                case "4":
                    pwText +="\t汉字密码";
                    break;
            }
        }else {
            params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,36);
        }
        password.setText(pwText);
        password.setLayoutParams(params);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 9651:
                switch (resultCode){
                    case Activity.RESULT_OK:
                        setPassword();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
