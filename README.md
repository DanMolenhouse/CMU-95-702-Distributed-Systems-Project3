# Distributed-Systems-Project3
**Project 3 - Distributed Systems**


**Project Objective:**
1. Distinguish between functional and non-functional characteristics of distributed systems.
2. Understand how blockchains technology works by creating a blockchain API that a remote client interacts with.
    - Note that the blockchain implemented here is not true to real blockchains, which are decentralized and include P2P communication. This blockchain also does not include Merkle Trees, it simply serves as a foundation of understanding.
3. Use JSON messaging for communicaiton.

**Tasks:**

1. Build a standalone blockchain that allows the user to:
  - View basic blockchain status. This includes:
    - Number of transactions on the chain
    - Difficulty of the most recent block
    - Total difficulty for all blocks
    - Hashes per second of the current machine
    - Expected hashes for whole chain
    - Nonce for the most recent block
    - Hash of the whole chain
  - Add a transaction to the chain. For the purposes of this simple blockchain, a transaction is simply a string that says something like "Bob pays Joe 50 Coins". The user also chooses a difficulty in this step, which is the number of leading 0's in the hash of the block. 
  - Verify the blockchain.
  - Corrupt the chain.
  - Repair the chain and eliminate corruption. 
2. Implement the blockchain completed in Task 1 on a remote server.
3. Create a client that communicates with the Blockchain server through TCP sockets using JSON messages.

**Topics/Skills covered:**
- Blockchain fundamentals

**Demonstration of completed tasks:**

This is the raw output of the client console. This will be the same for the local blockchain and the client-side implementation in the second task. Note that the chain automatically generates the first block with a difficulty of two, as shown below:


>0. View basic blockchain status.
>1. Add a transaction to the blockchain.
>2. Verify the blockchain.
>3. View the blockchain.
>4. Corrupt the chain.
>5. Hide the corruption by repairing the chain.
>6. Exit client.
>
>0
>
>Current Size of chain: 1
>
>Difficulty of most recent block: 2
>
>Total difficulty for all blocks: 2
>
>Approximate hashes per second on this machine: 2074
>
>Expected total hashes required for the whole chain: 256.0
>
>Nonce for the most recent block: 84
>
>Chain hash: 003eae6552b035d4d63551fb9bc0d64b282aa2ed226bc6a6045a66674544adf6

Here is an example of this same command once some transactions have been added to the blockchain:

>0
>
>Current Size of chain: 5
>
>Difficulty of most recent block: 4
>
>Total difficulty for all blocks: 12
>
>Approximate hashes per second on this machine: 2074
>
>Expected total hashes required for the whole chain: 66560.0
>
>Nonce for the most recent block: 8640
>
>Chain hash: 0000f8d83eadf711912d7518ee04258c14475ada7c2a6ddcd80c28efaa3e1e7b

This is an example of a transaction being added to the chain:

>1
>
>Enter difficulty greater than 0 
>
>2
>
>Enter transaction
>
>Alice pays Bob 100 DS Coin
>
>Total execution time was 7.0 milliseconds

The user can check the status of the chain with the following input. This will check for proof of work errors.

>2
>
>"Chain verification: TRUE"
>
>Total execution time was 20.0 milliseconds

The logic for this is as follows, taken from BlockChain.java:

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
        
        
"chainHash" represents the hash of the entire chain. In the case of an empty blockchain besides the genesis block, the hash of the genesis block is expected to equal the chain hash. Also checks for proof of work errors. If a block is modified somehow, the hash checks and proof of work checks will no longer pass and an error will be identified. This will be demonstrated below.

First we will view the chain as it exists:

>3
>
>View the blockchain: "{"ds_chain":"["{"index":0,"time stamp":"2022-03-18 16:45:11.353","tx":"Genesis","PrevHash":"","nonce":223,"difficulty":2}","{"index":1,"time stamp":"2022-03-18 16:45:32.71",**"tx":""Alice pays Bob 100 DS Coin""**,"PrevHash":"00746094b8b774a14cbaea3380c1f3a1a2ee86d65c500c7e4dbcc56650e73c0a","nonce":42,"difficulty":2}","{"index":2,"time stamp":"2022-03-18 16:45:46.593","tx":""Bob pays Carol 50 DSCoin"","PrevHash":"00a28aa6ed2ec72267a185aea83262dbb154789dd1815e59fd37b0bc671a221f","nonce":502,"difficulty":2}","{"index":3,"time stamp":"2022-03-18 16:46:02.986","tx":""Carol pays Andy 10 DS Coin"","PrevHash":"0052c2a7c2409edd07191b3d3eb23f34e475686ba1f08370fa8a16d7bfa4a27e","nonce":185,"difficulty":2}"]","chainHash":"00419e7c89c483ff46403031a37b2f269ddffa7cd7f4aa024ea103b9c196892b"}"

Next, we intentionally corrupt the chain as follows (changing the payment of 100 DS Coins from Alice to Bob to 76 DS Coins):

