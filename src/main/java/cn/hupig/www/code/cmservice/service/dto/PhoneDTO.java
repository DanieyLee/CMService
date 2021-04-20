package cn.hupig.www.code.cmservice.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link cn.hupig.www.code.cmservice.domain.Phone} entity.
 */
public class PhoneDTO implements Serializable {
    
    private Long id;

    private String phone;

    private Integer code;

    private Instant effectiveTime;

    private Instant sendTime;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Instant getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Instant effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public Instant getSendTime() {
        return sendTime;
    }

    public void setSendTime(Instant sendTime) {
        this.sendTime = sendTime;
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
        if (!(o instanceof PhoneDTO)) {
            return false;
        }

        return id != null && id.equals(((PhoneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhoneDTO{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", code=" + getCode() +
            ", effectiveTime='" + getEffectiveTime() + "'" +
            ", sendTime='" + getSendTime() + "'" +
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
