package com.android.mt.activity.layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import tools.Log;
import tools.Tools;


/**
 * Created by jychen on 2018/2/9.
 */

public class WechatMoneyLayout extends LinearLayout implements View.OnClickListener{

	private final static String SPTag = "DELAY_TIME";
	private final static String timeTag = "config_time";
	private final static String checkTimeTag = "check_config_time";
	public static long delayTime = 0;

	private EditText[] editTexts;  //秒
	private String[] platformName;
	private Button btnCloseService;
	private Context context;
	private SharedPreferences sp;
	private Editor editor;
	private Tools tools;

	public WechatMoneyLayout(Context context) {
		super(context);
		this.context = context;
		editTexts = new EditText[2];
		platformName = new String[]{"微信", "支付宝"};
		tools = Tools.getInstance(context);
		sp = context.getSharedPreferences(SPTag, 0);
		editor = sp.edit();
		delayTime = sp.getLong(timeTag, 0);
		initView();
	}

	public void initView(){
		setOrientation(LinearLayout.VERTICAL);
		LayoutParams lp;
		int i = 0;
		for(;;){
			if(i >= 1){
				break;
			}
			lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			TextView checkText = new TextView(context);
			checkText.setPadding(0, 20, 0, 20);
			checkText.setGravity(Gravity.CENTER);
			checkText.setBackgroundColor(0xffdddddd);
			checkText.setTextSize(18);
			checkText.setTextColor(Color.BLACK);
			addView(checkText, lp);

			editTexts[i] = new EditText(context);
			editTexts[i].setTextSize(18);
			editTexts[i].setInputType(EditorInfo.TYPE_CLASS_PHONE);
			editTexts[i].setCustomSelectionActionModeCallback(new ActionMode.Callback() {
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {return false;}
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {return false;}
				public boolean onActionItemClicked(ActionMode mode, MenuItem item) {return false;}
				public void onDestroyActionMode(ActionMode mode) {}
			});
			editTexts[i].setLongClickable(false);
			addView(editTexts[i], lp);

			Button btnSure = new Button(context);
			btnSure.setGravity(Gravity.CENTER);
			btnSure.setText("确定");
			btnSure.setTextSize(20);
			btnSure.setTag(i);
			lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 0, 0, 150);
			addView(btnSure, lp);
			btnSure.setOnClickListener(this);

			if(i == 0){
				checkText.setText("延迟抢红包：（单位：毫秒）");
				editTexts[i].setHint(String.valueOf(sp.getLong(timeTag, 0)) + "毫秒");
			} else if(i == 1){
				btnSure.setText("确定");
				checkText.setText("检查功能是否开启的间隔：（单位：分）");
				editTexts[i].setHint(String.valueOf(sp.getLong(checkTimeTag, 0)) + "分");
			}
			i++;
		}

		Spinner dcieSpinner = new Spinner(context);
		String[] textDcie = {"1", "2", "3", "4", "5", "6"};

