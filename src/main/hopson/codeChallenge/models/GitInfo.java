package main.hopson.codeChallenge.models;

import java.util.List;

public class GitInfo {

    private String login;
    private String id;
    private String followersUrl;
    private List<GitInfo> gitFollowersList;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public List<GitInfo> getGitFollowersList() {
        return gitFollowersList;
    }

    public void setGitFollowersList(List<GitInfo> gitFollowersList) {
        this.gitFollowersList = gitFollowersList;
    }
}
