package org.westos.domain;

import java.io.Serializable;

/**
 * @Author: ShenMouMou
 * @CreateTime: 2019-12-20 09:57
 * @Company:西部开源教育科技有限公司
 * @Description:爱生活，爱Java!
 */
public class Users implements Serializable {

    private static final long serialVersionUID = -4942574759995067844L;
    private String username;
    private String password;

    public Users() {
    }

    public Users(String username, String password) {
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
