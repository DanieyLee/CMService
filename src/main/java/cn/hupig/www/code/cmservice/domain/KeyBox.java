package cn.hupig.www.code.cmservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A KeyBox.
 */
@Entity
@Table(name = "key_box")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KeyBox implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_account")
    private String userAccount;

    @Column(name = "password")
    private String password;

    @Column(name = "second_password")
    private String secondPassword;

    @Column(name = "login_address")
    private String loginAddress;

    @Column(name = "jhi_explain")
    private String explain;

    @Column(name = "display")
    private Boolean display;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "creat_time")
    private Instant creatTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Instant updateTime;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = "keyBoxes", allowSetters = true)
    private UserLink userLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public KeyBox userAccount(String userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public KeyBox password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public KeyBox secondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
        return this;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public KeyBox loginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
        return this;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getExplain() {
        return explain;
    }

    public KeyBox explain(String explain) {
        this.explain = explain;
        return this;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Boolean isDisplay() {
        return display;
    }

    public KeyBox display(Boolean display) {
        this.display = display;
        return this;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public String getCreateUser() {
        return createUser;
    }

    public KeyBox createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreatTime() {
        return creatTime;
    }

    public KeyBox creatTime(Instant creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public KeyBox updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public KeyBox updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public KeyBox note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public UserLink getUserLink() {
        return userLink;
    }

    public KeyBox userLink(UserLink userLink) {
        this.userLink = userLink;
        return this;
    }

    public void setUserLink(UserLink userLink) {
        this.userLink = userLink;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyBox)) {
            return false;
        }
        return id != null && id.equals(((KeyBox) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyBox{" +
            "id=" + getId() +
            ", userAccount='" + getUserAccount() + "'" +
            ", password='" + getPassword() + "'" +
            ", secondPassword='" + getSecondPassword() + "'" +
            ", loginAddress='" + getLoginAddress() + "'" +
            ", explain='" + getExplain() + "'" +
            ", display='" + isDisplay() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", creatTime='" + getCreatTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
