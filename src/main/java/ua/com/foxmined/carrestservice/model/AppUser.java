package ua.com.foxmined.carrestservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "encryted_password")
    private String encrytedPassword;

    @Column(name = "enabled")
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return enabled == appUser.enabled
                && Objects.equals(userId, appUser.userId)
                && Objects.equals(userName, appUser.userName)
                && Objects.equals(encrytedPassword, appUser.encrytedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, encrytedPassword, enabled);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", encrytedPassword='" + encrytedPassword + '\'' +
                ", enabled=" + enabled +
                '}';
    }

}


