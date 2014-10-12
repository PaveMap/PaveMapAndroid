package com.ttp.pavemap.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ttp.pavemap.PaveMap;
import com.ttp.pavemap.R;
import com.ttp.pavemap.external.facebook.FacebookAuthenticateActivity;

/**
 * Created by Thai on 10/5/2014.
 * Log In Screen
 */
public class AccountSignInActivity extends Activity implements View.OnClickListener {
    private static final int REQUEST_FACEBOOK_AUTHENTICATE = 1;

    private EditText mEmail, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sign_in);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        findViewById(R.id.root).setOnClickListener(this);
        findViewById(R.id.log_in).setOnClickListener(this);
        findViewById(R.id.forgot).setOnClickListener(this);
        findViewById(R.id.sign_up).setOnClickListener(this);
        findViewById(R.id.facebook).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root: // hide soft keyboard
                if (getCurrentFocus() != null)
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.log_in:
                if(mEmail.length() == 0 || mPassword.length() == 0)
                    PaveMap.instance.showToast("please fill email and password");
                else{

                }
                break;
            case R.id.forgot:
                //TODO
                PaveMap.instance.showToast("not implement");
                break;
            case R.id.sign_up:
                //TODO
                PaveMap.instance.showToast("not implement");
                break;
            case R.id.facebook:
                startActivityForResult(new Intent(AccountSignInActivity.this, FacebookAuthenticateActivity.class), REQUEST_FACEBOOK_AUTHENTICATE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FACEBOOK_AUTHENTICATE) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(RESULT_OK);
                PaveMap.showToast("success");
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
