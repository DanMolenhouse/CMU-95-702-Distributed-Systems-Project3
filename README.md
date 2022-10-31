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

This is the raw output of the client console. This will be the same for the local blockchain and the client-server implementation. Note that the chain automatically generates the first block with a difficulty of two, as shown below:

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


