# trieConcurrentHashMap
Key/Value store implemented with trie and Suffix tree concept using hashcode() similar to ConcurrentHashMap. This is on the intention that this trie won't require rehashing, very huge concurrency level and very low contention. I am considering this as an alternative to ConcurrentHashMap in Java.

Read the problem statement and solution here <BR>
http://codereview.stackexchange.com/questions/112535/trie-key-value-store-implementation-comparing-with-hashmap

The idea behind this is create unique path for hashcode (using trie methodalogy) by converting the hashcode into binary and pick group of bits for each level. So every unique hashcode will have its own unique path to reach the key/value pair.
