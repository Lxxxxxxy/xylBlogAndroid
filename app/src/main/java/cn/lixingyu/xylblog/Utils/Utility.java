package cn.lixingyu.xylblog.Utils;

import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.lixingyu.xylblog.Entity.Blog;
import cn.lixingyu.xylblog.Entity.BlogList;
import cn.lixingyu.xylblog.Entity.Category;
import cn.lixingyu.xylblog.MainActivity;
import cn.lixingyu.xylblog.MyApplication;

/**
 * @author lxxxxxxy
 * @time 2019/5/20 08:24
 */
public class Utility {

    public static boolean saveBlog(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
                String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
                String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

                Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
                Matcher m_script = p_script.matcher(response);
                response = m_script.replaceAll(""); //过滤script标签

                Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
                Matcher m_style = p_style.matcher(response);
                response = m_style.replaceAll(""); //过滤style标签

                Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
                Matcher m_html = p_html.matcher(response);
                response = m_html.replaceAll("");
                response = response.replace("&nbsp;", " ");

                JSONObject jsonObject = new JSONObject(response);
                String blogList1 = jsonObject.getString("blogList");
                JSONArray jsonArray = new JSONArray(blogList1);
                BlogList blogList = new BlogList();
                List<Blog> blogList2 = blogList.getBlogList();
                blogList2 = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); ++i) {
                    JSONObject blogContent = jsonArray.getJSONObject(i);
                    Blog blog = new Blog();
                    blog.setBody(blogContent.getString("body"));
                    blog.setTime(blogContent.getString("time"));
                    blog.setTitle(blogContent.getString("title"));
                    if (blogContent.getString("mm") != null) {
                        blog.setMm(blogContent.getString("mm"));
                        if (blogContent.getString("yzmm") != null) {
                            blog.setYzmm(blogContent.getString("yzmm"));
                        }
                    }
                    blog.setCategory(blogContent.getString("category"));
                    blog.setTag_id(String.valueOf(blogContent.getInt("id")));
                    blogList2.add(blog);
                }
                for (int i = 0; i < blogList2.size(); ++i) {
                    blogList2.get(i).save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean saveAdmin(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                if (success.equals("true")) {
                    return true;
                } else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void saveCategory(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String success = jsonObject.getString("success");
                if (success.equals("true")) {
                    String categoryJson = jsonObject.getString("categoryList");
                    JSONArray jsonArray = new JSONArray(categoryJson);
                    List<Category> categoryList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        JSONObject categoryJsonObject = jsonArray.getJSONObject(i);
                        Category category = new Category();
                        category.setCategory(categoryJsonObject.getString("category"));
                        category.setCount(Integer.parseInt(categoryJsonObject.getString("count")));
                        categoryList.add(category);
                    }
                    for (int i = 0; i < categoryList.size(); ++i) {
                        categoryList.get(i).save();
                    }
                } else {
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
