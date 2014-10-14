package com.ttp.pavemap.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;
import com.ttp.pavemap.PaveMap;
import com.ttp.pavemap.R;

/**
 * Created by Thai on 10/13/2014.
 */
public class AccountSignInFragment extends Fragment implements View.OnClickListener {
    LoginButton mFacebook;
    private EditText mEmail, mPassword;

    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_sign_in, container, false);
        mEmail = (EditText) view.findViewById(R.id.email);
        mPassword = (EditText) view.findViewById(R.id.password);

        view.findViewById(R.id.root).setOnClickListener(this);
        view.findViewById(R.id.log_in).setOnClickListener(this);
        view.findViewById(R.id.forgot).setOnClickListener(this);
        view.findViewById(R.id.sign_up).setOnClickListener(this);
        mFacebook = (LoginButton) view.findViewById(R.id.facebook);
        mFacebook.setFragment(this);
        //mFacebook.setReadPermissions(Arrays.asList("user_likes", "user_status"));

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            // TODO log in
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        } else if (state.isClosed()) {
            // TODO log out
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root: // hide soft keyboard
                if (getActivity().getCurrentFocus() != null)
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.log_in:
                if (mEmail.length() == 0 || mPassword.length() == 0)
                    PaveMap.instance.showToast("please fill email and password");
                else {

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
        }
    }
}
