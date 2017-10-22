package test.hopson.codeChallenge.services;

import main.hopson.codeChallenge.config.RestTemplateConfig;
import main.hopson.codeChallenge.models.GitInfo;
import main.hopson.codeChallenge.services.GitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestTemplateConfig.class}, loader = AnnotationConfigContextLoader.class)
public class GitService_UT {

    @Inject
    private GitService gitService;

    @Test
    public void testGetFollowersInfo() throws IOException {
        GitInfo gitInfo = gitService.getFollowersInfo(2);
        assertEquals("defunkt", gitInfo.getLogin());
        assertEquals("https://api.github.com/users/defunkt/followers", gitInfo.getFollowersUrl());
        assertNotNull(gitInfo);
    }
}
