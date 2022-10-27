# Distributed-Systems-Project3
**Project 3 - Distributed Systems**

**Project Objective:**


**Tasks:**

0. Build a standalone blockchain that allows the user to:
  a. View basic blockchain status. This includes:
      - Number of transactions on the chain
      - Difficulty of the most recent block
      - Total difficulty for all blocks
      - Hashes per second of the current machine
      - Expected hashes for whole chain
      - Nonce for the most recent block
      - Hash of the whole chain
      
  b. Add a transaction to the chain.
      - For the purposes of this simple blockchain, a transaction is simply a string that says something like "Bob pays Joe 50 Coins". The user also chooses a difficulty in this step, which is the number of leading 0's in the hash of the block. 
      
  c. Verify the blockchain.
  d. Corrupt the chain.
  e. Repair the chain and eliminate corruption.
  
1. Create a distributed system where a remote client interacts with a blockchain API

**Topics/Skills covered:**


**Demonstration of completed tasks:**
**_ Task 0 execution, raw client console output demonstrating interaction with the blobkchain: _**

/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63018:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/danmolenhouse/Project3Task0/out/production/Project3Task0:/Users/danmolenhouse/Project3Task0/gson-2.8.2.jar BlockChain
0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
0
Current Size of chain: 1
Difficulty of most recent block: 2
Total difficulty for all blocks: 2
Approximate hashes per second on this machine: 2074
Expected total hashes required for the whole chain: 256.0
Nonce for the most recent block: 84
Chain hash: 003eae6552b035d4d63551fb9bc0d64b282aa2ed226bc6a6045a66674544adf6

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
1
Enter difficulty > 0 
2
Enter transaction
Alice pays Bob 100DSCoin
Total execution time was 14845.0 milliseconds

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
1
Enter difficulty > 0 
2
Enter transaction
Bob pays Carol 50 DS Coin
Total execution time was 14082.0 milliseconds

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
1
Enter difficulty > 0 
2
Enter transaction
Carol pays Alice 10 DSCoin
Total execution time was 23547.0 milliseconds

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
2
Chain verification: TRUE
Total execution time was 14.0 milliseconds

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
3
View the blockchain: {"ds_chain":"["{"index":0,"time stamp":"2022-03-18 16:10:22.824","tx":"Genesis","PrevHash":"","nonce":84,"difficulty":2}","{"index":1,"time stamp":"2022-03-18 16:10:43.641","tx":"Alice pays Bob 100DSCoin","PrevHash":"003eae6552b035d4d63551fb9bc0d64b282aa2ed226bc6a6045a66674544adf6","nonce":46,"difficulty":2}","{"index":2,"time stamp":"2022-03-18 16:11:02.613","tx":"Bob pays Carol 50 DS Coin","PrevHash":"00f1e524b277f727d9e4e7882d79a4ea158beb880433bd597bff110ddf335bd1","nonce":632,"difficulty":2}","{"index":3,"time stamp":"2022-03-18 16:11:33.499","tx":"Carol pays Alice 10 DSCoin","PrevHash":"00808c883df41feb79b02d47011ec2625454b771e9403708d0c8ff23cc96a87a","nonce":269,"difficulty":2}"]","chainHash":"00f6212848ffd996b51060b96ac9eae0c10aff45d914a9c52ae5b73faf32e68b"}
0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
4
Corrupt the chain
Enter block ID of block to corrupt
1
Enter new data for block
Alice pays Bob 76 DSCoin
Block 1 now contains Alice pays Bob 76 DSCoin
0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
3
View the blockchain: {"ds_chain":"["{"index":0,"time stamp":"2022-03-18 16:10:22.824","tx":"Genesis","PrevHash":"","nonce":84,"difficulty":2}","{"index":1,"time stamp":"2022-03-18 16:10:43.641","tx":"Alice pays Bob 76 DSCoin","PrevHash":"003eae6552b035d4d63551fb9bc0d64b282aa2ed226bc6a6045a66674544adf6","nonce":46,"difficulty":2}","{"index":2,"time stamp":"2022-03-18 16:11:02.613","tx":"Bob pays Carol 50 DS Coin","PrevHash":"00f1e524b277f727d9e4e7882d79a4ea158beb880433bd597bff110ddf335bd1","nonce":632,"difficulty":2}","{"index":3,"time stamp":"2022-03-18 16:11:33.499","tx":"Carol pays Alice 10 DSCoin","PrevHash":"00808c883df41feb79b02d47011ec2625454b771e9403708d0c8ff23cc96a87a","nonce":269,"difficulty":2}"]","chainHash":"00f6212848ffd996b51060b96ac9eae0c10aff45d914a9c52ae5b73faf32e68b"}
0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
2
Block 1 proof of work error
Total execution time was 3.0 milliseconds

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
5
Total execution time was 6.0 milliseconds

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
2
Chain verification: TRUE
Total execution time was 0.0 milliseconds

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
1
Enter difficulty > 0 
4
Enter transaction
Anndy pays Sean 25 DSCoin
Total execution time was 46099.0 milliseconds

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
0
Current Size of chain: 5
Difficulty of most recent block: 4
Total difficulty for all blocks: 12
Approximate hashes per second on this machine: 2074
Expected total hashes required for the whole chain: 66560.0
Nonce for the most recent block: 8640
Chain hash: 0000f8d83eadf711912d7518ee04258c14475ada7c2a6ddcd80c28efaa3e1e7b

0. View basic blockchain status.
1. Add a transaction to the blockchain.
2. Verify the blockchain.
3. View the blockchain.
4. Corrupt the chain.
5. Hide the corruption by repairing the chain.
6. Exit client.
6
Exiting.


Process finished with exit code 0


