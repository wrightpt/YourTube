package com.yourtube.c.yourtube;

import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;

/**
 * Created by c on 2/28/16.
 */
public class DialogFragmentPlayer extends DialogFragment {

    String mNum;
    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;

    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;
    private LinearLayout mContentView;
    FrameLayout.LayoutParams COVER_SCREEN_GRAVITY_CENTER = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

    static DialogFragmentPlayer newInstance(String num) {
        DialogFragmentPlayer f = new DialogFragmentPlayer();
        Log.d("num",num);
        Bundle args = new Bundle();
        args.putString("num", num);
        f.setArguments(args);

        return f;






}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNum = getArguments().getString("num");
        Log.d("mNum",mNum);




}

    String youtubeHTML = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/vWZ2W86JkME?rel=0\" frameborder=\"0\" allowfullscreen></iframe></body></html>";






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        String a = mNum;
        View v = (View) inflater.inflate(R.layout.player_webfragment, container, false);

        webView = (VideoEnabledWebView)v.findViewById(R.id.webView);
        ViewGroup videoLayout = (ViewGroup)v.findViewById(R.id.videoLayout);
        //WebView myWebView = (WebView) v.findViewById(R.id.mywebview);
        View nonvideoLayout = null;
        nonvideoLayout = (View)v.findViewById(R.id.nonVideoLayout);
        WebSettings settings = webView.getSettings();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);




       // webView.setWebChromeClient(new WebChromeClient());

       // webView.setWebViewClient(new WebViewClient());
       // webView.setWebViewClient(new HelloWebViewClient());
        //webView.setWebViewClient
                webChromeClient = new VideoEnabledWebChromeClient(nonvideoLayout,videoLayout ,v, webView){


                    @Override
                    public void onProgressChanged(WebView view, int progress)
                    {
                        // Your code...
                    }



           // @Override
           // public boolean shouldOverrideUrlLoading(WebView view, String url){
           //     return false;
           // }
        };

        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(new InsideWebViewClient());
      //  webView.loadUrl("http://m.youtube.com/watch?v=" + mNum);
       // myWebView.addJavascriptInterface(new MyJavaScriptInterface(this), "JSHandler");
      //  myWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        StringBuilder sb;
        sb = new StringBuilder();
        sb.append("<html><body><iframe width=\"100%\" height=\"100%\"   src=\"https://www.youtube.com/embed/");
        sb.append(mNum);


        sb.append("\" frameborder=\"0\" allowfullscreen></iframe></body></html>");


        StringBuilder sb2;
        sb2 = new StringBuilder();
        sb2.append("<html><body>Youtube video .. <br> <iframe width=\"320\" height=\"315\" src=\"https://www.youtube.com/embed/lY2H2ZP56K4\" frameborder=\"0\" allowfullscreen></iframe></body></html>");


       webView.loadDataWithBaseURL("https://www.youtube.com", sb.toString(), "text/html; charset=utf-8", "UTF-8", null);

        //myWebView.loadDataWithBaseURL("https://www.youtube.com", sb2.toString(), "text/html; charset=utf-8", "UTF-8", null);

        //webView.loadData(sb.toString(), "text/html; charset=utf-8; application/javascript ", "UTF-8");

       // WebView.loadDataWithBaseURL("https://www.youtube.com", n, "text/html; charset=utf-8", "UTF-8", null);




        return v;

}


    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }







    public class MyJavaScriptInterface{


        private DialogFragmentPlayer activity;

        public MyJavaScriptInterface(DialogFragmentPlayer activity){

            this.activity = activity;
        }}



    private class HelloWebViewClient extends WebViewClient  {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webview, String url)
        {
            webview.setWebChromeClient(new WebChromeClient() {

                private View mCustomView;

                @Override
                public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
                {
                    // if a view already exists then immediately terminate the new one
                    if (mCustomView != null)
                    {
                        callback.onCustomViewHidden();
                        return;
                    }

                    // Add the custom view to its container.
                    mCustomViewContainer.addView(view, COVER_SCREEN_GRAVITY_CENTER);
                    mCustomView = view;
                    mCustomViewCallback = callback;

                    // hide main browser view
                    mContentView.setVisibility(View.GONE);

                    // Finally show the custom view container.
                    mCustomViewContainer.setVisibility(View.VISIBLE);
                    mCustomViewContainer.bringToFront();
                }

            });

            webview.loadUrl(url);

            return true;
        }
    }







}
