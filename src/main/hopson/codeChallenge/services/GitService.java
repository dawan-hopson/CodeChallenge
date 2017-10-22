package main.hopson.codeChallenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import main.hopson.codeChallenge.helpers.GitSerializer;
import main.hopson.codeChallenge.models.GitInfo;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Named
public class GitService {

    @Inject
    private RestTemplate restTemplate;

    @Inject
    private GitSerializer gitSerializer;

    public Integer getUsersId(String userLogin) throws IOException {
        URI uri = URI.create("https://api.github.com/users/" + userLogin);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readTree(responseEntity.getBody()).get("id").intValue();
    }

    public GitInfo getFollowersInfo(Integer gitId) throws IOException {
        URI uri = URI.create("https://api.github.com/user/" + gitId);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

        GitInfo gitInfo = new GitInfo();
        ObjectMapper objectMapper = getObjectMapper(List.class, gitSerializer);


        gitInfo.setId(objectMapper.readTree(responseEntity.getBody()).get("id").toString());
        gitInfo.setFollowersUrl(objectMapper.readTree(responseEntity.getBody()).get("followers_url").textValue());
        gitInfo.setLogin(objectMapper.readTree(responseEntity.getBody()).get("login").textValue());
        gitInfo.setGitFollowersList(getFollowers(gitInfo.getFollowersUrl()));

        retrieveNextLevelFollowers(gitInfo.getGitFollowersList());
        for(GitInfo gitInfo2 : gitInfo.getGitFollowersList()){
            retrieveNextLevelFollowers(gitInfo2.getGitFollowersList());
        }

        return gitInfo;
    }

    private List<GitInfo> getFollowers(String followersUrl) throws IOException{
        URI uri = URI.create(followersUrl);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

        List<GitInfo> gitInfoList = new ArrayList<>();
        ObjectMapper objectMapper = getObjectMapper(List.class, gitSerializer);

        gitInfoList.addAll(objectMapper.readValue(responseEntity.getBody(), List.class));

        return gitInfoList;
    }

    private void retrieveNextLevelFollowers(List<GitInfo> gitInfoList) throws IOException{
        for(GitInfo gitInfo : gitInfoList){
            gitInfo.setGitFollowersList(getFollowers(gitInfo.getFollowersUrl()));
        }
    }

    private ObjectMapper getObjectMapper(Class clazz, StdDeserializer stdDeserializer){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(clazz, stdDeserializer);
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }
}

