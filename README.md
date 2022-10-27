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
Task 0, part a execution:

>/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home/bin/java -javaagent:/Applications/IntelliJ IDEA.app/Contents/lib/idea_rt.jar=63018:/Applications/IntelliJ IDEA.app/Contents/bin -Dfile.encoding=UTF-8 -classpath /Users/danmolenhouse/Project3Task0/out/production/Project3Task0:/Users/danmolenhouse/Project3Task0/gson-2.8.2.jar BlockChain
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
>Difficulty of most recent block: 2
>Total difficulty for all blocks: 2
>Approximate hashes per second on this machine: 2074
>Expected total hashes required for the whole chain: 256.0
>Nonce for the most recent block: 84
>Chain hash: 003eae6552b035d4d63551fb9bc0d64b282aa2ed226bc6a6045a66674544adf6


