package tools;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.android.mt.R;

import java.util.List;


@SuppressWarnings("deprecation")
@SuppressLint("NewApi")

public class EditTextService extends AccessibilityService{
    public List<AccessibilityNodeInfo> adsId;
    private Tools tool;

	public void onAccessibilityEvent(AccessibilityEvent event) {

		tool = Tools.getInstance(getBaseContext());
		Log.show("执行");
		 AccessibilityNodeInfo msgs = getRootInActiveWindow();
	        if (msgs == null || tool.getCode().equals("")) {
	            return;
	        }
	        int t = event.getEventType();
	        switch (t) {
		        case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
		            Log.show("改变"+event.getClassName().toString());
		            String className = event.getClassName().toString();
		            if (className.equals(this.getResources().getString(R.string.chat))) {
		                reply();
		            }break;

		        case AccessibilityEvent.TYPE_VIEW_SCROLLED:
		            Log.show("滚动"+event.getClassName().toString());
		            String className1 = event.getClassName().toString();
		            if (className1.equals("android.widget.ListView")) {
		                reply();
		            }break;
	        }
	}


	public void reply(AccessibilityEvent event){
		Log.show("测试独立的类");
		AccessibilityNodeInfo source = event.getSource();
	    adsId = source.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id) + "aaa");
	    Log.show(adsId.toString());
		if (adsId!=null) {
			for (AccessibilityNodeInfo input : adsId) {
				Bundle arguments = new Bundle();
     	    	   	arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "本人没在看手机，请稍后联系（自动回复）");
     	    	   input.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
				Log.show("文本输入");
				adsId = source.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id)+"c70");
				for (AccessibilityNodeInfo send : adsId) {
					send.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					Log.show( "发送");
				}
			 }
		 }
	}

	public void reply(){
		Log.show("测试独立的类，获取窗体");
		AccessibilityNodeInfo source = getRootInActiveWindow();
		adsId = source.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id) + "aaa");
		Log.show(adsId.toString());
		if (adsId!=null) {
			for (AccessibilityNodeInfo input : adsId) {
				Bundle arguments = new Bundle();
				arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "本人没在看手机，请稍后联系（自动回复）");
				input.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
				Log.show("文本输入");
				adsId = source.findAccessibilityNodeInfosByViewId(this.getResources().getString(R.string.id)+"c70");
				for (AccessibilityNodeInfo send : adsId) {
					send.performAction(AccessibilityNodeInfo.ACTION_CLICK);
					Log.show( "发送");
				}
			}
		}
	}

	public void onInterrupt() {
		
	}

}