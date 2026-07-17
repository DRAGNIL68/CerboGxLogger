package net.outmoded.cerbogxlogger.yml;

import org.jetbrains.annotations.NotNull;

// for yml per device
public class LoginData {
    private String userId;
    private String password;
    private String url;

    public void setUrl(@NotNull("url was null") String url) {
        this.url = url;
    }

    public void setUserId(@NotNull("userId was null") String userId) {
        this.userId = userId;
    }

    public void setPassword(@NotNull("password was null") String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }
}
