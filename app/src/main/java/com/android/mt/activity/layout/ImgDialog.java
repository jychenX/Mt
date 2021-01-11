package com.android.mt.activity.layout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.android.mt.R;

import tools.Tools;

/**
 * Created by jychen on 2018/2/11.
 */

public class ImgDialog extends Dialog {

	private Context context;
	private int platformId;

	public ImgDialog(Context context) {
		super(context);
		this.context = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public void setPlatform(int platformId){
		this.platformId = platformId;
		initView();
	}

	private void initView(){
		getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		int resid;
		if(platformId == 0){
			resid = R.mipmap.wechatpay;
		} else {
			resid = R.mipmap.alipay;
		}
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setBackgroundColor(Color.TRANSPARENT);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		linearLayout.setGravity(Gravity.CENTER);
		ImageView iv = new ImageView(context);
		iv.setBackgroundResource(resid);
//		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), resid);
//		iv.setLayoutParams(new FrameLayout.LayoutParams(bm.getWidth(), bm.getHeight()));
		LayoutParams flp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		int width = Tools.getScreenWidth(context);
		int height = Tools.getScreenHeight(context);
		flp.setMargins(width/9, height/6, width/9, height/6);
		linearLayout.addView(iv, flp);
		setContentView(linearLayout);
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		setCancelable(true);
	}
}
