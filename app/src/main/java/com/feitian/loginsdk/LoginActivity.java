package com.feitian.loginsdk;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.feitian.login.CnHttpUtils;
import com.feitian.login.CnUtils;
import com.feitian.login.FTLogin;

import butterknife.BindView;


public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_progress)
    ProgressBar mProgressView;
    @BindView(R.id.account)
    AutoCompleteTextView mphoneNumsView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.account_sign_in_button)
    Button mphoneNumsSignInButton;
    @BindView(R.id.account_login_form)
    LinearLayout accountLoginForm;
    @BindView(R.id.login_form)
    ScrollView mLoginFormView;


    @Override
    protected void initView() {
        CnUtils.init(true);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mphoneNumsSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected int provieResourceID() {
        return R.layout.activity_login;
    }


    private void attemptLogin() {

        // Reset errors.
        mphoneNumsView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String phoneNums = mphoneNumsView.getText().toString();
        String password = mPasswordView.getText().toString();


        new FTLogin().onLogin(phoneNums, password, new CnHttpUtils.CallBacks() {
            @Override
            public void onSuccess(Object o) {
            }

            @Override
            public void onFailure(Exception e) {
//                CnLogUtils.d("onFailure",e.toString());
            }
        });
    }
}