>4
>
>Corrupt the chain
>
>Enter block ID of block to corrupt
>
>1
>
>Enter new data for block
>
>Alice pays Bob 76 DS Coin
>
>Block 1 now contains ""Alice pays Bob 76 DS Coin""

Now we will view the corrupted block on the chain:
>3
>
>View the blockchain: "{"ds_chain":"["{"index":0,"time stamp":"2022-03-18 16:45:11.353","tx":"Genesis","PrevHash":"","nonce":223,"difficulty":2}","{"index":1,"time stamp":"2022-03-18 16:45:32.71",**"tx":""Alice pays Bob 76 DS Coin""**,"PrevHash":"00746094b8b774a14cbaea3380c1f3a1a2ee86d65c500c7e4dbcc56650e73c0a","nonce":42,"difficulty":2}","{"index":2,"time stamp":"2022-03-18 16:45:46.593","tx":""Bob pays Carol 50 DSCoin"","PrevHash":"00a28aa6ed2ec72267a185aea83262dbb154789dd1815e59fd37b0bc671a221f","nonce":502,"difficulty":2}","{"index":3,"time stamp":"2022-03-18 16:46:02.986","tx":""Carol pays Andy 10 DS Coin"","PrevHash":"0052c2a7c2409edd07191b3d3eb23f34e475686ba1f08370fa8a16d7bfa4a27e","nonce":185,"difficulty":2}"]","chainHash":"00419e7c89c483ff46403031a37b2f269ddffa7cd7f4aa024ea103b9c196892b"}"

Now when we validate the chain, we get the following error:

>2
>
>"Block 1 proof of work error"
>Total execution time was 5.0 milliseconds

**Raw output of client side console output:**

>---The server is running.---
>
>Blockchain server running
>
>We have a visitor
>
>Operation Requested: 0
>
>
>Response: 
>>{"operation":0,"size":1,"difficulty":2,"totalDifficulty":2.0,"hashesPerSecond":1998,"totalExpectedHashes":256.0,"nonce":203,"hash":"00106cb1f4554be7b5845b7ce41c026a852c5db1054d2729fdf474187e90f55c"}
>Blockchain server running
>
>We have a visitor
>
>Operation Requested: 1
>
>Adding a block
>
>Total execution time was 46.0 milliseconds
>
>Response: {"operation":1,"elapsedTime":46.0}
>
>Blockchain server running
>
>We have a visitor
>
>Operation Requested: 1
>
>Adding a block
>
>Total execution time was 27.0 milliseconds
>
>Response: {"operation":1,"elapsedTime":27.0}
>
>Blockchain server running
>
>We have a visitor
>
>Operation Requested: 1
>
>Adding a block
>
>Total execution time was 17.0 milliseconds
>
>Response: {"operation":1,"elapsedTime":17.0}
>
>Blockchain server running
>
>We have a visitor
>
>Operation Requested: 2
>
>Verifying entire chain
>
>Total execution time was 13.0 milliseconds
>
>Response: {"operation":2,"elapsedTime":13.0,"verification":"Chain verification: TRUE"}
>
>Blockchain server running
>
>We have a visitor
>
>Operation Requested: 3
>
>View the blockchain
>
>Response: {"operation":3,"wholeChain":"{\"ds_chain\":\"[\"{\"index\":0,\"time stamp\":\"2022-03-18 16:49:13.793\",\"tx\":\"Genesis\",\"PrevHash\":\"\",\"nonce\":203,\"difficulty\":2}\",\"{\"index\":1,\"time stamp\":\"2022-03-18 16:49:43.833\",\"tx\":\"\"Alice pays Bob 100 DS Coin\"\",\"PrevHash\":\"00106cb1f4554be7b5845b7ce41c026a852c5db1054d2729fdf474187e90f55c\",\"nonce\":404,\"difficulty\":2}\",\"{\"index\":2,\"time stamp\":\"2022-03-18 16:49:56.065\",\"tx\":\"\"Bob pays Carol 50 DS Coin\"\",\"PrevHash\":\"003381e3637be706caea49b1732f33c94e8c0b7c5243adb06e594c00bb1133a2\",\"nonce\":294,\"difficulty\":2}\",\"{\"index\":3,\"time stamp\":\"2022-03-18 16:50:10.409\",\"tx\":\"\"Carol pays Andy 10 DS Coin\"\",\"PrevHash\":\"00c187c2b8bbd5339a456beaf3839329d6344d859c740182abfd8f8c2889759e\",\"nonce\":115,\"difficulty\":2}\"]\",\"chainHash\":\"0072a7829ba10f9e7be9bbf130ffd16e7dd6d6b345ac77457e905012ed5fc80f\"}"}
>
>Blockchain server running
>
>We have a visitor
>
>Operation Requested: 4
>
>Corrupt the blockchain
>
>Block 1 now contains "Alice pays Bob 76 DS Coin"
>
>Response: {"index":1,"tx":"\"Alice pays Bob 76 DS Coin\""}
>
>Blockchain server running


