
import com.google.gson.JsonObject;

public class RequestMessage {

    private JsonObject jsonRequest;

    public RequestMessage(int operation){
        jsonRequest = new JsonObject();
        jsonRequest.addProperty("operation", operation);

    }

    public RequestMessage(int operation, int option, String tx){
        jsonRequest = new JsonObject();
        jsonRequest.addProperty("operation", operation);

        if(operation == 1) {
            jsonRequest.addProperty("difficulty", option);
        }else if(operation == 4){
            jsonRequest.addProperty("index", option);
        }

        jsonRequest.addProperty("tx", tx);
    }

    public JsonObject getJsonRequest(){
        return jsonRequest;
    }


}
