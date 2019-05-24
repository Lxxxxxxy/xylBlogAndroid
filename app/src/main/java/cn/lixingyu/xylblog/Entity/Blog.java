package cn.lixingyu.xylblog.Entity;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class Blog extends DataSupport {

	@Column(unique = true)
	private int id;
	private String tag_id;
	private String title;
	private String body;
	private String time;
	private String category;
	private String mm;
	private String yzmm;

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", tag_id='" + tag_id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", time='" + time + '\'' +
                ", category='" + category + '\'' +
                ", mm='" + mm + '\'' +
                ", yzmm='" + yzmm + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    public String getYzmm() {
        return yzmm;
    }

    public void setYzmm(String yzmm) {
        this.yzmm = yzmm;
    }

    public Blog(int id, String tag_id, String title, String body, String time, String category, String mm, String yzmm) {

        this.id = id;
        this.tag_id = tag_id;
        this.title = title;
        this.body = body;
        this.time = time;
        this.category = category;
        this.mm = mm;
        this.yzmm = yzmm;
    }

    public Blog() {

    }
}
