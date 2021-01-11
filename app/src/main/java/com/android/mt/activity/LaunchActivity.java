package com.android.mt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.android.mt.activity.layout.WechatMoneyLayout;

import tools.Tools;

/**
 * Created by jychen on 2018/2/8.
 */

public class LaunchActivity extends Activity {

	private LinearLayout mainLayout;
//	private

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		AlibcTradeSDK.asyncInit();
		initView();
		setContentView(mainLayout);
	}

	private void initView(){
		mainLayout = new LinearLayout(this);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		mainLayout.setLayoutParams(lp);
//		mainLayout.addView(new QQGroudLayout(this), lp);
		mainLayout.addView(new WechatMoneyLayout(this), lp);
		Tools.getInstance(getBaseContext()).getCode();
//		new Sen();
	}
}
