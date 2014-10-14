package com.ttp.pavemap.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ttp.pavemap.fragment.AccountSignInFragment;

/**
 * Created by Thai on 10/5/2014.
 * Log In Screen
 */
public class AccountActivity extends FragmentActivity {
    private AccountSignInFragment mSignInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mSignInFragment = new AccountSignInFragment();
        } else {
            // Or set the fragment from restored state info
            mSignInFragment = (AccountSignInFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
        }
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, mSignInFragment).commit();
    }
}
