package cn.lixingyu.xylblog;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import cn.lixingyu.xylblog.Entity.Admin;
import cn.lixingyu.xylblog.Utils.HttpUtils;
import cn.lixingyu.xylblog.Utils.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText username;

    private EditText password;

    private Button login;

    private String usernameText;

    private String passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("login");
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameText = username.getText().toString();
                passwordText = password.getText().toString();
                try {
                    send("http://www.lixingyu.cn/xylBlog/xylBlog/login", "userCheck", "{user:\"" + usernameText + "\",password:\"" + passwordText + "\"}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void send(String url, String requestName, String requestBody) throws IOException {
        HttpUtils.sendOkHttpRequestByPost(url, requestName, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(), "登录失败！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean isLogin = Utility.saveAdmin(responseText);
                if (isLogin) {
                    Admin admin = new Admin();
                    admin.setUser(usernameText);
                    admin.setPassword(passwordText);
                    admin.save();
                    Intent intent = new Intent();
                    intent.putExtra("loginInfo", isLogin);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }
}
