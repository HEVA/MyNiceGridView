package ty.com.myninegridview;

import android.app.Application;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import ty.com.myninegridview.util.BitmapCache;

/**
 * Created by Lenny on 2015/7/30.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private static ImageLoader imageLoader;


    public static MyApplication getInstance() {
        return instance;
    }

    public static ImageLoader getImageLoader() {
        if (imageLoader == null) {
            RequestQueue mQueue = Volley.newRequestQueue(instance);
            imageLoader = new ImageLoader(mQueue, BitmapCache.getInstance());
        }
        return imageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public interface AppDirConstant {
        public static final String APP_ROOT_DIR = "NineGridView";
        public static final String APP_APK_DIR = APP_ROOT_DIR + File.separator + "apk";
        public static final String APP_IMAGE_DATA_DIR = APP_ROOT_DIR + File.separator + "image";

    }

    /*  *
      * 从Assets中读取图片
    */
    public Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }
}
