import com.google.gson.JsonObject;

import java.math.BigInteger;

public class ResponseMessage {

    private JsonObject jsonResponse;

    //Basic constructor for simple operation replies
    public ResponseMessage(int operation){
        jsonResponse = new JsonObject();
        jsonResponse.addProperty("operation", operation);
    }

    //Constructor for when response must have an elapsed time attached (Case 1 & 5)
    public ResponseMessage(int operation, double elapsedTime){
        jsonResponse = new JsonObject();
        jsonResponse.addProperty("operation", operation);
        jsonResponse.addProperty("elapsedTime", elapsedTime);
    }

    //Constructor for case 4
    public ResponseMessage(int operation, int index, String tx){
        jsonResponse = new JsonObject();
        jsonResponse.addProperty("index", index);
        jsonResponse.addProperty("tx", tx);
    }

    //Constructor for case 3 (View chain)
    public ResponseMessage(int operation, String wholechain){
        jsonResponse = new JsonObject();
        jsonResponse.addProperty("operation", operation);
        jsonResponse.addProperty("wholeChain", wholechain);
    }

    //Constructor for case 2
    public ResponseMessage(int operation, double elapsedTime, String verification){
        jsonResponse = new JsonObject();
        jsonResponse.addProperty("operation", operation);
        jsonResponse.addProperty("elapsedTime", elapsedTime);
        jsonResponse.addProperty("verification", verification);
    }

    //Constructor for operaiton 0 with lots of information replied
    public ResponseMessage(int operation, int size, int difficulty, double totalDifficulty, int hashesPerSecond, double totalExpectedHashes, BigInteger nonce, String hash){
        jsonResponse = new JsonObject();
        jsonResponse.addProperty("operation", operation);
        //Add all requested information to a JsonObject
        jsonResponse.addProperty("size", size);
        jsonResponse.addProperty("difficulty", difficulty);
        jsonResponse.addProperty("totalDifficulty", totalDifficulty);
        jsonResponse.addProperty("hashesPerSecond", hashesPerSecond);
        jsonResponse.addProperty("totalExpectedHashes", totalExpectedHashes);
        jsonResponse.addProperty("nonce", nonce);
        jsonResponse.addProperty("hash", hash);
    }

    public JsonObject getJsonResponse(){
        return jsonResponse;
    }


}
