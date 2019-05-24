package cn.lixingyu.xylblog.Entity;

import org.litepal.crud.DataSupport;

public class Category extends DataSupport {

    private String category;
    private int count;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Category(String category, int count, int id) {
        this.category = category;
        this.count = count;
        this.id = id;
    }

    public Category() {
    }
}
