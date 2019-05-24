package cn.lixingyu.xylblog;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePalApplication;

/**
 * @author lxxxxxxy
 * @time 2019/5/20 08:37
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        LitePalApplication.initialize(context);
    }

    public static Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
