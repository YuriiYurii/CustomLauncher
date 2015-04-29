package com.example.yuriitsap.customlauncher;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by yuriitsap on 29.04.15.
 */
public class AppLoader extends AsyncTaskLoader {

    public AppLoader(Context context) {
        super(context);
    }

    @Override
    public Object loadInBackground() {

        return null;
    }
}
