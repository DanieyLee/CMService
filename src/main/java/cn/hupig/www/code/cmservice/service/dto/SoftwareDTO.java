package cn.hupig.www.code.cmservice.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import cn.hupig.www.code.cmservice.domain.enumeration.SystemType;

/**
 * A DTO for the {@link cn.hupig.www.code.cmservice.domain.Software} entity.
 */
public class SoftwareDTO implements Serializable {
    
    private Long id;

    private Boolean stars;

    @NotNull
    private String name;

    private String explain;

    @Lob
    private byte[] softwareICO;

    private String softwareICOContentType;
    private Double score;

    private Long size;

    private String version;

    private SystemType applySystem;

    private Boolean show;

    private Boolean allow;

    private String downloadUrl;

    private Long downloadNumber;

    private Long browseNumber;

    private Boolean state;

    private String createUser;

    private Instant creatTime;

    private String updateUser;

    private Instant updateTime;

    private String note;


    private Long softwareTypeId;

    private String softwareTypeType;

    private Long userLinkId;

    private String userLinkFirstName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStars() {
        return stars;
    }

    public void setStars(Boolean stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public byte[] getSoftwareICO() {
        return softwareICO;
    }

    public void setSoftwareICO(byte[] softwareICO) {
        this.softwareICO = softwareICO;
    }

    public String getSoftwareICOContentType() {
        return softwareICOContentType;
    }

    public void setSoftwareICOContentType(String softwareICOContentType) {
        this.softwareICOContentType = softwareICOContentType;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SystemType getApplySystem() {
        return applySystem;
    }

    public void setApplySystem(SystemType applySystem) {
        this.applySystem = applySystem;
    }

    public Boolean isShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Boolean isAllow() {
        return allow;
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Long getDownloadNumber() {
        return downloadNumber;
    }

    public void setDownloadNumber(Long downloadNumber) {
        this.downloadNumber = downloadNumber;
    }

    public Long getBrowseNumber() {
        return browseNumber;
    }

    public void setBrowseNumber(Long browseNumber) {
        this.browseNumber = browseNumber;
    }

    public Boolean isState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
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

    public Long getSoftwareTypeId() {
        return softwareTypeId;
    }

    public void setSoftwareTypeId(Long softwareTypeId) {
        this.softwareTypeId = softwareTypeId;
    }

    public String getSoftwareTypeType() {
        return softwareTypeType;
    }

    public void setSoftwareTypeType(String softwareTypeType) {
        this.softwareTypeType = softwareTypeType;
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
        if (!(o instanceof SoftwareDTO)) {
            return false;
        }

        return id != null && id.equals(((SoftwareDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoftwareDTO{" +
            "id=" + getId() +
            ", stars='" + isStars() + "'" +
            ", name='" + getName() + "'" +
            ", explain='" + getExplain() + "'" +
            ", softwareICO='" + getSoftwareICO() + "'" +
            ", score=" + getScore() +
            ", size=" + getSize() +
            ", version='" + getVersion() + "'" +
            ", applySystem='" + getApplySystem() + "'" +
            ", show='" + isShow() + "'" +
            ", allow='" + isAllow() + "'" +
            ", downloadUrl='" + getDownloadUrl() + "'" +
            ", downloadNumber=" + getDownloadNumber() +
            ", browseNumber=" + getBrowseNumber() +
            ", state='" + isState() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", creatTime='" + getCreatTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", note='" + getNote() + "'" +
            ", softwareTypeId=" + getSoftwareTypeId() +
            ", softwareTypeType='" + getSoftwareTypeType() + "'" +
            ", userLinkId=" + getUserLinkId() +
            ", userLinkFirstName='" + getUserLinkFirstName() + "'" +
            "}";
    }
}
