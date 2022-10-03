import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
//Project2Task4 Dan Molenhouse dmolenho
public class EchoClientTCP {

    public static int serverPort;
    public static Socket clientSocket = null;

    public static void main(String args[]) throws IOException {

        EchoClientTCP ecTCP = new EchoClientTCP();
        Scanner s = new Scanner(System.in);

        // arguments supply hostname
        RequestMessage jsonRequest = null;

        int operation = 0;
        BufferedReader in = null;
        try {
            // Convert local host to IP Address, initialize server port to 6789, initialize socket
            // Create buffered reader and a placeholder string to read user inputs
            System.out.println("The client is running."); //Confirm client is active

            serverPort = 7777; //Prompt user for port to scan
            clientSocket = new Socket("localhost", serverPort);

            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
            //initializations for TCP stream
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

            String m;
            boolean poopoopeepee = true;

            while (poopoopeepee == true) {
                //Assemble user interface
                ecTCP.displayMenu();
                operation = s.nextInt();

                //Switch case for each integer
                // 1 -> view status
                // 2 -> add transaction
                // 3 -> verify blockchain
                // 4 -> Corrupt blockchain
                // 5 -> Repair blockchain
                // 6 -> Exit

                Object object = null;

                switch (operation) {
                    case 0: {
                        //request
                        jsonRequest = new RequestMessage(0);

                        out.println(jsonRequest.getJsonRequest());
                        out.flush();


                        String data = in.readLine();
                        object = new JsonParser().parse(data);
                        JsonObject jsonReply = (JsonObject) object;
                        System.out.println("Current Size of chain: " + jsonReply.get("size").toString());
                        System.out.println("Difficulty of most recent block: " + jsonReply.get("difficulty").toString());
                        System.out.println("Total difficulty for all blocks: " + jsonReply.get("totalDifficulty").toString());
                        System.out.println("Approximate hashes per second on this machine: " + jsonReply.get("hashesPerSecond").toString());
                        System.out.println("Expected total hashes required for the whole chain: " + jsonReply.get("totalExpectedHashes").toString());
                        System.out.println("Nonce for the most recent block: " + jsonReply.get("nonce").toString());
                        System.out.println("Chain hash: " + jsonReply.get("hash").toString() + "\n");
                        break;
                    }

                    case 1: {
                        System.out.println("Enter difficulty > 0 ");
                        int difficulty = parseInt(typed.readLine());

                        System.out.println("Enter transaction");
                        String transaction = typed.readLine();

                        jsonRequest = new RequestMessage(1, difficulty, transaction);

                        out.println(jsonRequest.getJsonRequest());
                        out.flush();

                        String data = in.readLine();
                        object = new JsonParser().parse(data);
                        JsonObject jsonReply = (JsonObject) object;
                        System.out.println("Total execution time was " + jsonReply.get("elapsedTime").toString() + " milliseconds\n");

                    }
                    ;
                    break;
                    case 2: {
                        jsonRequest = new RequestMessage(2);

                        out.println(jsonRequest.getJsonRequest());
                        out.flush();

                        String data = in.readLine();
                        object = new JsonParser().parse(data);
                        JsonObject jsonReply = (JsonObject) object;
                        System.out.println(jsonReply.get("verification").toString());
                        System.out.println("Total execution time was " + jsonReply.get("elapsedTime").toString() + " milliseconds\n");

                    }
                    ;
                    break;
                    case 3: {
                        jsonRequest = new RequestMessage(3);

                        out.println(jsonRequest.getJsonRequest());
                        out.flush();

                        String data = in.readLine();
                        object = new JsonParser().parse(data);
                        JsonObject jsonReply = (JsonObject) object;
                        System.out.println("View the blockchain: " + jsonReply.get("wholeChain").toString().replace("\\",""));

                    }
                    ;
                    break;
                    case 4: {
                        //Corrupt chain
                        System.out.println("Corrupt the chain");
                        System.out.println("Enter block ID of block to corrupt");
                        int id = parseInt(typed.readLine());

                        System.out.println("Enter new data for block");
                        String newData = typed.readLine();

                        jsonRequest = new RequestMessage(4, id, newData);


                        out.println(jsonRequest.getJsonRequest());
                        out.flush();

                        String data = in.readLine();
                        object = new JsonParser().parse(data);
                        JsonObject jsonReply = (JsonObject) object;
                        System.out.println("Block " + jsonReply.get("index").toString() + " now contains " + jsonReply.get("tx").toString().replace("\\",""));
                    }
                    ;
                    break;
                    case 5: {
                        //Fix chain
                        jsonRequest = new RequestMessage(5);

                        out.println(jsonRequest.getJsonRequest());
                        out.flush();

                        String data = in.readLine();
                        object = new JsonParser().parse(data);
                        JsonObject jsonReply = (JsonObject) object;

                        System.out.println("Total execution time was " + jsonReply.get("elapsedTime").toString() + " milliseconds\n");

                    }
                    ;
                    break;
                    case 6:
                        jsonRequest = new RequestMessage(6);

                        out.println(jsonRequest.getJsonRequest());
                        out.flush();

                        System.out.println("Client Exiting.\n");
                        poopoopeepee = false;
                        break;
                    default:
                        System.out.println("Incorrect submission. Client closing.");
                        break;
                }

            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
}