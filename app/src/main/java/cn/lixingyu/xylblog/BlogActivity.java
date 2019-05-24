package cn.lixingyu.xylblog;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class BlogActivity extends AppCompatActivity {

    private TextView title;

    private TextView body;

    private TextView time;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        time = findViewById(R.id.time);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("blogTitle"));
        body.setText(intent.getStringExtra("blogBody"));
        time.setText(intent.getStringExtra("blogTime"));
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("blogContent");
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
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
}
