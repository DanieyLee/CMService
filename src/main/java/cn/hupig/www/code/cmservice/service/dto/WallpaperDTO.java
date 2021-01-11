package cn.hupig.www.code.cmservice.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import cn.hupig.www.code.cmservice.domain.enumeration.ImageType;

/**
 * A DTO for the {@link cn.hupig.www.code.cmservice.domain.Wallpaper} entity.
 */
public class WallpaperDTO implements Serializable {
    
    private Long id;

    private String imageName;

    @NotNull
    private String imageUrl;

    private String imagePixel;

    private ImageType imageType;

    private Integer visitorVolume;

    private Boolean isDownload;

    private Long like;

    private Boolean state;

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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePixel() {
        return imagePixel;
    }

    public void setImagePixel(String imagePixel) {
        this.imagePixel = imagePixel;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public Integer getVisitorVolume() {
        return visitorVolume;
    }

    public void setVisitorVolume(Integer visitorVolume) {
        this.visitorVolume = visitorVolume;
    }

    public Boolean isIsDownload() {
        return isDownload;
    }

    public void setIsDownload(Boolean isDownload) {
        this.isDownload = isDownload;
    }

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
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
        if (!(o instanceof WallpaperDTO)) {
            return false;
        }

        return id != null && id.equals(((WallpaperDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WallpaperDTO{" +
            "id=" + getId() +
            ", imageName='" + getImageName() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", imagePixel='" + getImagePixel() + "'" +
            ", imageType='" + getImageType() + "'" +
            ", visitorVolume=" + getVisitorVolume() +
            ", isDownload='" + isIsDownload() + "'" +
            ", like=" + getLike() +
            ", state='" + isState() + "'" +
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
