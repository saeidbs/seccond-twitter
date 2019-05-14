package ir.mkp.second_twitter.utility;

import android.app.Application;

import ir.mkp.second_twitter.database.dataManager.DataManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Values.dataManager = DataManager.createDateManager(this);
    }
}
