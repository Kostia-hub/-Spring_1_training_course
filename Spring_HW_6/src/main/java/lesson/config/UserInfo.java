package lesson.config;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import lesson.domain.Role;
import lesson.domain.User;

import java.util.Objects;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserInfo {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean hasRole(Role role){
        if(user == null){
            throw new RuntimeException("User is not defined");
        }
        return Objects.equals(role, user.getRole());
    }
}
