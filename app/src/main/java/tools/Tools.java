package tools;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import com.android.mt.R;

import java.lang.reflect.Method;
import java.util.List;


public class Tools {

	private Context context;
	private static Tools tools;
	private String code;
	private static String WEIBO_PACKAGE_NAME = "com.sina.weibo";
	private static String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
	private static String QQ_LITE_PACKAGE_NAME = "com.tencent.qqlite";
	private static String TT_PACKAGE_NAME = "com.ss.android.article.lite";
	private static String CZRW_PACKAGE_NAME = "com.chizhouren.forum";
	private static String XHS_PACKAGE_NAME = "com.xingin.xhs";

	public static synchronized Tools getInstance(Context c) {
		if(tools == null && c != null){
			tools = new Tools(c);
		}
		return tools;
	}

	private Tools(Context context){
		this.context = context;
	}

	public static String getQQPackageName(){
		return QQ_PACKAGE_NAME;
	}

	public static String getQQLitePackageName(){
		return QQ_LITE_PACKAGE_NAME;
	}

	public static String getTtPackageName(){
		return TT_PACKAGE_NAME;
	}

	public static String getCZRWPackageName(){
		return CZRW_PACKAGE_NAME;
	}

	public static String getWeiboPackageName(){
		return WEIBO_PACKAGE_NAME;
	}

	public static String getXHSPackageName(){
		return XHS_PACKAGE_NAME;
	}

