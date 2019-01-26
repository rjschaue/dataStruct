/**
 * Class to create a hash table
 * @author Joey Schauer
 */

public class HashTable {
	/** the array of string values for the table */
    public HashNode[] hashArray;
    /** the max capacity for the table */
    public int maxCapacity;
    /** the size of the table */
    public int size;
    /** the number of probes for the table */
    public int probes;
 
    /** 
     *  Constructor for hash table
     */
    public HashTable(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        hashArray = new HashNode[this.maxCapacity];
        size = 0;
        probes = 0;
    }
    
    /**
     * Method used to look for a given key value in the hash table
     * @param key is the key value to look for
     * @return the found key or null if not found
     */
    public String lookup(String key) {
    	int hashValue = hashFunction(key);
        HashNode node = hashArray[hashValue];
        probes++;
        while (node != null && !node.key.equals(key)) {
            node = node.next;
            probes++;
        }
    	if (node == null) {
            return null;
        } else {
            return node.key;
        }
    }
    
    /**
     * inserts a given key value into the has table
     * @param key is the key to insert into the hash table
     */
    public void insert(String key) {
    	int hashValue = hashFunction(key);
        HashNode node = new HashNode(key);
    	if (hashArray[hashValue] == null) {
            hashArray[hashValue] = node;
        } else {
            node.next = hashArray[hashValue];
            hashArray[hashValue] = node;
        }
        size++;
    }
    
    /**
     * this generates a hash value from the given key
     * @param key is the key to generate a hash value for
     * @return the hash value for the given key
     */
    private int hashFunction(String key) {
    	int hashValue = 0;
    	
    	for (int i = 0; i < key.length(); i++) {
            hashValue = (hashValue << 5) | (hashValue >>> 27);
            hashValue += (int) key.charAt(i);
    	}
        hashValue = Math.abs(hashValue);
    	hashValue %= maxCapacity;
    	
    	return hashValue;
    }
    
    /**
     * returns the size of the hash table
     * @return the size of the hash table
     */
    public int getSize() {
        return size;
    }
    
    /**
     * returns the number of probes for the hash table
     * @return the number of probes for the hash table
     */
    public int getProbes() {
        return probes;
    }
    
    /**
     * the class for nodes in the hash table
     */
    private class HashNode {
    /** the key value for the node */
    public String key;
    /** the next node in the linked list */
    public HashNode next;
    
    /**
     * the constructor for the hash node
     * @param key is the key for the node
     */
    public HashNode(String key) {
        this.key = key;
        next = null;
    }
}
}


