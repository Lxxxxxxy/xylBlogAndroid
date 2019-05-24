package cn.lixingyu.xylblog;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.lixingyu.xylblog.Entity.Blog;

/**
 * @author lxxxxxxy
 * @time 2019/5/20 15:04
 */
public class RecyclerViewBlogListAdapter extends RecyclerView.Adapter<RecyclerViewBlogListAdapter.ViewHolder> {

    private List<Blog> blogList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView body;
        View blogView;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            body = view.findViewById(R.id.body);
            blogView = view;
        }

    }

    public RecyclerViewBlogListAdapter(List<Blog> list) {
        this.blogList = list;
    }

    @NonNull
    @Override
    public RecyclerViewBlogListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.blog_items, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.blogView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAdapterPosition();
                Blog blog = blogList.get(adapterPosition);
                Intent intent = new Intent(MyApplication.getContext(), BlogActivity.class);
                intent.putExtra("blogTitle", blog.getTitle());
                intent.putExtra("blogBody", blog.getBody());
                intent.putExtra("blogTime", blog.getTime());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MyApplication.getContext().startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBlogListAdapter.ViewHolder viewHolder, int i) {
        Blog blog = blogList.get(i);
        viewHolder.title.setText(blog.getTitle());
        if (blog.getBody().length() >= 40) {
            viewHolder.body.setText(blog.getBody().substring(0, 40) + "....");
        } else {
            viewHolder.body.setText(blog.getBody());
        }
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
