package com.android.mt.activity.money;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.android.mt.R;
import com.android.mt.activity.layout.WechatMoneyLayout;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import tools.AccessibilityUtils;
import tools.Log;
import tools.Tools;


@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
public class TaobaoService extends AccessibilityService implements Handler.Callback{

    private AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
    private List<AccessibilityNodeInfo> adsId;
    private static Boolean isCloseY = false;
    private static Boolean isCloseN = false;
    private String tag = "----my---";
    private final static String SPTag = "DELAY_TIME";
    private final static String timeTag = "config_time";
    private long delyedTime;
    private Tools tool;
    private Handler handler;
    private SharedPreferences sp;
    private int t;
    private static AccessibilityEvent access;
    private static boolean isStop = false;

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//		Log.show("---onDestroy---");
//		Autolock.recover();
    }

    private void initData(){
        handler = new Handler(Looper.getMainLooper(), this);
        tool = Tools.getInstance(getBaseContext());
        sp = getBaseContext().getSharedPreferences(SPTag, 0);
        delyedTime = sp.getLong(timeTag, 0);
    }

    public void onAccessibilityEvent(final AccessibilityEvent accessibilityEvent) {
        access = accessibilityEvent;
        Log.show("access赋值:" + access);

        initData();
        t = accessibilityEvent.getEventType();
        Log.show("Action:" + t);

        AccessibilityNodeInfo info = getRootInActiveWindow();
        Log.show(access + "\ninfo:" + info);
//        if ( tool.getCode().equals("")) {
//            return;
//        }
        switch (t) {
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = access.getClassName().toString();
                Log.show("改变页面：" + className);
                if (className.equals(TaobaoService.this.getResources().getString(R.string.taobao_gwc))
                    || className.equals(TaobaoService.this.getResources().getString(R.string.taobao_gwc_1))) {
                    doJob(access);
                } else if(className.equals(TaobaoService.this.getResources().getString(R.string.taobao_jiesuan))){
                    tjdd(access);
                } else if(className.equals(TaobaoService.this.getResources().getString(R.string.taobao_pay))){
                    isStop = true;
                    zhifu(access);
                } else if(className.equals(TaobaoService.this.getResources().getString(R.string.taobao_pay_password))){
//                    setPassword();
                }
                break;
        }
    }

    private void doJob(AccessibilityEvent event){
        Log.show("doJob:" + WechatMoneyLayout.delayTime);
        while (true) {
            if(getSeconds() >= (72000000 + WechatMoneyLayout.delayTime)) {
                jiesuan(event);
                break;
            }
        }
    }

    /**
     * 自动结算
     */
    private void jiesuan(AccessibilityEvent event){
        Log.show("自动结算");
        if(event.getSource() == null || event.getSource().getChildCount()==0 || ((event.getToIndex()<event.getItemCount()-1))){
            return;
        } else if(event.getSource().getChild(event.getSource().getChildCount()-1) != null){
            if(!AccessibilityUtils.clickByText(event.getSource().getChild(event.getSource().getChildCount() - 1), "结算(1)")){
                AccessibilityUtils.clickById(event.getSource().getChild(event.getSource().getChildCount() - 1), R.string.taobao_id + "button_cart_charge");
            }
        }
    }


    /**
     * 自动提交订单
     */
    private void tjdd(AccessibilityEvent event){
        Log.show("提交订单");
        if(event.getSource() == null || event.getSource().getChildCount()==0 || ((event.getToIndex()<event.getItemCount()-1))){
            return;
        } else if(event.getSource().getChild(event.getSource().getChildCount()-1) != null){
            while (!isStop) {
                AccessibilityUtils.clickByText(event.getSource().getChild(event.getSource().getChildCount() - 1), "提交订单");
            }
        }
    }

    /**
     * 自动输入支付密码
     */
    private void setPassword(){
        Log.show("自动设置密码");
        try {
            Process p = Runtime.getRuntime().exec("input text \"123456\"");
            Log.show("执行完毕");
            InputStreamReader isr = new InputStreamReader(p.getInputStream(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                line = line.trim();
                Log.show("执行结果：" + line);
                line = br.readLine();
            }
            br.close();
            //#if def{debuggable}
            p.destroy();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 自动支付
     */
    private void zhifu(AccessibilityEvent event){
        Log.show("自动支付");
        if(event.getSource() == null || event.getSource().getChildCount()==0 || ((event.getToIndex()<event.getItemCount()-1))){
            return;
        } else if(event.getSource().getChild(event.getSource().getChildCount()-1) != null){
            AccessibilityUtils.clickByText(event.getSource().getChild(event.getSource().getChildCount() - 1), "立即付款");
        }
    }

    //当前时间距离零点的毫秒数
    public static long getSeconds(){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        res = simpleDateFormat.format(date);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        try {
            Date tag = simpleDateFormat.parse(res);
            Log.show("当前毫秒：" + tag.getTime());
            return tag.getTime();
        } catch (Throwable e) {
        }
        return 0;
    }

    public void onInterrupt() {
        //    	   Log.show("---onInterrupt---onInterrupt");
    }

    @Override
    public boolean handleMessage(Message msg) {
        int action = msg.what;
        if(action == 1){
//		    openPacket();
        } else if(action == 2){
//		    closeNoMoney();
        }
        return false;
    }
}
