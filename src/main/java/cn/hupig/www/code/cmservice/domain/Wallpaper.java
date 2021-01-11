package cn.hupig.www.code.cmservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import cn.hupig.www.code.cmservice.domain.enumeration.ImageType;

/**
 * A Wallpaper.
 */
@Entity
@Table(name = "wallpaper")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Wallpaper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_name")
    private String imageName;

    @NotNull
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_pixel")
    private String imagePixel;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type")
    private ImageType imageType;

    @Column(name = "visitor_volume")
    private Integer visitorVolume;

    @Column(name = "is_download")
    private Boolean isDownload;

    @Column(name = "jhi_like")
    private Long like;

    @Column(name = "state")
    private Boolean state;

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
    @JsonIgnoreProperties(value = "wallpapers", allowSetters = true)
    private UserLink userLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public Wallpaper imageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Wallpaper imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePixel() {
        return imagePixel;
    }

    public Wallpaper imagePixel(String imagePixel) {
        this.imagePixel = imagePixel;
        return this;
    }

    public void setImagePixel(String imagePixel) {
        this.imagePixel = imagePixel;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public Wallpaper imageType(ImageType imageType) {
        this.imageType = imageType;
        return this;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public Integer getVisitorVolume() {
        return visitorVolume;
    }

    public Wallpaper visitorVolume(Integer visitorVolume) {
        this.visitorVolume = visitorVolume;
        return this;
    }

    public void setVisitorVolume(Integer visitorVolume) {
        this.visitorVolume = visitorVolume;
    }

    public Boolean isIsDownload() {
        return isDownload;
    }

    public Wallpaper isDownload(Boolean isDownload) {
        this.isDownload = isDownload;
        return this;
    }

    public void setIsDownload(Boolean isDownload) {
        this.isDownload = isDownload;
    }

    public Long getLike() {
        return like;
    }

    public Wallpaper like(Long like) {
        this.like = like;
        return this;
    }

    public void setLike(Long like) {
        this.like = like;
    }

    public Boolean isState() {
        return state;
    }

    public Wallpaper state(Boolean state) {
        this.state = state;
        return this;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getCreateUser() {
        return createUser;
    }

    public Wallpaper createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreatTime() {
        return creatTime;
    }

    public Wallpaper creatTime(Instant creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public Wallpaper updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Wallpaper updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public Wallpaper note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public UserLink getUserLink() {
        return userLink;
    }

    public Wallpaper userLink(UserLink userLink) {
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
        if (!(o instanceof Wallpaper)) {
            return false;
        }
        return id != null && id.equals(((Wallpaper) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wallpaper{" +
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
            "}";
    }
}
