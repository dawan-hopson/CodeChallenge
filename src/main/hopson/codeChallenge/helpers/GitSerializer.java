package main.hopson.codeChallenge.helpers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import main.hopson.codeChallenge.models.GitInfo;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;

import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named
public class GitSerializer extends StdDeserializer<List<GitInfo>> {

    private Logger logger = Logger.getLogger(this.getClass());

    public GitSerializer(){
        this(null);
    }

    private GitSerializer(Class<GitInfo> rt){
        super(rt);
    }

    @Override
    public List<GitInfo> deserialize(JsonParser var1, DeserializationContext var2) throws IOException {
        List<GitInfo> gitInfoList = new ArrayList<>();

        try {
            TreeNode results1 = var1.readValueAsTree();
            JSONArray jsonArray = new JSONArray(results1.toString());
            int maxNumOfFollowers = 5;
            if(jsonArray.length() < 5){
                maxNumOfFollowers = jsonArray.length();
            }

            for(int index = 0; index < maxNumOfFollowers; index++){
                GitInfo gitInfo = new GitInfo();
                gitInfo.setId(jsonArray.getJSONObject(index).get("id").toString());
                gitInfo.setFollowersUrl(jsonArray.getJSONObject(index).get("followers_url").toString());
                gitInfo.setLogin(jsonArray.getJSONObject(index).get("login").toString());
                gitInfoList.add(gitInfo);
            }

        }
        catch (JSONException e){
            logger.error("Error deserializing the users followers information", e);
            throw e;
        }
        return gitInfoList;
    }
}
