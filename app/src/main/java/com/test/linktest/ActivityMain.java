package com.test.linktest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.test.linktest.util.KakaoLinkUtil;
import com.test.linktest.util.KeyUtil;
import com.test.linktest.util.repository.YDataStoreRepository;
import com.test.linktest.util.repository.YSharedRepository;

public class ActivityMain extends AppCompatActivity {
    private final String TAG = "###" + getClass().getSimpleName();

    private Context mContext;
    private RelativeLayout relativeLayoutDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initView();
        setEvent();

        testDB();
    }

    private void init() {

        mContext = this;

        // key hash
        KeyUtil.getHashKey(mContext);

        // kakao init
        KakaoLinkUtil.Companion.init(mContext);
    }


    private void initView() {
        relativeLayoutDefault = (RelativeLayout) findViewById(R.id.relativeLayoutDefault);
    }

    private void setEvent(){

        relativeLayoutDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KakaoLinkUtil util = new KakaoLinkUtil();
//                util.sendMessage(mContext, "100,000원이 입금되었습니다.");
                util.test(mContext);
            }
        });
    }

    private void testDB(){

        // sharedpreferences
//        String TEST = YSharedRepository.Companion.getString(this, "TEST");
//        Log.d(TAG, "TEST : " + TEST);
//        YSharedRepository.Companion.setString(this, "TEST", "Hi2");
//        TEST = YSharedRepository.Companion.getString(this, "TEST");
//        Log.d(TAG, "TEST (after) : " + TEST);

        // datastore

    }
}
