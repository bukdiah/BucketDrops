package slidnerd.vivz.bucketdrops;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Kevin on 4/9/2016.
 */
public class AppBucketDrops extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration configuration= new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(configuration);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
