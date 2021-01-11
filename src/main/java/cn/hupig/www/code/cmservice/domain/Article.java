package cn.hupig.www.code.cmservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author")
    private String author;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "views")
    private Long views;

    @Column(name = "like_number")
    private Long likeNumber;

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
    @JsonIgnoreProperties(value = "articles", allowSetters = true)
    private ArticleType articleType;

    @ManyToOne
    @JsonIgnoreProperties(value = "articles", allowSetters = true)
    private UserLink userLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Article title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public Article author(String author) {
        this.author = author;
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public Article content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getViews() {
        return views;
    }

    public Article views(Long views) {
        this.views = views;
        return this;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Long getLikeNumber() {
        return likeNumber;
    }

    public Article likeNumber(Long likeNumber) {
        this.likeNumber = likeNumber;
        return this;
    }

    public void setLikeNumber(Long likeNumber) {
        this.likeNumber = likeNumber;
    }

    public Boolean isState() {
        return state;
    }

    public Article state(Boolean state) {
        this.state = state;
        return this;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getCreateUser() {
        return createUser;
    }

    public Article createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreatTime() {
        return creatTime;
    }

    public Article creatTime(Instant creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public Article updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Article updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public Article note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public Article articleType(ArticleType articleType) {
        this.articleType = articleType;
        return this;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public UserLink getUserLink() {
        return userLink;
    }

    public Article userLink(UserLink userLink) {
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
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", content='" + getContent() + "'" +
            ", views=" + getViews() +
            ", likeNumber=" + getLikeNumber() +
            ", state='" + isState() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", creatTime='" + getCreatTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
