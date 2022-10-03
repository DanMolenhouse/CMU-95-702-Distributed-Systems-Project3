import com.google.gson.JsonObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Timestamp;
// Project 3 Task 1 - Dan Molenhouse dmolenho
// Block

public class Block {

    //initialize variables existing in each block
    private int index;
    private BigInteger nonce;
    private Timestamp timeStamp;
    private String data;
    private String hash;
    private String previousHash = "";
    private int difficulty;

    //Constructor for Block class
    //Note that hash / proof of work is not calculated here, the previous hash must be set by user first
    public Block(int index, Timestamp timeStamp, String data, int difficulty) {
        this.nonce = BigInteger.valueOf(0);
        this.index = index;
        this.timeStamp = timeStamp;
        this.data = data;
        this.difficulty = difficulty;
    }

    //Calculate hash function concates block data into a string and finds the
    //SHA-256 Hash of that string.
    //Output is the hash itself as a  hexadecimal string
    //Lab 1 used as reference for this code

    public String calculateHash() {

        //Concate data together to be hashed
        String dataToHash = index + timeStamp.toString() + data + previousHash + nonce + difficulty;


        try {

            // SHA-256 message digest for hashing purposes
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //byte array of hashed string
            byte[] hash = digest.digest(dataToHash.getBytes("UTF-8"));

            //buffer for hexadecimel string
            StringBuffer hexadecimalString = new StringBuffer();

            //Convert byteArray to from bytes to hexadecimel
            for (int i = 0; i < hash.length; i++) {
                String hexadecimal = Integer.toHexString(0xff & hash[i]);
                if (hexadecimal.length() == 1) hexadecimalString.append('0');
                hexadecimalString.append(hexadecimal);
            }

            //set as string
            String hashValue = hexadecimalString.toString();

            return hashValue;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //The following chunk of data is just simple getter and setter methods
    //for all Block parameters

    //Getter Data
    public String getData(){
        return this.data;
    }
    //Setter for Data
    public void setData(String data) {
        this.data = data;
    }

    //Getter Difficulty
    public int getDifficulty(){
        return this.difficulty;
    }
    //Setter for Difficulty
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    //Getter Index
    public int getIndex(){
        return this.index;
    }
    //Setter for Index
    public void setIndex(int index) {
        this.index = index;
    }

    //Getter Nonce
    public BigInteger getNonce(){
        return this.nonce;
    }

    //Getter previousHash
    public String getPreviousHash(){
        return this.previousHash;
    }
    //Setter for PreviousHash
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    //Getter Timestamp
    public Timestamp getTimestamp(){
        return this.timeStamp;
    }
    //Setter for Timestamp
    public void setTimestap(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    //End of getter / setter methods


    //Proof of Work
    //returns hash with target difficulty met
    public String proofOfWork(){
        //Temporary hash value
        String tempHash = calculateHash();

        //This creates the target hash based on the difficulty
        // if difficulty is 1, target is "0", if difficulty is 2, target is "00"
        String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"

        // While loop that calculates hash with the current nonce, tests if it meets the target, and if not increments nonce by one
        // and tries again
        while(!tempHash.substring( 0, difficulty).equals(target)) {

            nonce = nonce.add(BigInteger.ONE); //increment nonce by one
            tempHash = calculateHash(); //recalculate hash

        }

        return tempHash;
    }

    @Override
    // Add all properties to a JSON Object and output the JSON String
    // Source: https://www.tutorialspoint.com/gson/gson_first_application.htm
    public String toString() {
        //Originally used a manual string method but later changed it
        //String stringJsonRep = "{\"index\":"+index+", \"time stamp\": \""+timeStamp+"\", \"Tx\" : \""+data+"\", \"PrevHash\": \""+previousHash+"\", \"nonce\": \""+nonce+"\", \"difficulty\": \""+difficulty+"\"}";

        //Populate the object for each value
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("index", index);
        jsonObject.addProperty("time stamp", String.valueOf(timeStamp));
        jsonObject.addProperty("tx", data);
        jsonObject.addProperty("PrevHash", previousHash);
        jsonObject.addProperty("nonce", nonce);
        jsonObject.addProperty("difficulty", difficulty);

        //Create a string from the JSON object
        String jsonString = jsonObject.toString().replace("\\","");

        return jsonString;

    }

}
