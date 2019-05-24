package cn.lixingyu.xylblog.Entity;

import org.litepal.crud.DataSupport;

/**
 * @author lxxxxxxy
 * @time 2019/5/22 10:05
 */
public class AdminList extends DataSupport {

    private String success;

    @Override
    public String toString() {
        return "AdminList{" +
                "success='" + success + '\'' +
                '}';
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public AdminList(String success) {

        this.success = success;
    }

    public AdminList() {

    }
}
