package io.collective.basic;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Blockchain {
    private final ArrayList<Block> blockchain;

    public Blockchain() {
        this.blockchain = new ArrayList<Block>();
    }

    public boolean isEmpty() {
        return blockchain.isEmpty();
    }

    public void add(Block block) {
        blockchain.add(block);
    }

    public int size() {
        return blockchain.size();
    }

    public boolean isValid() throws NoSuchAlgorithmException {
        String prevHash = "0";

         for (Block block: blockchain) {
             // check mined
             if (!isMined(block)) {
                 return false;
             }
             // check previous hash matches
             if (!block.getPreviousHash().equals(prevHash)) {
                 return false;
             }
             prevHash = block.getHash();

             //check hash is correctly calculated
             if (!block.calculatedHash().equals(block.getHash())) {
                 return false;
             }
         }
        return true;
    }

    /// Supporting functions that you'll need.

    public static Block mine(Block block) throws NoSuchAlgorithmException {
        Block mined = new Block(block.getPreviousHash(), block.getTimestamp(), block.getNonce());

        while (!isMined(mined)) {
            mined = new Block(mined.getPreviousHash(), mined.getTimestamp(), mined.getNonce() + 1);
        }
        return mined;
    }

    public static boolean isMined(Block minedBlock) {
        return minedBlock.getHash().startsWith("00");
    }
}