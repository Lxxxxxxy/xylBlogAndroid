package cn.lixingyu.xylblog.Entity;

import org.litepal.crud.DataSupport;

/**
 * @author lxxxxxxy
 * @time 2019/5/22 19:26
 */
public class CategoryList extends DataSupport {

    private Category category;

    private Integer count;

    @Override
    public String toString() {
        return "CategoryList{" +
                "category=" + category +
                ", count=" + count +
                '}';
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public CategoryList(Category category, Integer count) {

        this.category = category;
        this.count = count;
    }

    public CategoryList() {

    }
}
