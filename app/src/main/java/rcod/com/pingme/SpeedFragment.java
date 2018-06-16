package rcod.com.pingme;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;



public class SpeedFragment extends Fragment {
    WebView speedtest;
    ProgressBar spinner;

    public SpeedFragment() {
        // Required empty public constructor
    }

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_speed, container, false);

        spinner = v.findViewById(R.id.progressBar1);

        speedtest = v.findViewById(R.id.webview);
        speedtest.setWebViewClient(new CustomWebViewClient());

        //Enable JavaScript
        speedtest.getSettings().setJavaScriptEnabled(true);
        speedtest.setFocusable(true);
        speedtest.setFocusableInTouchMode(true);

        //Set Render Priority to High
        speedtest.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        speedtest.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        speedtest.getSettings().setDomStorageEnabled(true);
        speedtest.getSettings().setDatabaseEnabled(true);
        speedtest.getSettings().setAppCacheEnabled(true);
        speedtest.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //speedtest.setScrollY(130);
        speedtest.setVerticalScrollBarEnabled(true);
        speedtest.setInitialScale(145);

//        speedtest.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return (event.getAction() == MotionEvent.ACTION_MOVE);
//            }
//        });

        //Load URL
        if (savedInstanceState != null)
            speedtest.restoreState(savedInstanceState);
        else
            speedtest.loadUrl("http://pingme.speedtestcustom.com");
        //speedtest.setWebViewClient(new WebViewClient());

        return v;
    }
    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {

            // only make it invisible the FIRST time the app is run
            if (MainActivity.ShowOrHideWebViewInitialUse.equals("show")) {
                webview.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView webview, String url) {

            MainActivity.ShowOrHideWebViewInitialUse = "hide";
            spinner.setVisibility(View.GONE);

            webview.setVisibility(View.VISIBLE);
            super.onPageFinished(webview, url);

        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        speedtest.saveState(outState);
    }
}
