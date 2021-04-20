package cn.hupig.www.code.cmservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import cn.hupig.www.code.cmservice.domain.enumeration.FileType;

/**
 * A ArticleEnclosure.
 */
@Entity
@Table(name = "article_enclosure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ArticleEnclosure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enclosure_url")
    private String enclosureURL;

    @Enumerated(EnumType.STRING)
    @Column(name = "enclosure_type")
    private FileType enclosureType;

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
    @JsonIgnoreProperties(value = "ArticleEnclosures", allowSetters = true)
    private Article article;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnclosureURL() {
        return enclosureURL;
    }

    public ArticleEnclosure enclosureURL(String enclosureURL) {
        this.enclosureURL = enclosureURL;
        return this;
    }

    public void setEnclosureURL(String enclosureURL) {
        this.enclosureURL = enclosureURL;
    }

    public FileType getEnclosureType() {
        return enclosureType;
    }

    public ArticleEnclosure enclosureType(FileType enclosureType) {
        this.enclosureType = enclosureType;
        return this;
    }

    public void setEnclosureType(FileType enclosureType) {
        this.enclosureType = enclosureType;
    }

    public String getCreateUser() {
        return createUser;
    }

    public ArticleEnclosure createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreatTime() {
        return creatTime;
    }

    public ArticleEnclosure creatTime(Instant creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public ArticleEnclosure updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public ArticleEnclosure updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public ArticleEnclosure note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Article getArticle() {
        return article;
    }

    public ArticleEnclosure article(Article article) {
        this.article = article;
        return this;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArticleEnclosure)) {
            return false;
        }
        return id != null && id.equals(((ArticleEnclosure) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "articleEnclosure{" +
            "id=" + getId() +
            ", enclosureURL='" + getEnclosureURL() + "'" +
            ", enclosureType='" + getEnclosureType() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", creatTime='" + getCreatTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
