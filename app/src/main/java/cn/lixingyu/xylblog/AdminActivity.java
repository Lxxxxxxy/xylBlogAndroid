package cn.lixingyu.xylblog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import cn.lixingyu.xylblog.Entity.Admin;
import cn.lixingyu.xylblog.Utils.HttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AdminActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Admin admin = null;

    private EditText user;

    private EditText password;

    private Button editAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Admin");
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        editAdmin = findViewById(R.id.editAdmin);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        try {
            admin = DataSupport.findFirst(Admin.class);
        } catch (Exception e) {
        }
        if (admin != null) {
            user.setText(admin.getUser());
        } else {
            Toast.makeText(MyApplication.getContext(), "登录失败！请重新登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
            startActivity(intent);
        }
        editAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString();
                String newPassword = password.getText().toString();
                try {
                    editAdmin("http://www.lixingyu.cn/xylBlog/admin/editAdmin", "admin", "{\"user\":\"" + username + "\",\"password\":\"" + newPassword + "\"}");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void editAdmin(String url, String requestName, String requestBody) throws IOException {
        HttpUtils.sendOkHttpRequestByPost(url, requestName, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().string());
                    String success = jsonObject.getString("success");
                    if (success.equals("true")) {
                        Intent intent = new Intent();
                        intent.putExtra("editAdminInfo", success);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
