package tools;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by jychen on 2018/8/10.
 */

@TargetApi(18)
public class AccessibilityUtils {

	/**
	 * 根据ID获取控件并点击
	 * @param info
	 * @param controlId
	 * @return
	 */
	public static boolean clickById(AccessibilityNodeInfo info, String controlId){
		try{
//			Log.show("Id:" + controlId);
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByViewId(controlId);
			if (adsId == null || adsId.toString().equals("[]")){
				Log.show("ClickById return null");
				return false;
			}
			for (AccessibilityNodeInfo nodeInfoViewId : adsId) {
				click(nodeInfoViewId);
				return true;
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 根据文本(getText或者)获取控件并点击
	 * @param info
	 * @param controlText
	 * @return
	 */
	public static boolean clickByText(AccessibilityNodeInfo info, String controlText){
		Log.show("检索的文本：" + controlText);
		try{
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByText(controlText);
			if (adsId == null || adsId.toString().equals("[]")){
				Log.show("clickByText return null");
				return false;
			}
			Log.show("clickByText:" + adsId.toString());
			for (AccessibilityNodeInfo nodeInfoViewId : adsId) {
				if((!TextUtils.isEmpty(nodeInfoViewId.getText())
						&& nodeInfoViewId.getText().equals(controlText))
						|| (!TextUtils.isEmpty(nodeInfoViewId.getContentDescription())
						&& nodeInfoViewId.getContentDescription().equals(controlText))){
					click(nodeInfoViewId);
					return true;
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 根据文本(getText或者)获取控件并点击
	 * @param info
	 * @param controlText
	 * @return
	 */
	public static boolean clickByTextP(AccessibilityNodeInfo info, String controlText){
		Log.show("检索的文本：" + controlText);
		try{
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByText(controlText);
			if (adsId == null || adsId.toString().equals("[]")){
				Log.show("clickByText return null");
				return false;
			}
			Log.show("clickByText:" + adsId.toString());
			for (AccessibilityNodeInfo nodeInfoViewId : adsId) {
				if((!TextUtils.isEmpty(nodeInfoViewId.getText())
						&& nodeInfoViewId.getText().equals(controlText))
						|| (!TextUtils.isEmpty(nodeInfoViewId.getContentDescription())
						&& nodeInfoViewId.getContentDescription().equals(controlText))){
					Log.show("本层是否可以点击：" + nodeInfoViewId.isClickable());
					Log.show("一层是否可以点击：" + nodeInfoViewId.getParent().isClickable());
					Log.show("二层是否可以点击：" + nodeInfoViewId.getParent().getParent().isClickable());
					click(nodeInfoViewId.getParent().getParent());
					return true;
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 实际点击，若此控件不支持点击，则寻找父级控件
	 * @param nodeInfoViewId
	 * @return
	 */
	private static boolean click(AccessibilityNodeInfo nodeInfoViewId){
		if(!nodeInfoViewId.isClickable()){
//			Log.show("即将点击父控件" + nodeInfoViewId);
			return nodeInfoViewId.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
		}
//		Log.show("即将点击子控件" + nodeInfoViewId);
		return nodeInfoViewId.performAction(AccessibilityNodeInfo.ACTION_CLICK);
	}

	/**
	 * 实际点击，遍历两层父级控件
	 * @param nodeInfoViewId
	 * @return
	 */
	public static boolean clickByP(AccessibilityNodeInfo nodeInfoViewId){
		if(!nodeInfoViewId.isClickable()){
			Log.show("即将点击父控件" + nodeInfoViewId);
			if(nodeInfoViewId.getParent().isClickable()) {
				return nodeInfoViewId.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
			} else{
				return nodeInfoViewId.getParent().getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
			}
		}
		Log.show("即将点击子控件" + nodeInfoViewId);
		return nodeInfoViewId.performAction(AccessibilityNodeInfo.ACTION_CLICK);
	}

	/**
	 * 根据ID获取多组相同ID的控件,且判断是否包含关键字，取第一个包含关键字的Item，
	 * @param info
	 * @param controlId
	 * @return
	 */
	public static boolean clickItemById(AccessibilityNodeInfo info, String controlId, String keyWord){
		try{
			AccessibilityNodeInfo tagsNodeInfo = null;
			int count = 0;
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByViewId(controlId);
			if (adsId == null || adsId.toString().equals("[]")){
				Log.show("ClickById return null");
				return false;
			}
			tags:for (AccessibilityNodeInfo nodeInfoViewId : adsId) {
				count ++;
				if(count ==1) {
					tagsNodeInfo = nodeInfoViewId;
				}
				for(int i = 0; i < nodeInfoViewId.getChildCount(); i++){
					AccessibilityNodeInfo child = nodeInfoViewId.getChild(i);
					if(child.getClassName().equals("android.widget.TextView")){
//					Log.show("检索到的文本：" + child.getText());
						if(child.getText() != null && child.getText().toString().contains(keyWord)){
							tagsNodeInfo = child.getParent();
							break tags;
						}
					}
				}
			}
			if(tagsNodeInfo != null && !tagsNodeInfo.toString().equals("[]")) {
				tagsNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
				return true;
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 根据Id滚动控件
	 * @param info
	 * @param controlId
	 * @return
	 */
	public static boolean scrollViewById(AccessibilityNodeInfo info, String controlId){
		//		Log.show("滚动view");
		try{
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByViewId(controlId);
			if (adsId == null || adsId.toString().equals("[]")){
				Log.show("clickByText return null");
				return false;
			}
			for (AccessibilityNodeInfo nodeInfoViewId : adsId) {
				nodeInfoViewId.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
				return true;
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 根据ID获取控件并输入
	 * @param info
	 * @param controlId
	 * @param inputText
	 * @return
	 */
	public static boolean inputTextById(AccessibilityNodeInfo info, String controlId, String inputText){
//		Log.show("controlId:" + controlId + " inputText:" + inputText) ;
		try{
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByViewId(controlId);
//			Log.show("result:" + adsId);
			if (adsId != null && !adsId.toString().equals("[]")) {
				for (AccessibilityNodeInfo input : adsId) {
					Bundle arguments = new Bundle();
					arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, inputText);
					input.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
					Log.show("Auto input text：" + inputText);
					return true;
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 根据文本获取控件并输入
	 * @param info
	 * @param controlText
	 * @param inputText
	 * @return
	 */
	public static boolean inputTextByText(AccessibilityNodeInfo info, String controlText, String inputText){
		try{
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByText(controlText);
//			Log.show("result:" + adsId);
			if (adsId != null && !adsId.toString().equals("[]")) {
				for (AccessibilityNodeInfo input : adsId) {
					Bundle arguments = new Bundle();
					arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, inputText);
					input.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
					Log.show("Auto input text：" + inputText);
					return true;
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 根据ID获取控件对应的文本描述
	 * @param info
	 * @param controlId
	 * @return
	 */
	public static String getText(AccessibilityNodeInfo info, String controlId){
		try{
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByViewId(controlId);
			if (adsId != null && !adsId.toString().equals("[]")) {
				for (AccessibilityNodeInfo nodeInfoViewId : adsId) {
					if(nodeInfoViewId.getText() != null){
						return nodeInfoViewId.getText().toString();
					}
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return "";
	}

	/**
	 * 根据ID获取全部控件对应的文本描述
	 * @param info
	 * @param controlId
	 * @return
	 */
	public static Set<String> getAllText(AccessibilityNodeInfo info, String controlId){
//		Log.show("获取全部控件文本");
		Set<String> set = new HashSet<String>();
		try{
			List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByViewId(controlId);
			if (adsId != null && !adsId.toString().equals("[]")) {
				for (AccessibilityNodeInfo nodeInfoViewId : adsId) {
					if(nodeInfoViewId.getText() != null){
						String name = (String)nodeInfoViewId.getText();
//						Log.show("获取到的昵称：" + name);
						set.add(name);
					}
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return set;
	}

	/**
	 * 判断一个控件是否存在
	 * @param info
	 * @param controlId
	 * @param controlText
	 * @return
	 */
	public static boolean isControlExist(AccessibilityNodeInfo info, String controlId, String controlText){
		try{
			List<AccessibilityNodeInfo> adsId;
			if(!TextUtils.isEmpty(controlId)) {
				adsId = info.findAccessibilityNodeInfosByViewId(controlId);
				if (adsId != null && !adsId.toString().equals("[]")) {
					return true;
				}
			}
			if(!TextUtils.isEmpty(controlText)) {
				adsId = info.findAccessibilityNodeInfosByText(controlText);
				if (adsId != null && !adsId.toString().equals("[]")) {
					return true;
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 判断一个控件是否存在，且文本和预想的一样
	 * @param info
	 * @param controlId
	 * @param text
	 * @return
	 */
	public static boolean isControlExistWithText(AccessibilityNodeInfo info, String controlId, String text){
		try{
			List<AccessibilityNodeInfo> adsId;
			if(!TextUtils.isEmpty(controlId)) {
				adsId = info.findAccessibilityNodeInfosByViewId(controlId);
				if (adsId != null && !adsId.toString().equals("[]")) {
					for(AccessibilityNodeInfo accessibilityNodeInfo : adsId){
						String name = (String)accessibilityNodeInfo.getText();
						return name.contains(text);
					}
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return false;
	}

	/**
	 * 模糊搜索控件，并点击
	 * @param infos
	 * @param viewName 控件的包名+类名
	 * @param keyWord 控件文本描述包含的关键字
	 */
	public static boolean likeControl(AccessibilityNodeInfo infos, String viewName, String keyWord){
		AccessibilityNodeInfo info;
		if(infos == null){
			return false;
		}
		for(int i = 0; i < infos.getChildCount(); i++){
			info = infos.getChild(i);
//			Log.show("类名：" + info.getClassName() + "  内容描述：" + info.getContentDescription());
			if(info == null || TextUtils.isEmpty(info.getClassName()) || TextUtils.isEmpty(info.getContentDescription())){
				continue;
			}
			try{
				if(info.getClassName().equals(viewName) && info.getContentDescription().equals(keyWord)){
					click(info);
					return true;
				}
			} catch (Throwable t){
				Log.showStackTrace(t);
				t.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 根据文本或者id返回一个控件
	 * @param info
	 * @param controlId
	 * @param controlText
	 * @return
	 */
	public static AccessibilityNodeInfo getControl(AccessibilityNodeInfo info, String controlId, String controlText){
		try{
			List<AccessibilityNodeInfo> result = null;
			if(!TextUtils.isEmpty(controlId)) {
				List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByViewId(controlId);
				if (adsId != null && !adsId.toString().equals("[]")) {
					result = adsId;
				}
			}
			if(!TextUtils.isEmpty(controlText)) {
				List<AccessibilityNodeInfo> adsId = info.findAccessibilityNodeInfosByText(controlText);
				if (adsId != null && !adsId.toString().equals("[]")) {
					result = adsId;
				}
			}
			if (result != null && !result.toString().equals("[]")) {
				for (AccessibilityNodeInfo accessibilityNodeInfo : result) {
					if(accessibilityNodeInfo != null && !accessibilityNodeInfo.toString().equals("[]")){
						return accessibilityNodeInfo;
					}
				}
			}
		} catch (Throwable t){
			Log.showStackTrace(t);
		}
		return null;
	}

	/**
	 * 点击通知栏
	 * @param event
	 * @return
	 */
	public static boolean clickNotifition(AccessibilityEvent event){
		if (event.getParcelableData() != null && event.getParcelableData() instanceof Notification) {
			Notification notification = (Notification) event.getParcelableData();
			PendingIntent pendingIntent = notification.contentIntent;
			try {
				pendingIntent.send();
				Log.show("打开通知栏");
				return true;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
