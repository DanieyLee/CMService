package cn.hupig.www.code.cmservice.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UserLink.
 */
@Entity
@Table(name = "user_link")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserLink implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "sex")
    private Boolean sex;

    @Column(name = "age")
    private Long age;

    @Column(name = "theme")
    private String theme;

    @Min(value = 6L)
    @Column(name = "password_key")
    private Long passwordKey;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserLink firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean isSex() {
        return sex;
    }

    public UserLink sex(Boolean sex) {
        this.sex = sex;
        return this;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Long getAge() {
        return age;
    }

    public UserLink age(Long age) {
        this.age = age;
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getTheme() {
        return theme;
    }

    public UserLink theme(String theme) {
        this.theme = theme;
        return this;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Long getPasswordKey() {
        return passwordKey;
    }

    public UserLink passwordKey(Long passwordKey) {
        this.passwordKey = passwordKey;
        return this;
    }

    public void setPasswordKey(Long passwordKey) {
        this.passwordKey = passwordKey;
    }

    public User getUser() {
        return user;
    }

    public UserLink user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserLink)) {
            return false;
        }
        return id != null && id.equals(((UserLink) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserLink{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", sex='" + isSex() + "'" +
            ", age=" + getAge() +
            ", theme='" + getTheme() + "'" +
            ", passwordKey=" + getPasswordKey() +
            "}";
    }
}
