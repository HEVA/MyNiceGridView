package ty.com.myninegridview.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.android.volley.toolbox.ImageLoader.ImageCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ty.com.myninegridview.MyApplication;

/**
 * @author kelina
 * @version createTime: 2015年4月3日 下午4:36:51
 */
public class BitmapCache implements ImageCache {
	/** 单例 */
	private static BitmapCache cache;
	/** 内存缓存 */
	private LruCache<String, Bitmap> memory;

	/** 获取单例 */
	public static BitmapCache getInstance() {
		if (null == cache) {
			cache = new BitmapCache();
		}
		return cache;
	}


	public BitmapCache() {
		int maxSize = 10 * 1024 * 1024;
		memory = new LruCache<String, Bitmap>(maxSize) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}

		};
	}

	@Override
	public Bitmap getBitmap(String url) {
		// 获取图片
		try {
			String key = url;
			Bitmap bitmap = memory.get(key);
			if (bitmap!= null) {
				return bitmap;
			} else {
				String fileName = getFileName(key);
				boolean isExsit = FileUtils.fileIsExist(MyApplication.AppDirConstant.APP_IMAGE_DATA_DIR, fileName);
				boolean isBitmapComplete = false;
				if(isExsit){
					byte[] datas = FileUtils.readFiletoBytes(MyApplication.AppDirConstant.APP_IMAGE_DATA_DIR, fileName);
					if (datas.length > 0) {
						for (int i = datas.length - 1; i > datas.length - 64; --i) {
							if (datas[i] != 0) {
								isBitmapComplete = true;
								break;
							}
						}
					}

					if (isBitmapComplete) {
						bitmap = BitmapFactory.decodeByteArray(datas, 0, datas.length);
						return bitmap;
					}
				}
			}
		} catch (Exception e) {
			Log.d("halfman", e.getMessage());
		}
		return null;
	}
	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		try {
			String fileName = getFileName(url);
			boolean isExsit = FileUtils.fileIsExist(MyApplication.AppDirConstant.APP_IMAGE_DATA_DIR, fileName);
			boolean isBitmapComplete = false;
			if(isExsit){
				byte[] datas;
				datas = FileUtils.readFiletoBytes(MyApplication.AppDirConstant.APP_IMAGE_DATA_DIR, fileName);
				if (datas.length > 0) {
					for (int i = datas.length - 1; i > datas.length - 64; --i) {
						if (datas[i] != 0) {
							isBitmapComplete = true;
							break;
						}
					}
				}

				if (!isBitmapComplete) {
					FileUtils.deleteFileByRelativePath(MyApplication.AppDirConstant.APP_IMAGE_DATA_DIR, fileName);
					saveFile(bitmap,fileName);
				}
			}else{
				saveFile(bitmap,fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 放入图片到内存
		memory.put(url, bitmap);
	}

	private void saveFile(Bitmap bitmap,String fileName){
		try {
			File file = FileUtils.createSDFile(MyApplication.AppDirConstant.APP_IMAGE_DATA_DIR,fileName);
			FileOutputStream os = new FileOutputStream(file, false);
			if(fileName.toUpperCase().contains("PNG")){
				bitmap.compress(CompressFormat.PNG, 100, os);
			}else{
				bitmap.compress(CompressFormat.JPEG, 100, os);
			}

			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFileName(String fileUrl){
		int index=fileUrl.lastIndexOf("/");
		String fileName=fileUrl.substring(index+1);
		return fileName;
	}
}