package cn.lixingyu.xylblog.Entity;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * @author lxxxxxxy
 * @time 2019/5/20 08:47
 */
public class BlogList extends DataSupport {
    private String success;
    private List<Blog> blogList;

    public BlogList() {
    }

    @Override
    public String toString() {
        return "BlogList{" +
                "success='" + success + '\'' +
                ", blogList=" + blogList +
                '}';
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public BlogList(String success, List<Blog> blogList) {

        this.success = success;
        this.blogList = blogList;
    }
}
