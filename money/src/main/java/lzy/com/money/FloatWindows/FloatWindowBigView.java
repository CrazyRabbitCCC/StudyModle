package lzy.com.money.FloatWindows;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lzy.com.money.Application.moneyApplication;
import lzy.com.money.Bean.Bill;
import lzy.com.money.Bean.Function;
import lzy.com.money.DateBase.DbHelper;
import lzy.com.money.R;

public class FloatWindowBigView extends LinearLayout {

	/**
	 * 记录大悬浮窗的宽度
	 */
	public static int viewWidth;

	/**
	 * 记录大悬浮窗的高度
	 */
	public static int viewHeight;
	private DbHelper dbHelper;
	private Spinner spinner;
	private EditText edit;

	public FloatWindowBigView(final Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.float_window_big, this);
		View view = findViewById(R.id.big_window_layout);
		viewWidth = view.getLayoutParams().width;
		viewHeight = view.getLayoutParams().height;
		Button save = (Button) findViewById(R.id.save);
		spinner= (Spinner) findViewById(R.id.spinner);
		edit= (EditText) findViewById(R.id.edit);
		Button back = (Button) findViewById(R.id.back);
		dbHelper=new DbHelper(context);
		List<Function> functionList=moneyApplication.getFunction(dbHelper);
		List<String> list=new ArrayList<>();
		for (Function function:functionList)
		list.add(function.get(1));
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Bill bill=new Bill();
				if (!checkDate())
					return;
				if (edit.getText()==null||edit.getText().toString().isEmpty())
					return;
				if (spinner.getSelectedItem()==null||spinner.getSelectedItem().toString().isEmpty())
					return;
				SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm");
				Bill bill=new Bill(spinner.getSelectedItem().toString(),edit.getText().toString(),format.format(new Date()));
				try {
					bill.insert(dbHelper);
					Toast.makeText(context,"添加成功",Toast.LENGTH_SHORT).show();
				}
				catch (Exception e){
					Toast.makeText(context,"添加失败",Toast.LENGTH_SHORT).show();
				}
				// 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
//				MyWindowManager.removeBigWindow(context);
//				MyWindowManager.removeSmallWindow(context);
//				Intent intent = new Intent(getContext(), FloatWindowService.class);
//				context.stopService(intent);
			}
		});
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 点击返回的时候，移除大悬浮窗，创建小悬浮窗
				MyWindowManager.removeBigWindow(context);
				MyWindowManager.createSmallWindow(context);
			}
		});
	}

	private boolean checkDate() {
		if (spinner.getSelectedItem()==null||spinner.getSelectedItem().toString().isEmpty())
		return false;
		if (edit.getText()==null||edit.getText().toString().isEmpty())
			return false;
		return true;
	}
}
