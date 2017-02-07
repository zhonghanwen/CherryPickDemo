package cmcm.cherry.pick;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.lang.reflect.InvocationTargetException;

import cmcm.cherry.pick.utils.WeakHandler;

/**
 * Created by zhonghanwen on 2017/2/7.
 */

public class AwesomeWebViewActivity extends AppCompatActivity {

    private static final String TAG = AwesomeWebViewActivity.class.getSimpleName();

    private WebView mWebView;
    private ProgressBar mLoadingProgress;
    private WebViewDownloadListener mDownloadListener;

    public static final String EXTRA_DATA_URL = "extra_url";
    public static final String EXTRA_DATA_TITLE = "extra_title";
    public static final String EXTRA_LAUNCH_TYPE = "enum_type";

    public static final int ENUM_TYPE_PUSH = 1;

    private String mUrl;
    private String mTitle;
    private int mLaunchType = ENUM_TYPE_PUSH;
    private WeakHandler mWeakHandler = new WeakHandler();


    public static void launch(Activity activity, String title, String url) {
        Intent intent = new Intent(activity, AwesomeWebViewActivity.class);
        intent.putExtra(EXTRA_DATA_TITLE, title);
        intent.putExtra(EXTRA_DATA_URL, url);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awesome_webview);
        initExtraData();
        initView();
        loadUrl(mUrl);
    }


    private void initExtraData() {
        mTitle = getIntent().getStringExtra(EXTRA_DATA_TITLE);
        mUrl = getIntent().getStringExtra(EXTRA_DATA_URL);
        mLaunchType = getIntent().getIntExtra(EXTRA_LAUNCH_TYPE, ENUM_TYPE_PUSH);
    }

    private void initView() {
        mLoadingProgress = (ProgressBar) findViewById(R.id.loading_progress_bar);
        mWebView = (WebView) findViewById(R.id.wv_tutorial_webview);
        mDownloadListener = new WebViewDownloadListener(this);
        mWebView.setDownloadListener(mDownloadListener);
        WebSettings webSettings = mWebView.getSettings(); // webView: 类WebView的实例
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);           //将图片调整到适合webview的大小
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            //避免api23中onReceivedError方法deprecation
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
//                super.onReceivedSslError(view, handler, error);
                handleSslError(handler);
            }


        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mLoadingProgress != null) {
                    mLoadingProgress.setVisibility(View.VISIBLE);
                    mLoadingProgress.setProgress(newProgress);
                    if (newProgress == 100) {
                        mLoadingProgress.setVisibility(View.GONE);
                    }
                }
            }
        });

        mLoadingProgress.setVisibility(View.VISIBLE);
    }


    private void handleSslError(final SslErrorHandler handler) {

    }


    private void doExit(boolean finish) {
        if (!finish && mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        finish();

    }


    @Override
    public void finish() {
        super.finish();
    }


    private void loadUrl(final String url) {
        mWeakHandler.post(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(url)) {
                    return;
                }
                try {
                    mWebView.loadUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mWebView != null) {
                Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(mWebView, (Object[]) null);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mWebView != null) {
                Class.forName("android.webkit.WebView").getMethod("onResume", (Class[]) null).invoke(mWebView, (Object[]) null);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 可能可以避免内存泄露
        mWebView.destroy();
        mWebView = null;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doExit(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
