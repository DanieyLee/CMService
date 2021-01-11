package cn.hupig.www.code.cmservice.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link cn.hupig.www.code.cmservice.domain.UserLink} entity.
 */
public class UserLinkDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String firstName;

    private Boolean sex;

    private Long age;

    private String theme;

    @Min(value = 6L)
    private Long passwordKey;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean isSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Long getPasswordKey() {
        return passwordKey;
    }

    public void setPasswordKey(Long passwordKey) {
        this.passwordKey = passwordKey;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserLinkDTO)) {
            return false;
        }

        return id != null && id.equals(((UserLinkDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserLinkDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", sex='" + isSex() + "'" +
            ", age=" + getAge() +
            ", theme='" + getTheme() + "'" +
            ", passwordKey=" + getPasswordKey() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
