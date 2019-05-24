package cn.lixingyu.xylblog;

import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.lixingyu.xylblog.Entity.Blog;
import cn.lixingyu.xylblog.Utils.HttpUtils;
import cn.lixingyu.xylblog.Utils.Utility;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author lxxxxxxy
 * @time 2019/5/20 15:04
 */
public class RecyclerViewManagerBlogAdapter extends RecyclerView.Adapter<RecyclerViewManagerBlogAdapter.ViewHolder> {

    private List<Blog> blogList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView time;
        View blogView;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
            blogView = view;
        }

    }

    public RecyclerViewManagerBlogAdapter(List<Blog> list) {
        this.blogList = list;
    }

    @NonNull
    @Override
    public RecyclerViewManagerBlogAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.maneger_blog_items, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.blogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Blog blog = blogList.get(adapterPosition);
                try {
                    deleteBlog("http://www.lixingyu.cn/xylBlog/admin/deleteBlog?id=" + blog.getTag_id());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewManagerBlogAdapter.ViewHolder viewHolder, int i) {
        Blog blog = blogList.get(i);
        viewHolder.title.setText("标题：" + blog.getTitle());
        viewHolder.time.setText("发表时间：" + blog.getTime());
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public void deleteBlog(String url) throws IOException {
        HttpUtils.sendOkHttpRequestByGet(url, new Callback() {
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
                        Looper.prepare();
                        Toast.makeText(MyApplication.getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
