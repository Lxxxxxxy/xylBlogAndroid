package cn.lixingyu.xylblog;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import cn.lixingyu.xylblog.Entity.Blog;
import cn.lixingyu.xylblog.Utils.HttpUtils;
import cn.lixingyu.xylblog.Utils.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ManagerBlogActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefresh;

    private RecyclerViewManagerBlogAdapter ManagerBlogAdapter;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private List<Blog> allBlog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_blog);
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Manager Blog");
        recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        swipeRefresh = findViewById(R.id.swipteRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    refreshManagerBlogList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            allBlog = DataSupport.findAll(Blog.class);
            if (allBlog.size() == 0) {
                getManagerBlogList("http://www.lixingyu.cn/xylBlog/getBlogList");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        swipeRefresh.setRefreshing(true);
        try {
            refreshManagerBlogList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void refreshManagerBlogList() throws InterruptedException {
        DataSupport.deleteAll(Blog.class);
        try {
            getManagerBlogList("http://www.lixingyu.cn/xylBlog/getBlogList");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.sleep(500);
        allBlog = DataSupport.findAll(Blog.class);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ManagerBlogAdapter = new RecyclerViewManagerBlogAdapter(allBlog);
        swipeRefresh.setRefreshing(false);
        recyclerView.setAdapter(ManagerBlogAdapter);
    }

    private void getManagerBlogList(String url) throws IOException {
        HttpUtils.sendOkHttpRequestByGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.saveBlog(responseText);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            default:
                break;
        }
        return true;
    }
}
