package main.hopson.codeChallenge.controllers;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import main.hopson.codeChallenge.models.GitInfo;
import main.hopson.codeChallenge.services.GitService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@Api(value = "GitService", description = "Git code challenge api")
@RequestMapping(value = "/git")
public class GitController {

    Logger logger = Logger.getLogger(this.getClass());

    @Inject
    private GitService gitService;

    @ApiOperation(value = "Retrieve followers by git id")
    @RequestMapping(value = "/followers", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GitInfo> getFollowerGitHubIds(@ApiParam(name="gitId", required = true) @RequestParam Integer gitId){
        GitInfo gitInfo;

        try{
            gitInfo = gitService.getFollowersInfo(gitId);
        }
        catch (Exception e){
            logger.error("Error followers for git id " + gitId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(gitInfo);
    }

    @ApiOperation(value = "Get git id by user login")
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Integer> getUsersId(@ApiParam(name="userLogin", required = true)@RequestParam String userLogin){
        int userId = 0;
        try{
            userId = gitService.getUsersId(userLogin);
        }
        catch (Exception e){
            logger.error("Error retrieving user id for userLogin " + userLogin, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }
}
