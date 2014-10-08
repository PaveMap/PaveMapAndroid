package com.ttp.pavemap.external.facebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.ttp.pavemap.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thai on 10/5/2014.
 */
public class FacebookAuthenticateActivity extends Activity {

    @SuppressWarnings("serial")
    private static final List<String> PERMISSIONS = new ArrayList<String>() {
        {
            add("user_birthday");
            add("email");
            add("user_photos");
            add("user_friends");
        }
    };

    @SuppressWarnings("serial")
    private static final List<String> PUBLIC_PERMISSIONS = new ArrayList<String>() {
        {
            add("publish_actions");
        }
    };

    private UiLifecycleHelper lifecycleHelper;

    int numRequestPublish, numRequestRead;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_authenticate);

        lifecycleHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChanged(session, state, exception);
            }
        });
        lifecycleHelper.onCreate(savedInstanceState);
        final Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            // Not has read permission
            if (!sessionHasNecessaryPerms(session)) {
                if (numRequestRead < 1) {
                    session.requestNewReadPermissions(new Session.NewPermissionsRequest(FacebookAuthenticateActivity.this, getMissingPermissions(session)));
                    numRequestRead++;
                } else {
                    logoutFB();
                    finish();
                }
            }
            // has only read permission
            else if (!sessionHasNecessaryPublishPerms(session)) {
                if (numRequestPublish < 1) {
                    session.requestNewPublishPermissions(new Session.NewPermissionsRequest(FacebookAuthenticateActivity.this, getMissingPublishPermissions(session)));
                    numRequestPublish++;
                }
            }
            // Has full permission
            else {
                setResult(Activity.RESULT_OK);
                finish();
            }
        } else {
            ensureOpenSession();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        lifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    private boolean ensureOpenSession() {
        if (Session.getActiveSession() == null || !Session.getActiveSession().isOpened()) {
            Session.openActiveSession(this, true, new Session.StatusCallback() {
                @Override
                public void call(Session session, SessionState state, Exception exception) {
                    onSessionStateChanged(session, state, exception);
                }
            });
            return false;
        }
        return true;
    }

    private boolean sessionHasNecessaryPerms(Session session) {
        if (session != null && session.getPermissions() != null) {
            for (String requestedPerm : PERMISSIONS) {
                if (!session.getPermissions().contains(requestedPerm)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean sessionHasNecessaryPublishPerms(Session session) {
        if (session != null && session.getPermissions() != null) {
            for (String requestedPerm : PUBLIC_PERMISSIONS) {
                if (!session.getPermissions().contains(requestedPerm)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private List<String> getMissingPermissions(Session session) {
        List<String> missingPerms = new ArrayList<String>(PERMISSIONS);
        if (session != null && session.getPermissions() != null) {
            for (String requestedPerm : PERMISSIONS) {
                if (session.getPermissions().contains(requestedPerm)) {
                    missingPerms.remove(requestedPerm);
                }
            }
        }
        return missingPerms;
    }

    private List<String> getMissingPublishPermissions(Session session) {
        List<String> missingPerms = new ArrayList<String>(PUBLIC_PERMISSIONS);
        if (session != null && session.getPermissions() != null) {
            for (String requestedPerm : PUBLIC_PERMISSIONS) {
                if (session.getPermissions().contains(requestedPerm)) {
                    missingPerms.remove(requestedPerm);
                }
            }
        }
        return missingPerms;
    }

    private void onSessionStateChanged(final Session session, SessionState state, Exception exception) {
        if (state.isOpened() && !sessionHasNecessaryPerms(session)) {
            if (numRequestRead < 1) {
                session.requestNewReadPermissions(new Session.NewPermissionsRequest(FacebookAuthenticateActivity.this, getMissingPermissions(session)));
                numRequestRead++;
            } else {
                logoutFB();
                finish();
            }
        } else if (state.isOpened() && !sessionHasNecessaryPublishPerms(session)) {
            if (numRequestPublish < 1) {
                session.requestNewPublishPermissions(new Session.NewPermissionsRequest(FacebookAuthenticateActivity.this, getMissingPublishPermissions(session)));
                numRequestPublish++;
            } else {
                logoutFB();
                finish();
            }
        } else if (state.isOpened() && sessionHasNecessaryPublishPerms(session)) {
            setResult(Activity.RESULT_OK);
            finish();
        } else if (state == SessionState.CLOSED_LOGIN_FAILED || state == SessionState.CLOSED) {
            finish();
        }
    }

    public void logoutFB() {
        Session session = Session.getActiveSession();
        if (session != null) {
            if (!session.isClosed()) {
                session.closeAndClearTokenInformation();
            }
        }
    }
}