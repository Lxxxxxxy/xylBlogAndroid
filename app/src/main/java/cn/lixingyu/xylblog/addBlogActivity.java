package cn.lixingyu.xylblog;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import cn.lixingyu.xylblog.Entity.Admin;
import cn.lixingyu.xylblog.Entity.Category;
import cn.lixingyu.xylblog.Utils.HttpUtils;
import cn.lixingyu.xylblog.Utils.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class addBlogActivity extends AppCompatActivity {

    private Spinner spinner;

    private CheckBox isPassword;

    private Toolbar toolbar;

    private EditText yzmm;

    private LinearLayout password;

    private Button addBlog;

    private EditText title;

    private EditText blogContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Add Blog");
        isPassword = findViewById(R.id.isPassword);
        password = findViewById(R.id.password);
        yzmm = findViewById(R.id.yzmm);
        addBlog = findViewById(R.id.addBlog);
        title = findViewById(R.id.title);
        blogContent = findViewById(R.id.blogContent);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        spinner = findViewById(R.id.category);
        try {
            List<Category> allCategory = DataSupport.findAll(Category.class);
            String s[] = new String[allCategory.size()];
            for (int i = 0; i < allCategory.size(); ++i) {
                s[i] = allCategory.get(i).getCategory();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, android.R.id.text1, s);
            spinner.setAdapter(adapter);
        } catch (Exception e) {
        }
        isPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPassword.isChecked()) {
                    password.setVisibility(View.VISIBLE);
                } else {
                    password.setVisibility(View.GONE);
                }
            }
        });
        addBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = (String) spinner.getSelectedItem();
                String titleText = title.getText().toString();
                String body = blogContent.getText().toString();
                String requestName = "blog";
                String requestBody = "{\"title\":\"" + titleText + "\",\"body\":\"" + body + "\",\"category\":\"" + category + "\"";
                try {
                    if (isPassword.isChecked()) {
                        requestBody += ",\"yzmm\":\"" + yzmm.getText().toString() + "\"}";
                    } else {
                        requestBody += "}";
                    }
                    addBlog("http://www.lixingyu.cn/xylBlog/admin/addBlog", requestName, requestBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addBlog(String url, String requestName, String requestBody) throws IOException {
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
                        intent.putExtra("addBlogInfo", success);
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
