package com.example.uchat;

import android.net.Uri;
import android.os.StrictMode;

/**
 * @project: UChat
 * @package: com.example.uchat
 * @version: 1.0
 * @author: Habsah <habsah97@gmail.com>
 * @description: description
 * @since: 2019-05-20 17:46
 */
public class Application extends android.app.Application {

    public static Uri picUri;

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}
