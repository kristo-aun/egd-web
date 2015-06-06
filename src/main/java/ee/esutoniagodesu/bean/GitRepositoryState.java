package ee.esutoniagodesu.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:git.properties", ignoreResourceNotFound = true)
public class GitRepositoryState {

    @JsonIgnore
    @Value("${git.branch}")
    private String branch;
    @JsonIgnore
    @Value("${git.dirty}")
    private String dirty;
    @JsonIgnore
    @Value("${git.tags}")
    private String tags;
    @JsonIgnore
    @Value("${git.commit.id.describe}")
    private String describe;
    @JsonIgnore
    @Value("${git.commit.id.describe-short}")
    private String shortDescribe;
    @JsonIgnore
    @Value("${git.commit.id}")
    private String commitId;
    @JsonIgnore
    @Value("${git.commit.id.abbrev}")
    private String commitIdAbbrev;
    @JsonIgnore
    @Value("${git.build.user.name}")
    private String buildUserName;
    @JsonIgnore
    @Value("${git.build.user.email}")
    private String buildUserEmail;

    @Value("${git.build.time}")
    private String buildTime;//näitame välja ainult kompileerimise aega
    @JsonIgnore
    @Value("${git.commit.user.name}")
    private String commitUserName;
    @JsonIgnore
    @Value("${git.commit.user.email}")
    private String commitUserEmail;
    @JsonIgnore
    @Value("${git.commit.message.full}")
    private String commitMessageFull;
    @JsonIgnore
    @Value("${git.commit.message.short}")
    private String commitMessageShort;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDirty() {
        return dirty;
    }

    public void setDirty(String dirty) {
        this.dirty = dirty;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getShortDescribe() {
        return shortDescribe;
    }

    public void setShortDescribe(String shortDescribe) {
        this.shortDescribe = shortDescribe;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getCommitIdAbbrev() {
        return commitIdAbbrev;
    }

    public void setCommitIdAbbrev(String commitIdAbbrev) {
        this.commitIdAbbrev = commitIdAbbrev;
    }

    public String getBuildUserName() {
        return buildUserName;
    }

    public void setBuildUserName(String buildUserName) {
        this.buildUserName = buildUserName;
    }

    public String getBuildUserEmail() {
        return buildUserEmail;
    }

    public void setBuildUserEmail(String buildUserEmail) {
        this.buildUserEmail = buildUserEmail;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getCommitUserName() {
        return commitUserName;
    }

    public void setCommitUserName(String commitUserName) {
        this.commitUserName = commitUserName;
    }

    public String getCommitUserEmail() {
        return commitUserEmail;
    }

    public void setCommitUserEmail(String commitUserEmail) {
        this.commitUserEmail = commitUserEmail;
    }

    public String getCommitMessageFull() {
        return commitMessageFull;
    }

    public void setCommitMessageFull(String commitMessageFull) {
        this.commitMessageFull = commitMessageFull;
    }

    public String getCommitMessageShort() {
        return commitMessageShort;
    }

    public void setCommitMessageShort(String commitMessageShort) {
        this.commitMessageShort = commitMessageShort;
    }
}
