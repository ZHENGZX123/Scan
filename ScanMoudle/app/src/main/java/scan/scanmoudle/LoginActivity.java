package scan.scanmoudle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback {
    private App app;
    private EditText userName;
    private EditText passWord;
    private String LoginUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        app = (App) getApplicationContext();
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void initView() throws Exception {
        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.password);
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
    }

    void login() {
        hideKeyboard();
        Request request = new Request.Builder()
                .url(LoginUrl)
                .build();
        Call call = app.okHttpClient.newCall(request);
        call.enqueue(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                startActivity(new Intent(LoginActivity.this, ScanActivity.class));
                break;
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (call.request().url().toString().equals(LoginUrl)) {
        }
    }

    /**
     * 隐藏软键盘
     */
    void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

