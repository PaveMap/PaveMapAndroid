package com.ttp.pavemap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ttp.pavemap.R;
import com.ttp.pavemap.external.facebook.FacebookAuthenticateActivity;

/**
 * Created by Thai on 10/5/2014.
 */
public class AccountSignInActivity extends FragmentActivity implements View.OnClickListener {
    private static final int REQUEST_FACEBOOK_AUTHENTICATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_sign_in);
        ((TextView) findViewById(R.id.sign_in)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in:
                startActivityForResult(new Intent(AccountSignInActivity.this, FacebookAuthenticateActivity.class), REQUEST_FACEBOOK_AUTHENTICATE);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FACEBOOK_AUTHENTICATE) {
            if (resultCode == Activity.RESULT_OK) {
//                ((SignUpKardBuilderActivity)getActivity()).getUserInfo(Session.getActiveSession());
                Toast.makeText(this, "success", Toast.LENGTH_SHORT);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
