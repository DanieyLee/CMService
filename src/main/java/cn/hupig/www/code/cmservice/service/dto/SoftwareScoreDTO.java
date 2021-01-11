package cn.hupig.www.code.cmservice.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link cn.hupig.www.code.cmservice.domain.SoftwareScore} entity.
 */
public class SoftwareScoreDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double score;

    private String createUser;

    private Instant creatTime;

    private String updateUser;

    private Instant updateTime;

    private String note;


    private Long softwareId;

    private String softwareName;

    private Long userLinkId;

    private String userLinkFirstName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    public Long getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(Long softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
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
        if (!(o instanceof SoftwareScoreDTO)) {
            return false;
        }

        return id != null && id.equals(((SoftwareScoreDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoftwareScoreDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", createUser='" + getCreateUser() + "'" +
            ", creatTime='" + getCreatTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", note='" + getNote() + "'" +
            ", softwareId=" + getSoftwareId() +
            ", softwareName='" + getSoftwareName() + "'" +
            ", userLinkId=" + getUserLinkId() +
            ", userLinkFirstName='" + getUserLinkFirstName() + "'" +
            "}";
    }
}