	public String getTaobaoCode(){
		PackageManager manager=context.getPackageManager();
		int i = 0;
		String code = ".";
		char[] charCode = new char[10];
		try {
			PackageInfo info = manager.getPackageInfo("com.taobao.taobao", 0);
			if(info != null){
				char key[]=code.toCharArray();
				char allCode[] =info.versionName.toString().toCharArray();
				int j=0;
				for(i=0; i<allCode.length; i++){
					if(allCode[i]==key[0]){
						j++;
						if(j==3){
							break;
						}
					}
					charCode[i] = allCode[i];
				}
				code=String.valueOf(charCode).substring(0, i);
			}else{
				code="";
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(!(code.equals("9.18.0"))){
			code="";
		}
		this.code = code;
		System.out.println("淘宝版本号：" + code);
		return code;
	}

	public String getCode(){
		PackageManager manager=context.getPackageManager();
		int i = 0;
		String code = ".";
		char[] charCode = new char[10];
		try {
			PackageInfo info = manager.getPackageInfo("com.tencent.mm", 0);
			if(info != null){
				char key[]=code.toCharArray();
				char allCode[] =info.versionName.toString().toCharArray();
				int j=0;
				for(i=0; i<allCode.length; i++){
					if(allCode[i]==key[0]){
						j++;
						if(j==3){
							break;
						}
					}
					charCode[i] = allCode[i];
				}
				code=String.valueOf(charCode).substring(0, i);
			}else{
				code="";
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if(!(code.equals("7.0.9") || code.equals("7.0.10") || code.equals("7.0.18") || code.equals("7.0.21"))){
			code="";
		}
		this.code = code;
		return code;
	}

	public String wechatVersion(int array){
		String chatOpen="";
		String open="";
		String close="";
		String noMoney="";
		String closeChat="";
		String moneyText="";
		String[] all= new String[6];
		if(code.equals("7.0.9")){
			chatOpen = "atb";
			open="dan";
			close="m0";
			noMoney="d84";
			closeChat="lr";
			moneyText="d62";
		} else if(code.equals("7.0.10")){
			chatOpen = "atb";
			open="dan";
			close="m0";
			noMoney="d84";
			closeChat="lr";
			moneyText="d62";
		} else if(code.equals("7.0.18")){
			chatOpen = "al7";
			open="den";
			close="dm";
			noMoney="dem";
			closeChat="rr";
			moneyText="d_h";
		} else if(code.equals("7.0.21")){
			chatOpen = "al7";
			open="den";
			close="dm";
			noMoney="dem";
			closeChat="rr";
			moneyText="d_h";
		}
		all[0] = chatOpen;
		all[1] = open;
		all[2] = close;
		all[3] = noMoney;
		all[4] = closeChat;
		all[5] = moneyText;
		return all[array];
	}

	public String wechatSearchV(int array){
		String[] all= new String[7];


		String searchIconBtn = "";//暂时用不到

		String inputPhoneEd = "";

		String findPhoneTv = "";

		String backUpBtn = "";

		String cleanTextBtn = "";

		String confirmText = "";

		String confirmBtn = "";


		if(code.equals("6.6.6")){
			searchIconBtn = "a8q";
			inputPhoneEd = "hx";
			findPhoneTv = "bay";
			backUpBtn = "i1";
			cleanTextBtn = "hy";
			confirmText = "c92";
			confirmBtn = "all";
		}
		all[0] = searchIconBtn;
		all[1] = inputPhoneEd;
		all[2] = findPhoneTv;
		all[3] = backUpBtn;
		all[4] = cleanTextBtn;
		all[5] = confirmText;
		all[6] = confirmBtn;
		return all[array];
	}

	public String infoTv(int array){

		String userWechatNum = "";
		//用户名字
		String userName = "";
		//用户性别
		String userSex = "";
		//备注和标签
		String userNote = "";
		//用户地区
		String userArea = "";
		//个性签名
		String userSignature = "";

		String[] all= new String[6];
		if(code.equals("6.6.6")){
			userWechatNum = "ang";
			userName = "q0";
			userSex = "anl";
			userNote = "ao0";
			userArea = "summary";//enable属性为false
			userSignature = "summary";//enable属性为true
		}
		all[0] = userWechatNum;
		all[1] = userName;
		all[2] = userSex;
		all[3] = userNote;
		all[4] = userArea;
		all[5] = userSignature;
		return all[array];
	}


	/**
	 * 判断某个辅助功能是否开启
	 */
	@SuppressWarnings("deprecation")
	public void checkSetting(){
		boolean isOpenAccessibility = true;
//		ConnectivityManager manager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		AccessibilityManager acc = (AccessibilityManager)context.getSystemService(Context.ACCESSIBILITY_SERVICE);
//		System.out.println("辅助功能信息："+acc.isEnabled()+"\n全部服务包："+acc.getAccessibilityServiceList().toString()+"\n已经打开的服务："+acc.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK).toString());
		List<AccessibilityServiceInfo> accessibilityServices = acc.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
		for (AccessibilityServiceInfo info : accessibilityServices) {
//			System.out.println("包名和类名信息:"+info.getId());
			if (info.getId().equals("cn.cjy.ztools/.money.ZService")
					|| info.getId().equals("cn.cjy.ztools/.money.TaobaoService")) {
				isOpenAccessibility = false;
				break;
			}
		}
		if(isOpenAccessibility){
			Toast.makeText(context, "辅助功能没有打开", Toast.LENGTH_LONG).show();
			new MusicPlay(context, R.raw.alert);
			Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		}
	}


	/**
	 * 判断某一Service是否正在运行
	 */
	public boolean isServiceRunning() {
		String serviceName = "cn.cjy.ztools.money.ListenerService";
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
		if (runningServiceInfos.size() <= 0) {
			return false;
		}
		for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
			if (serviceInfo.service.getClassName().equals(serviceName)) {
				return true;
			}
		}
		return false;
	}
//
//	public static Context getContext() {
//		try {
//			Object actThread = DeviceHelper.currentActivityThread();
//			if (actThread != null) {
//				Context app = ReflectHelper.invokeInstanceMethod(actThread, "getApplication");
//				if (app != null) {
////					Log.show("Context 不为空");
//					return app;
//				}
//			}
//		} catch (Throwable t) {
//			Log.showStackTrace(t);
//		}
//		return null;
//	}

	private void inut(){
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
// 发送通知
		manager.notify(1, null);
	}

	public static int[] getScreenSize(Context context) {
		WindowManager wm;
		try {
			wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		} catch (Throwable t) {
			wm = null;
		}
		if (wm == null) {
			return new int[] {0, 0};
		}

		Display display = null;
		try {
			display = wm.getDefaultDisplay();
		} catch (Throwable t) {
		}

		if (display == null) {
			try {
				DisplayMetrics dm = context.getResources().getDisplayMetrics();
				return new int[] {dm.widthPixels, dm.heightPixels};
			} catch (Throwable t) {
				return new int[] {0, 0};
			}
		} else if (Build.VERSION.SDK_INT < 13){
			try {
				DisplayMetrics dm = new DisplayMetrics();
				display.getMetrics(dm);
				return new int[] {dm.widthPixels, dm.heightPixels};
			} catch (Throwable t) {
				return new int[] {0, 0};
			}
		} else {
			try {
				Point size = new Point();
				Method method = display.getClass().getMethod("getRealSize", Point.class);
				method.setAccessible(true);
				method.invoke(display, size);
				return new int[] {size.x, size.y};
			} catch (Throwable t) {
				return new int[] {0, 0};
			}
		}
	}

	public static int getScreenWidth(Context context) {
		return getScreenSize(context)[0];
	}

	public static int getScreenHeight(Context context) {
		return getScreenSize(context)[1];
	}

	public static void openNotificationListenSettings(Context context) {
		try {
			Intent intent;
			if (Build.VERSION.SDK_INT >= 21) {
				intent = new Intent("android.settings.NOTIFICATION_LISTENER_SETTINGS");
			} else {
				intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
			}
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			try {
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				ComponentName cn = new ComponentName("com.android.settings","com.android.settings.Settings$NotificationAccessSettingsActivity");
				intent.setComponent(cn);
				intent.putExtra(":settings:show_fragment", "NotificationAccessSettings");
				context.startActivity(intent);
			} catch(Throwable t) {
				t.printStackTrace();
			}
		}
	}
}









