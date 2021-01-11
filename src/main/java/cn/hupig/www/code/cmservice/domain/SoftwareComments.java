package cn.hupig.www.code.cmservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A SoftwareComments.
 */
@Entity
@Table(name = "software_comments")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoftwareComments implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tx_title", nullable = false)
    private String txTitle;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

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
    @JsonIgnoreProperties(value = "softwareComments", allowSetters = true)
    private Software software;

    @ManyToOne
    @JsonIgnoreProperties(value = "softwareComments", allowSetters = true)
    private UserLink userLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTxTitle() {
        return txTitle;
    }

    public SoftwareComments txTitle(String txTitle) {
        this.txTitle = txTitle;
        return this;
    }

    public void setTxTitle(String txTitle) {
        this.txTitle = txTitle;
    }

    public String getContent() {
        return content;
    }

    public SoftwareComments content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateUser() {
        return createUser;
    }

    public SoftwareComments createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreatTime() {
        return creatTime;
    }

    public SoftwareComments creatTime(Instant creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public SoftwareComments updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public SoftwareComments updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public SoftwareComments note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Software getSoftware() {
        return software;
    }

    public SoftwareComments software(Software software) {
        this.software = software;
        return this;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public UserLink getUserLink() {
        return userLink;
    }

    public SoftwareComments userLink(UserLink userLink) {
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
        if (!(o instanceof SoftwareComments)) {
            return false;
        }
        return id != null && id.equals(((SoftwareComments) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoftwareComments{" +
            "id=" + getId() +
            ", txTitle='" + getTxTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", createUser='" + getCreateUser() + "'" +
            ", creatTime='" + getCreatTime() + "'" +
            ", updateUser='" + getUpdateUser() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
