package com.bk.photogobuy.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.bk.photogobuy.utils.DebugLog;

import butterknife.ButterKnife;

/**
 * Created by Dell on 23-Jun-17.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        onPreSetContentView(savedInstanceState);
        super.onCreate(savedInstanceState);
        onPostSetContentView(savedInstanceState);
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            handleDeepLinkData(intent.getData());
        }
        setContentView(setContentViewId());
        ButterKnife.bind(this);
        handleIntent(intent);
        initView();
        initData();
    }

    /**
     * Handle data before setContentView call
     *
     * @param savedInstanceState
     */
    protected void onPreSetContentView(Bundle savedInstanceState) {

    }

    /**
     * Handle data after setContentView call
     *
     * @param savedInstanceState
     */
    protected void onPostSetContentView(Bundle savedInstanceState) {

    }

    /**
     * Handle deep link data
     *
     * @param uri
     */
    protected void handleDeepLinkData(Uri uri) {
        DebugLog.i("uri: " + uri.toString());
    }

    /**
     * @return layout of activity
     */
    public abstract int setContentViewId();

    /**
     * Define your view
     */
    public abstract void initView();

    /**
     * Setup your data
     */
    public abstract void initData();

    /**
     * Handle intent
     * @param intent
     */
    public void handleIntent(Intent intent) {}
}
