package cn.hupig.www.code.cmservice.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link cn.hupig.www.code.cmservice.domain.KeyBox} entity.
 */
public class KeyBoxDTO implements Serializable {
    
    private Long id;

    private String userAccount;

    private String password;

    private String secondPassword;

    private String loginAddress;

    private String explain;

    private Boolean display;

    private String createUser;

    private Instant creatTime;

    private String updateUser;

    private Instant updateTime;

    private String note;


    private Long userLinkId;

    private String userLinkFirstName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public String getLoginAddress() {
        return loginAddress;
    }

    public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Boolean isDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getUserLinkId() {
        return userLinkId;
    }

    public void setUserLinkId(Long userLinkId) {
        this.userLinkId = userLinkId;
    }

    public String getUserLinkFirstName() {
        return userLinkFirstName;
    }

    public void setUserLinkFirstName(String userLinkFirstName) {
        this.userLinkFirstName = userLinkFirstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyBoxDTO)) {
            return false;
        }

        return id != null && id.equals(((KeyBoxDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyBoxDTO{" +
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
            ", userLinkId=" + getUserLinkId() +
            ", userLinkFirstName='" + getUserLinkFirstName() + "'" +
            "}";
    }
}
