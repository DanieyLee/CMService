package cn.hupig.www.code.cmservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import cn.hupig.www.code.cmservice.domain.enumeration.SystemType;

/**
 * A Software.
 */
@Entity
@Table(name = "software")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Software implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stars")
    private Boolean stars;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "jhi_explain")
    private String explain;

    @Lob
    @Column(name = "software_ico")
    private byte[] softwareICO;

    @Column(name = "software_ico_content_type")
    private String softwareICOContentType;

    @Column(name = "score")
    private Double score;

    @Column(name = "size")
    private Long size;

    @Column(name = "version")
    private String version;

    @Enumerated(EnumType.STRING)
    @Column(name = "apply_system")
    private SystemType applySystem;

    @Column(name = "jhi_show")
    private Boolean show;

    @Column(name = "allow")
    private Boolean allow;

    @Column(name = "download_url")
    private String downloadUrl;

    @Column(name = "download_number")
    private Long downloadNumber;

    @Column(name = "browse_number")
    private Long browseNumber;

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
    @JsonIgnoreProperties(value = "software", allowSetters = true)
    private SoftwareType softwareType;

    @ManyToOne
    @JsonIgnoreProperties(value = "software", allowSetters = true)
    private UserLink userLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isStars() {
        return stars;
    }

    public Software stars(Boolean stars) {
        this.stars = stars;
        return this;
    }

    public void setStars(Boolean stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public Software name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExplain() {
        return explain;
    }

    public Software explain(String explain) {
        this.explain = explain;
        return this;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public byte[] getSoftwareICO() {
        return softwareICO;
    }

    public Software softwareICO(byte[] softwareICO) {
        this.softwareICO = softwareICO;
        return this;
    }

    public void setSoftwareICO(byte[] softwareICO) {
        this.softwareICO = softwareICO;
    }

    public String getSoftwareICOContentType() {
        return softwareICOContentType;
    }

    public Software softwareICOContentType(String softwareICOContentType) {
        this.softwareICOContentType = softwareICOContentType;
        return this;
    }

    public void setSoftwareICOContentType(String softwareICOContentType) {
        this.softwareICOContentType = softwareICOContentType;
    }

    public Double getScore() {
        return score;
    }

    public Software score(Double score) {
        this.score = score;
        return this;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getSize() {
        return size;
    }

    public Software size(Long size) {
        this.size = size;
        return this;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getVersion() {
        return version;
    }

    public Software version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public SystemType getApplySystem() {
        return applySystem;
    }

    public Software applySystem(SystemType applySystem) {
        this.applySystem = applySystem;
        return this;
    }

    public void setApplySystem(SystemType applySystem) {
        this.applySystem = applySystem;
    }

    public Boolean isShow() {
        return show;
    }

    public Software show(Boolean show) {
        this.show = show;
        return this;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

    public Boolean isAllow() {
        return allow;
    }

    public Software allow(Boolean allow) {
        this.allow = allow;
        return this;
    }

    public void setAllow(Boolean allow) {
        this.allow = allow;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public Software downloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        return this;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Long getDownloadNumber() {
        return downloadNumber;
    }

    public Software downloadNumber(Long downloadNumber) {
        this.downloadNumber = downloadNumber;
        return this;
    }

    public void setDownloadNumber(Long downloadNumber) {
        this.downloadNumber = downloadNumber;
    }

    public Long getBrowseNumber() {
        return browseNumber;
    }

    public Software browseNumber(Long browseNumber) {
        this.browseNumber = browseNumber;
        return this;
    }

    public void setBrowseNumber(Long browseNumber) {
        this.browseNumber = browseNumber;
    }

    public Boolean isState() {
        return state;
    }

    public Software state(Boolean state) {
        this.state = state;
        return this;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getCreateUser() {
        return createUser;
    }

    public Software createUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Instant getCreatTime() {
        return creatTime;
    }

    public Software creatTime(Instant creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public void setCreatTime(Instant creatTime) {
        this.creatTime = creatTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public Software updateUser(String updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Software updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public Software note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public SoftwareType getSoftwareType() {
        return softwareType;
    }

    public Software softwareType(SoftwareType softwareType) {
        this.softwareType = softwareType;
        return this;
    }

    public void setSoftwareType(SoftwareType softwareType) {
        this.softwareType = softwareType;
    }

    public UserLink getUserLink() {
        return userLink;
    }

    public Software userLink(UserLink userLink) {
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
        if (!(o instanceof Software)) {
            return false;
        }
        return id != null && id.equals(((Software) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Software{" +
            "id=" + getId() +
            ", stars='" + isStars() + "'" +
            ", name='" + getName() + "'" +
            ", explain='" + getExplain() + "'" +
            ", softwareICO='" + getSoftwareICO() + "'" +
            ", softwareICOContentType='" + getSoftwareICOContentType() + "'" +
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
            "}";
    }
}
