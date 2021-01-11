package tools;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.os.PowerManager;

@SuppressWarnings("deprecation")
public class Autolock {

    // 键盘锁  
    private static KeyguardLock mKeyguardLock;  
    // 唤醒锁  
    private static PowerManager.WakeLock mWakeLock;  

    
	public static void start(PowerManager pm, KeyguardManager km){
    	// 点亮亮屏  
        mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "Tag");  
        mWakeLock.acquire();  
        // 初始化键盘锁  
        mKeyguardLock = km.newKeyguardLock("");  
        // 键盘解锁  
        mKeyguardLock.disableKeyguard();  
    }
    
    public static void recover(){
    	 if (mWakeLock != null) {  
             System.out.println("----> 终止服务,释放唤醒锁");
             //mWakeLock.release();
             mWakeLock = null;  
         }  
         if (mKeyguardLock!=null) {  
             System.out.println("----> 终止服务,重新锁键盘");  
             //mKeyguardLock.reenableKeyguard();
         }  
    }
}
