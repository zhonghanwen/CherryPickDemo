package cmcm.cherry.pick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.widget.Toast;

import java.io.File;
import java.net.URI;

public class WebViewDownloadListener implements DownloadListener {

  private static final String TAG = WebViewDownloadListener.class.getSimpleName();
  public Context mContext;

  public WebViewDownloadListener(Context context) {
    mContext = context;
  }

  @Override
  public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
    Log.d(TAG, "onDownloadStart" + "url = " + url);
    String[] splitArr = url.split("/");
    String fileName = "";
    for (String str : splitArr) {
      if (str.endsWith(".apk")) {
        fileName = str;
        break;
      }
    }

    downloadFileMgr(url, fileName);
    Toast.makeText(mContext, fileName + " start download", Toast.LENGTH_LONG).show();
    if (mContext instanceof Activity) {
      ((Activity) mContext).finish();
    }
  }

  /**
   * API 9以上才调用的方法
   */
  @SuppressLint("NewApi")
  private long downloadFileMgr(String strUrl, String filename) {
//		Log.e("ccc", "downloadFileMgr strUrl = " + strUrl + ", fileName = " + filename);
    DownloadManager mdownloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
    DownloadManager.Request request = null;
    try {
      request = new DownloadManager.Request(Uri.parse(strUrl));
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (request == null) {
      return 0l;
    }

    String host = "";
    try {
      host = new URI(strUrl).getHost();
    } catch (Exception e) {
      e.printStackTrace();
    }
    String cookieStr = CookieManager.getInstance().getCookie(host);
    if (cookieStr != null) {
      request.addRequestHeader("Cookie", cookieStr + "; AcSe=0");
    }

    // 要求API 11
    request.allowScanningByMediaScanner();
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

    if (existSDCard()) {
      File sd = Environment.getExternalStorageDirectory();
      String strPath = sd.getPath() + "/" + Environment.DIRECTORY_DOWNLOADS;
      File file = new File(strPath);
      if (!file.exists()) {
        file.mkdir();
      }
      request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
    }

    return mdownloadManager.enqueue(request);
  }

  /**
   * 检查SDcard的存在状态
   */
  private boolean existSDCard() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return true;
    } else {
      return false;
    }
  }
}