package cn.lixingyu.xylblog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
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
import java.util.ArrayList;
import java.util.List;

import cn.lixingyu.xylblog.Entity.Admin;
import cn.lixingyu.xylblog.Entity.Blog;
import cn.lixingyu.xylblog.Entity.Category;
import cn.lixingyu.xylblog.Utils.HttpUtils;
import cn.lixingyu.xylblog.Utils.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private final int LOGIN_SUCCESS = 0;

    private final int ADD_BLOG = 1;

    private final int MANAGER_BLOG = 2;

    private final int ADMIN = 3;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefresh;

    private RecyclerViewBlogListAdapter blogAdapter;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private List<Blog> allBlog = null;

    private List<Category> allCategory = null;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private Toolbar toolbar;

    private FloatingActionButton floatButton;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.menu);
        floatButton = findViewById(R.id.floatButton);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        swipeRefresh = findViewById(R.id.swipteRefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    refreshBlogList();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            allBlog = DataSupport.findAll(Blog.class);
            if (allBlog.size() == 0) {
                sendBlog("http://www.lixingyu.cn/xylBlog/getBlogList");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            DataSupport.deleteAll(Category.class);
            sendCategory("http://www.lixingyu.cn/xylBlog/getCategoryList");
        } catch (IOException e) {
            e.printStackTrace();
        }
        swipeRefresh.setRefreshing(true);
        try {
            refreshBlogList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        List<Admin> allAdmin = new ArrayList<>();
        try {
            allAdmin = DataSupport.findAll(Admin.class);
        } catch (Exception e) {
        }
        if (allAdmin.size() == 0) {
            navigationView.inflateMenu(R.menu.login);
            navigationView.setCheckedItem(R.id.index);
            floatButton.setVisibility(View.GONE);
        } else {
            Toast.makeText(MyApplication.getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
            navigationView.inflateMenu(R.menu.menu);
            navigationView.setCheckedItem(R.id.addBlog);
            floatButton.setVisibility(View.VISIBLE);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
                switch (menuItem.getItemId()) {
                    case R.id.index:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.login:
                        startActivityForResult(intent, LOGIN_SUCCESS);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.offline:
                        DataSupport.deleteAll(Admin.class);
                        Toast.makeText(MyApplication.getContext(), "注销成功！", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        navigationView.getMenu().clear();
                        navigationView.inflateMenu(R.menu.login);
                        navigationView.setCheckedItem(R.id.index);
                        startActivityForResult(intent, LOGIN_SUCCESS);
                        floatButton.setVisibility(View.GONE);
                        break;
                    case R.id.addBlog:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.managerBlog:
                        drawerLayout.closeDrawers();
                        Intent managerBlogIntent = new Intent(MyApplication.getContext(), ManagerBlogActivity.class);
                        startActivityForResult(managerBlogIntent, MANAGER_BLOG);
                        break;
                    case R.id.admin:
                        drawerLayout.closeDrawers();
                        Intent adminIntent = new Intent(MyApplication.getContext(), AdminActivity.class);
                        startActivityForResult(adminIntent, ADMIN);
                        break;
                }
                return true;
            }
        });
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getContext(), addBlogActivity.class);
                startActivityForResult(intent, ADD_BLOG);
            }
        });
    }

    private void refreshBlogList() throws InterruptedException {
        List<Blog> allBlog = null;
        DataSupport.deleteAll(Blog.class);
        try {
            sendBlog("http://www.lixingyu.cn/xylBlog/getBlogList");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.sleep(500);
        allBlog = DataSupport.findAll(Blog.class);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        blogAdapter = new RecyclerViewBlogListAdapter(allBlog);
        swipeRefresh.setRefreshing(false);
        recyclerView.setAdapter(blogAdapter);
    }

    public void sendBlog(String url) throws IOException {
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

    public void sendCategory(String url) throws IOException {
        HttpUtils.sendOkHttpRequestByGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Utility.saveCategory(responseText);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                navigationView.setCheckedItem(R.id.index);
                break;
        }
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case LOGIN_SUCCESS:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MyApplication.getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.menu);
                    navigationView.setCheckedItem(R.id.addBlog);
                    floatButton.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(MyApplication.getContext(), "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
                break;
            case ADD_BLOG:
                if (resultCode == RESULT_OK) {
                    swipeRefresh.setRefreshing(true);
                    try {
                        Toast.makeText(MyApplication.getContext(), "添加博客成功！", Toast.LENGTH_SHORT).show();
                        refreshBlogList();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MyApplication.getContext(), "添加博客失败！", Toast.LENGTH_SHORT).show();
                }
                break;
            case MANAGER_BLOG:
                if (resultCode == RESULT_OK) {
                    navigationView.setCheckedItem(R.id.addBlog);
                }
                break;
            case ADMIN:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MyApplication.getContext(), "修改密码成功！", Toast.LENGTH_SHORT).show();
                    DataSupport.deleteAll(Admin.class);
                    navigationView.getMenu().clear();
                    navigationView.inflateMenu(R.menu.login);
                    navigationView.setCheckedItem(R.id.index);
                    floatButton.setVisibility(View.GONE);
                }
        }
    }
}
