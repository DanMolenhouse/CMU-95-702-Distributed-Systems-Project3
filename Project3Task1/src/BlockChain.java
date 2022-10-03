import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;



public class BlockChain<HTObject> {

    // ArrayList to store the blocks
    public static ArrayList<Block> blockchain;
    public static int hashesPerSecond;
    public static String chainHash;

    public BlockChain(){
        blockchain = new ArrayList<Block>();
        chainHash = "";
        hashesPerSecond = 0;
    }



    public String getChainHash(){
        return chainHash;
    }

    public static Timestamp getTime(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    public Block getLatestBlock(){
        int i = blockchain.size()-1;
        return blockchain.get(i);
    }

    public int getChainSize(){
        return blockchain.size();
    }

    public static void computeHashesPerSecond() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Timestamp t1 = getTime();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String dataToHash = "00000000";

        for(int i = 0; i < 2000000; i++){
            byte[] hash = digest.digest(dataToHash.getBytes("UTF-8"));
        }
        Timestamp t2 = getTime();

        double diff = t2.getTime() - t1.getTime();

        hashesPerSecond = (int) (2000000/diff);
    }

    public int getHashesPerSecond(){
        return this.hashesPerSecond;
    }

    public static void addBlock(Block newBlock){
        blockchain.add(newBlock);
        chainHash = newBlock.calculateHash();
    }

    public Block getBlock(int i){
        return blockchain.get(i);
    }

    public int getTotalDifficulty(){
        int runningtotal = 0;
        for(int i = 0; i < blockchain.size(); i++){
            runningtotal = runningtotal + blockchain.get(i).getDifficulty();
        }
        return runningtotal;
    }

    public double getTotalExpectedHashes(){
        double totalExpectedHashes = Math.pow(16,getTotalDifficulty());
        return totalExpectedHashes;
    }

    public String isChainValid(){

       if(blockchain.size()==1){
           String hashCurrent = blockchain.get(0).calculateHash();
           String hashCheck = blockchain.get(0).calculateHash();
           String target = new String(new char[blockchain.get(0).getDifficulty()]).replace('\0', '0');

           if(!chainHash.equals(hashCheck)){
               return "Chain hash mismatch";
           }else if(!hashCheck.substring( 0, blockchain.get(0).getDifficulty()).equals(target)){
               return "Genesis block proof of work error";
           }else{
               return "Chain verification: TRUE";
           }
       }

       if(blockchain.size()>1) {
            for (int i = 1; i < blockchain.size(); i++) {
                //Hash proof of work target
                String target = new String(new char[blockchain.get(i).getDifficulty()]).replace('\0', '0');

                //Get parent hash
                String block0hash = blockchain.get(i-1).calculateHash();

                //Get hash pointer
                String block1hashPointer = blockchain.get(i).getPreviousHash();

                //Get current hash
                String block1hash = blockchain.get(i).calculateHash();

                if(!block0hash.equals(block1hashPointer)){
                    return "Block " + i  + " hash pointer error";
                }else if(!block1hash.substring( 0, blockchain.get(i).getDifficulty()).equals(target)){
                    return "Block " + i + " proof of work error";
                }
            }
        }

       if(!chainHash.equals(blockchain.get(blockchain.size()-1).calculateHash())){
           return "Chain hash error";
       }

       return "Chain verification: TRUE";
    }

    public void repairChain(){

        if(blockchain.size()==1) {
            blockchain.get(0).setPreviousHash("");
            blockchain.get(0).proofOfWork();
        }

        if(blockchain.size()>1) {
            for (int i = 1; i < blockchain.size(); i++) {
                blockchain.get(i).setPreviousHash(blockchain.get(i-1).calculateHash());
                blockchain.get(i).proofOfWork();
            }
        }

        chainHash = blockchain.get(blockchain.size()-1).calculateHash();

    }

    @Override
    // Create JSON Array of all block information
    // Create JSON Object of Blockchain
    // Source: https://stackoverflow.com/questions/18983185/how-to-create-correct-jsonarray-in-java-using-jsonobject
    // Source: https://www.w3schools.com/js/js_json_arrays.asp
    // Source: https://dzone.com/articles/introduction-to-json-with-java

    public String toString(){
        //Creates JSON Array and Object, the array will be filled with all of the JSON Objects of each block
        //and the Object will be the representation of the entire blockchain
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();

        //For loop populates the array with each JSON String from the blocks that exist in the chain
        for(int i = 0; i < blockchain.size(); i++){
            jsonArray.add(blockchain.get(i).toString());
        }

        //The following two lines populate the blockchain JSON object with the array and the chain hash
        jsonObject.addProperty("ds_chain", jsonArray.toString().replace("\\",""));
        jsonObject.addProperty("chainHash", chainHash);

        //Convert to string and remove forward slashes
        String jsonString = jsonObject.toString().replace("\\","");

        return jsonString;

    }


}
