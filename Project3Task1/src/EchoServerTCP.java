import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;

//Project2Task4 Dan Molenhouse dmolenho

public class EchoServerTCP {

    public static void main(String args[]) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Socket clientSocket = null;

        //Confirm server is operational
        System.out.println("---The server is running.---");

        BlockChain mainChain = new BlockChain();
        mainChain.computeHashesPerSecond();

        Block genesis = new Block(0, mainChain.getTime(), "Genesis", 2);
        genesis.setPreviousHash("");
        genesis.proofOfWork();

        mainChain.addBlock(genesis);

        try {
            int serverPort = 7777; // the server port we are using

            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);

            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making
             * the socket ready for reading and writing.
             */
            clientSocket = listenSocket.accept();
            // If we get here, then we are now connected to a client.

            // Set up "in" to read from the client socket
            Scanner in;
            in = new Scanner(clientSocket.getInputStream());

            // Set up "out" to write to the client socket
            PrintWriter output;
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

            //initialize variables here to include all calls to them in scope
            String tx = null;
            int difficulty = 0;
            int index = 0;
            boolean poopoopeepee = true;


            /*
             * Forever,
             *   read a line from the socket
             *   print it to the console
             *   echo it (i.e. write it) back to the client
             */
            while (poopoopeepee == true) {
                //Wait for client interaction
                System.out.println("Blockchain server running");
                String data = in.nextLine();

                //Get JSON OBject form input stream
                Object object = new JsonParser().parse(data);
                JsonObject jsonObject = (JsonObject) object;

                System.out.println("We have a visitor");

                //Parse operation and other information from Json Object
                int operation = Integer.parseInt(jsonObject.get("operation").toString());

                //If statements to test if this exists
                if(jsonObject.get("tx")!=null){
                    tx = jsonObject.get("tx").toString();
                }
                if(jsonObject.get("difficulty")!=null){
                    difficulty = Integer.parseInt(jsonObject.get("difficulty").toString());
                }
                if(jsonObject.get("index")!=null){
                    index = Integer.parseInt(jsonObject.get("index").toString());
                }

                //switch case based on operation number
                switch (operation) {

                    //request block statistics
                    case 0: {

                        System.out.println("Operation Requested: 0" + "\n");

                        //Add all requested information to a JsonObject
                        int size = mainChain.getChainSize();
                        int difficultyint = mainChain.getLatestBlock().getDifficulty();
                        double totalDifficulty = mainChain.getTotalDifficulty();
                        int hashesPerSecond = mainChain.getHashesPerSecond();
                        double totalExpectedHash =  mainChain.getTotalExpectedHashes();
                        BigInteger nonce = mainChain.getLatestBlock().getNonce();
                        String hash = mainChain.getLatestBlock().calculateHash();
                        ResponseMessage jsonReply = new ResponseMessage(0,size,difficultyint,totalDifficulty,hashesPerSecond,totalExpectedHash,nonce,hash);

                        //Print response
                        System.out.println("Response: " + jsonReply.getJsonResponse().toString());

                        //Send response
                        output.println(jsonReply.getJsonResponse());
                        output.flush();

                        //Break and wait for future communcation
                        break;
                    }

                    //Add a block
                    case 1: {

                        //start time counter
                        System.out.println("Operation Requested: 1" + "\n");
                        System.out.println("Adding a block");
                        Timestamp t1 = mainChain.getTime();

                        //Create new block and set appropriate properties / complete proof of work
                        Block newBlock = new Block(mainChain.getChainSize(), mainChain.getTime(), tx, difficulty);
                        newBlock.setPreviousHash(mainChain.chainHash);
                        newBlock.proofOfWork();

                        //add block to blockchain
                        mainChain.addBlock(newBlock);


                        //Compute elapsed time
                        Timestamp t2 = mainChain.getTime();
                        double elapsedTime = t2.getTime() - t1.getTime();
                        System.out.println("Total execution time was " + elapsedTime + " milliseconds\n");

                        //
                        ResponseMessage jsonReply = new ResponseMessage(1, elapsedTime);

                        //Send reply
                        System.out.println("Response: " + jsonReply.getJsonResponse().toString());
                        output.println(jsonReply.getJsonResponse());
                        output.flush();
                    }
                    ;
                    break;

                    //Verify blockchain
                    case 2: {

                        System.out.println("Operation Requested: 2" + "\n");
                        System.out.println("Verifying entire chain");
                        Timestamp t1 = mainChain.getTime();

                        //Verify chain
                        String verification = mainChain.isChainValid();

                        Timestamp t2 = mainChain.getTime();
                        double elapsedTime = t2.getTime() - t1.getTime();
                        System.out.println("Total execution time was " + elapsedTime + " milliseconds\n");

                        ResponseMessage jsonReply = new ResponseMessage(2,elapsedTime,verification);

                        System.out.println("Response: " + jsonReply.getJsonResponse().toString());
                        output.println(jsonReply.getJsonResponse());
                        output.flush();
                    }
                    ;
                    break;

                    //view the blockchain
                    case 3: {
                        //View chain
                        System.out.println("Operation Requested: 3" + "\n");
                        System.out.println("View the blockchain");
                        String wholeChain = mainChain.toString();

                        ResponseMessage jsonReply = new ResponseMessage(3, wholeChain);

                        System.out.println("Response: " + jsonReply.getJsonResponse().toString());

                        output.println(jsonReply.getJsonResponse());
                        output.flush();
                    }
                    ;
                    break;

                    //corrupt data
                    case 4: {

                        //Corrupt chain
                        System.out.println("Operation Requested: 4" + "\n");
                        System.out.println("Corrupt the blockchain");

                        mainChain.getBlock(index).setData(tx);

                        System.out.println("Block " + index + " now contains " + tx);

                        ResponseMessage jsonReply = new ResponseMessage(4,index,tx);

                        System.out.println("Response: " + jsonReply.getJsonResponse().toString());
                        output.println(jsonReply.getJsonResponse());
                        output.flush();
                    }
                    ;
                    break;

                    //Fix the chain
                    case 5: {

                        //Fix chain
                        System.out.println("Operation Requested: 5" + "\n");
                        System.out.println("Repairing chain");
                        Timestamp t1 = mainChain.getTime();

                        mainChain.repairChain();

                        Timestamp t2 = mainChain.getTime();
                        double elapsedTime = t2.getTime() - t1.getTime();

                        ResponseMessage jsonReply = new ResponseMessage(5, elapsedTime);

                        System.out.println("Response: " + jsonReply.getJsonResponse().toString());
                        output.println(jsonReply.getJsonResponse());
                        output.flush();
                    }
                    ;
                    break;

                    //Exit
                    case 6:
                        System.out.println("Operation Requested: 6" + "\n");
                        System.out.println("Exiting.\n");
                        poopoopeepee = false;

                        break;
                    default:
                        System.out.println("Incorrect submission.");
                        break;
                }

                //Make sure output is flushed
                output.flush();
            }

            // Handle exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());

            // If quitting (typically by you sending quit signal) clean up sockets
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }


}