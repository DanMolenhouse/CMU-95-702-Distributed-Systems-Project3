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
import static java.lang.Integer.parseInt;


public class BlockChain<HTObject> {

    // ArrayList to store the blocks
    public static ArrayList<Block> blockchain;
    public static int hashesPerSecond;
    public static String chainHash;

    public BlockChain(){
        blockchain = new ArrayList<Block>();
        chainHash = "";
        hashesPerSecond = 10;
    }

    public static void main(java.lang.String[] args) throws IOException, NoSuchAlgorithmException {

        Scanner s= new Scanner(System.in);
        BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
        String m;

        BlockChain mainChain = new BlockChain();
        mainChain.computeHashesPerSecond();

        Block genesis = new Block(0, getTime(), "Genesis", 2);
        genesis.setPreviousHash("");
        genesis.proofOfWork();

        addBlock(genesis);

        boolean test = true;

        while (test == true) {
            //Assemble user interface
            displayMenu();

            //Switch case for each integer
            // 1 -> view status
            // 2 -> add transaction
            // 3 -> verify blockchain
            // 4 -> Corrupt blockchain
            // 5 -> Repair blockchain
            // 6 -> Exit

            switch(s.nextInt()){
                case 0:{
                    //Status
                    System.out.println("Current Size of chain: " + mainChain.getChainSize());
                    System.out.println("Difficulty of most recent block: " + mainChain.getLatestBlock().getDifficulty());
                    System.out.println("Total difficulty for all blocks: " + mainChain.getTotalDifficulty());
                    System.out.println("Approximate hashes per second on this machine: " + mainChain.getHashesPerSecond());
                    System.out.println("Expected total hashes required for the whole chain: " + mainChain.getTotalExpectedHashes());
                    System.out.println("Nonce for the most recent block: " + mainChain.getLatestBlock().getNonce());
                    System.out.println("Chain hash: " + mainChain.getLatestBlock().calculateHash() + "\n");


                }; break;
                case 1:{

                    Timestamp t1 = getTime();
                    //Transaction
                    System.out.println("Enter difficulty > 0 ");
                    int difficulty = parseInt(typed.readLine());

                    System.out.println("Enter transaction");
                    String transaction = typed.readLine();

                    Block newBlock = new Block(mainChain.getChainSize(), getTime(), transaction, difficulty);
                    newBlock.setPreviousHash(chainHash);
                    newBlock.proofOfWork();

                    mainChain.addBlock(newBlock);


                    Timestamp t2 = getTime();
                    double elapsedTime = t2.getTime() - t1.getTime();
                    System.out.println("Total execution time was " + elapsedTime + " milliseconds\n");

                }; break;
                case 2:{

                    Timestamp t1 = getTime();

                    //Verify chain
                    System.out.println(mainChain.isChainValid());

                    Timestamp t2 = getTime();
                    double elapsedTime = t2.getTime() - t1.getTime();
                    System.out.println("Total execution time was " + elapsedTime + " milliseconds\n");


                }; break;
                case 3:{
                    //View chain
                    System.out.println("View the blockchain: " + mainChain.toString());
                }; break;
                case 4:{
                    //Corrupt chain
                    System.out.println("Corrupt the chain");
                    System.out.println("Enter block ID of block to corrupt");
                    int id = parseInt(typed.readLine());

                    System.out.println("Enter new data for block");
                    String newData = typed.readLine();

                    blockchain.get(id).setData(newData);

                    System.out.println("Block " + id + " now contains " + newData);

                }; break;
                case 5:{
                    //Fix chain
                    Timestamp t1 = getTime();

                    mainChain.repairChain();

                    Timestamp t2 = getTime();
                    double elapsedTime = t2.getTime() - t1.getTime();
                    System.out.println("Total execution time was " + elapsedTime + " milliseconds\n");
                }; break;
                case 6: System.out.println("Exiting.\n");test = false; break;
                default: System.out.println("Incorrect submission. Client closing."); break;
            }

        }
    }

    private static void displayMenu() {
        System.out.println("0. View basic blockchain status.");
        System.out.println("1. Add a transaction to the blockchain.");
        System.out.println("2. Verify the blockchain.");
        System.out.println("3. View the blockchain.");
        System.out.println("4. Corrupt the chain.");
        System.out.println("5. Hide the corruption by repairing the chain.");
        System.out.println("6. Exit client.");
    }

    //getters and setters
    //

    //Chain hash getter
    public java.lang.String getChainHash(){
        return chainHash;
    }

    //Time getter
    public static java.sql.Timestamp getTime(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp;
    }

    //Latest block getter
    public Block getLatestBlock(){
        int i = blockchain.size()-1;
        return blockchain.get(i);
    }

    //Chain size getter
    public int getChainSize(){
        return blockchain.size();
    }

    //gets block at ID
    public Block getBlock(int i){
        return blockchain.get(i);
    }

    //Gets total difficulty
    public int getTotalDifficulty(){
        int runningtotal = 0;
        for(int i = 0; i < blockchain.size(); i++){
            runningtotal = runningtotal + blockchain.get(i).getDifficulty();
        }
        return runningtotal;
    }

    //calculates expected total hashes
    public double getTotalExpectedHashes(){
        double totalExpectedHashes = 0;
        for(int i = 0; i < blockchain.size(); i++){
            totalExpectedHashes = totalExpectedHashes + Math.pow(16, blockchain.get(i).getDifficulty());
        }
        return totalExpectedHashes;
    }

    //Gets the hashes per second previously calculated
    public int getHashesPerSecond(){
        return this.hashesPerSecond;
    }


    //calculates hashes per second based on hash rate od machine
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

    //adds block to blockchain and sets chain hash
    public static void addBlock(Block newBlock){
        blockchain.add(newBlock);
        chainHash = newBlock.calculateHash();
    }

    //Tests if chain is valid
    public java.lang.String isChainValid(){

        //if block is empty besides genesis chain
       if(blockchain.size()==1){

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

       //if blockchain has blocks added
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

                //check proof of work / leading zeros
                if(!block1hash.substring( 0, blockchain.get(i).getDifficulty()).equals(target)){
                    return "Block " + i + " proof of work error";
                }else if(!block0hash.equals(block1hashPointer)){
                    //test previous hash
                    return "Block " + i  + " hash pointer error";
                }
            }
        }

       //check the chain hash
       if(!chainHash.equals(blockchain.get(blockchain.size()-1).calculateHash())){
           return "Chain hash error";
       }

       //if no errors return true
       return "Chain verification: TRUE";
    }

    //repairs chain
    public void repairChain(){

        //when only genesis exists
        if(blockchain.size()==1) {
            //Reset previous hash and recompute proof of work
            blockchain.get(0).setPreviousHash("");
            blockchain.get(0).proofOfWork();
        }

        //For larger chain
        if(blockchain.size()>1) {
            for (int i = 1; i < blockchain.size(); i++) {
                //reset previous hash and recompute proof of work
                blockchain.get(i).setPreviousHash(blockchain.get(i-1).calculateHash());
                blockchain.get(i).proofOfWork();
            }
        }

        //reset chainhash
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