		ArrayAdapter<String> dcieAdt = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, textDcie);
		dcieSpinner.setAdapter(dcieAdt);
		dcieSpinner.setOnItemSelectedListener(listener);
		dcieSpinner.setTag(1);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		addView(dcieSpinner, lp);

		Spinner fingerSpinner = new Spinner(context);
		String[] textFinger = {"剪刀", "石头", "布"};
		ArrayAdapter<String> fingerAdt = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, textFinger);
		fingerSpinner.setAdapter(fingerAdt);
		fingerSpinner.setOnItemSelectedListener(listener);
		fingerSpinner.setTag(2);
		lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		addView(fingerSpinner, lp);

		TextView message = new TextView(context);
		message.setPadding(0, 20, 0, 20);
		message.setGravity(Gravity.CENTER);
		message.setBackgroundColor(0xffdddddd);
		message.setText("喝水不忘挖井人");
		message.setTextSize(18);
		message.setTextColor(Color.BLACK);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(message, lp);

		LinearLayout payLayout = new LinearLayout(context);
		payLayout.setOrientation(LinearLayout.HORIZONTAL);
		i = 0;
		for(;;){
			if(i >= 2){
				break;
			}
			Button payBtn = new Button(context);
			payBtn.setGravity(Gravity.CENTER);
			payBtn.setTextSize(20);
			payBtn.setText(platformName[i]);
			payBtn.setTag(i + 2);
			payBtn.setOnClickListener(this);
			lp = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			lp.topMargin = 10;
			payLayout.addView(payBtn, lp);
			i++;
		}
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(payLayout, lp);

		RelativeLayout relativeLayout = new RelativeLayout(context);
		lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
		lp.gravity = Gravity.BOTTOM;
		addView(relativeLayout);
		btnCloseService = new Button(context);
		btnCloseService.setGravity(Gravity.CENTER);
		btnCloseService.setBackgroundColor(Color.RED);
		btnCloseService.setTag(5);
		btnCloseService.setText("关闭定时检测");
		if(tools.isServiceRunning()) {
			btnCloseService.setEnabled(true);
		} else {
			btnCloseService.setEnabled(false);
		}
		btnCloseService.setTextSize(20);
		RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		relativeLayout.addView(btnCloseService, rlp);
		btnCloseService.setOnClickListener(this);
		btnCloseService.setEnabled(true);
	}

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		if(tag == 0 || tag == 1){
			long delayTime = 0;
			if(!TextUtils.isEmpty(editTexts[tag].getText())){
				try {
					delayTime = Long.valueOf(editTexts[tag].getText().toString());
				} catch (Exception e){
					e.printStackTrace();
				}
				String endText = tag == 0 ? "毫秒" : "分";
				editTexts[tag].setHint(String.valueOf(delayTime) + endText);
				if(tag == 0) {
					Intent i = new Intent();
					i.setAction("COM.TENCENT.MM.RECEIVER");
					i.putExtra("delayTime", delayTime);
					context.sendBroadcast(i);
				}
				editor.putLong(tag == 0 ? timeTag : checkTimeTag, delayTime);
				editor.commit();
				WechatMoneyLayout.delayTime = delayTime;
			}
			editTexts[tag].setText("");
			editTexts[tag].clearFocus();
			if(tag == 1 && delayTime != 0){
//				context.startService(new Intent(context, ListenerService.class));
				if(tools.isServiceRunning()){
					btnCloseService.setEnabled(true);
				} else {
					btnCloseService.setEnabled(false);
				}
			}
			Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
		} else if(tag == 2){
			ImgDialog dialog = new ImgDialog(context);
			dialog.setPlatform(0);
			dialog.show();
		} else if(tag == 3){
			ImgDialog dialog = new ImgDialog(context);
			dialog.setPlatform(1);
			dialog.show();
		} else if(v == btnCloseService) {
			sendBroadcast();
//			btnCloseService.setEnabled(false);
//			context.stopService(new Intent(context, ListenerService.class));
		}
	}

	/** 广播错误日志 */
	private void sendBroadcast() {
		try {
			ext("am broadcast --user 0 -a android.intent.action.BATTERY_CHANGED");
//			Intent i = new Intent();
//			i.setAction("android.intent.action.BATTERY_CHANGED");
//			getContext().sendBroadcast(i);
			Log.show("发送广播成功");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private String ext(String cmd){
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
				output.append(buffer, 0, read);
			}
			reader.close();
			process.waitFor();
			return output.toString();
		} catch (Throwable e) {
		}
		return null;
	}


	AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			int dice = 0;
			int finger = 0;
			if(parent.getTag() == null){
				return;
			}
			int tag = Integer.valueOf(parent.getTag().toString());
			System.out.println("tag:" + tag + " position:" + position + "   " + view.getClass().getName());
			if(tag == 1){
				dice = position;
			} else if(tag == 2){
				finger = position;
			}
			Intent i = new Intent();
			i.setAction("COM.SPIDE.DEMO.INTENT.HOOK.RECEIVER");
			i.putExtra("dice", dice);
			i.putExtra("finger", finger);
			i.putExtra("type", tag);
			context.sendBroadcast(i);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			System.out.println("onNothingSelected");
		}
	};

}
