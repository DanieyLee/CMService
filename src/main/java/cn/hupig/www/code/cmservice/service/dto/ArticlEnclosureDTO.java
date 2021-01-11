package cn.hupig.www.code.cmservice.service.dto;

import java.time.Instant;
import java.io.Serializable;
import cn.hupig.www.code.cmservice.domain.enumeration.FileType;

/**
 * A DTO for the {@link cn.hupig.www.code.cmservice.domain.ArticlEnclosure} entity.
 */
public class ArticlEnclosureDTO implements Serializable {
    
    private Long id;

    private String enclosureURL;

    private FileType enclosureType;

    private String createUser;

    private Instant creatTime;

    private String updateUser;

    private Instant updateTime;

    private String note;


    private Long articleId;

    private String articleTitle;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnclosureURL() {
        return enclosureURL;
    }

    public void setEnclosureURL(String enclosureURL) {
        this.enclosureURL = enclosureURL;
    }

    public FileType getEnclosureType() {
        return enclosureType;
    }

    public void setEnclosureType(FileType enclosureType) {
        this.enclosureType = enclosureType;
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

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticlEnclosureDTO)) {
            return false;
        }

        return id != null && id.equals(((ArticlEnclosureDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArticlEnclosureDTO{" +
            "id=" + getId() +
            ", enclosureURL='" + getEnclosureURL() + "'" +
            ", enclosureType='" + getEnclosureType() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", creatTime='" + getCreatTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", note='" + getNote() + "'" +
            ", articleId=" + getArticleId() +
            ", articleTitle='" + getArticleTitle() + "'" +
            "}";
    }
}
