package com.yourtube.c.yourtube;

import android.os.*;
import android.support.v4.app.*;
import android.util.*;
import android.view.*;
import android.webkit.*;

/**
 * Created by c on 2/28/16.
 */
public class DialogFragmentPlayer extends DialogFragment {

    String mNum;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        String a = mNum;
        View v = inflater.inflate(R.layout.player_webfragment, container, false);
        WebView myWebView = (WebView) v.findViewById(R.id.mywebview);


        return v;

}





}
