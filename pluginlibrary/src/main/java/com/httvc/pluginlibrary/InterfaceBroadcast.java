package com.httvc.pluginlibrary;

import android.content.Context;
import android.content.Intent;

public interface InterfaceBroadcast {
    public void attach(Context context);
    public void onReceive(Context context, Intent intent);
}
