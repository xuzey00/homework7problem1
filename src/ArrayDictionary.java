public class ArrayDictionary implements Dictionary {
    private int capacity;
    private int count;
    private KVEntry[] entries;

    public ArrayDictionary(int capacity) {
        this.capacity = capacity;
        entries = new KVEntry[capacity];
    }

    private int hashFunction(int key) {
        return key % capacity;
    }

    @Override
    public boolean isEmpty() {
        return count==0;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean add(int key, int value) {

        int hashedKey = hashFunction(key);

        // when there's no entry yet
        if (entries[hashedKey] == null) {
            if (count == capacity) {
                return false;
            }
            entries[hashedKey] = new KVEntry(key, value);
            count++;
            return true;
        }

        KVEntry ptr = entries[hashedKey];
        KVEntry pNewNode = null;
        while (ptr != null) {
            // update value if key already exists
            if (ptr.key == key) {
                ptr.value = value;
                return true;
            }
            pNewNode = ptr;
            ptr = ptr.next;
        }

        // add an entry to the end of the chain
        pNewNode.next = new KVEntry(key, value);
        return true;
    }

    // Delete the entry with the given key from the dictionary
    // Return true if an entry is deleted, false otherwise
    @Override
    public boolean remove(int key) {
        // homework
        int hashedKey = hashFunction(key);
        if(getCount()==0){
            return false;
        }
        if (entries[hashedKey]!=null && entries[hashedKey].next == null){
                entries[hashedKey] = null;
                count--;
                return true;
        }

        KVEntry ptr = entries[hashedKey];
        KVEntry ptr2;
        while(ptr != null){
            if(ptr.next.key == key){
                ptr2 = ptr.next.next;
                ptr.next = null;
                ptr.next = ptr2;
                count--;
                return true;
            }
            ptr = ptr.next;
        }
        return false;
    }

    // Return true when the dictionary contains an entry
    // with the key
    @Override
    public boolean contains(int key) {
        // homework
        if(!isEmpty()) {
            int hashedKey = hashFunction(key);
            //System.out.println(hashedKey);
            return entries[hashedKey] != null;
        }
        return false;
    }

    // Return the entry value with the given key
    // Return null if no entry exists with the given key
    @Override
    public Integer get(int key) {
        // NOT IMPLEMENTED
        int hashedKey = hashFunction(key);
        if (entries[hashedKey]!=null && entries[hashedKey].next == null){
            return entries[hashedKey].value;
        }
        KVEntry ptr = entries[hashedKey];
        while(ptr != null){
            if(ptr.key == key){
                return ptr.value;
            }
            ptr = ptr.next;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < capacity; i++) {
            if (entries[i] == null) {
                builder.append("dictionary["+ i + "] = null\n");
                continue;
            }
            KVEntry ptr = entries[i];
            builder.append("dictionary["+i+"] = {");
            while (ptr != null) {
                builder.append("(" + ptr.key + ", " + ptr.value + ")");
                ptr = ptr.next;
            }
            builder.append("}\n");
        }
        return builder.toString();
    }
}
