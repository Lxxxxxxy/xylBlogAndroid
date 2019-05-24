package cn.lixingyu.xylblog.Entity;


import org.litepal.crud.DataSupport;

/**
 * @author lxxxxxxy
 * @time 2019/5/20 08:17
 */
public class Admin extends DataSupport {
    private String user;
    private String password;

    @Override
    public String toString() {
        return "Admin{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Admin(String user, String password) {

        this.user = user;
        this.password = password;
    }

    public Admin() {

    }
}
