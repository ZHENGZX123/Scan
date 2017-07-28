package scan.scanmoudle;

import android.app.Application;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/5/18.
 */

public class App extends Application {
    public OkHttpClient okHttpClient = new OkHttpClient();
}
