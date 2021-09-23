# trieConcurrentHashMap
Key/Value store implemented with trie and Suffix tree concept using hashcode() similar to ConcurrentHashMap. This is on the intention that this trie won't require rehashing, very huge concurrency level and very low contention. I am considering this as an alternative to ConcurrentHashMap in Java.

Read the problem statement and solution here <BR>
http://codereview.stackexchange.com/questions/112535/trie-key-value-store-implementation-comparing-with-hashmap

The idea behind this is create unique path for hashcode (using trie methodalogy) by converting the hashcode into binary and pick group of bits for each level. So every unique hashcode will have its own unique path to reach the key/value pair.
  
  
TrieMapBenchMark.java
  
Benchmark                          Mode  Cnt      Score      Error  Units
TrieMapBenchMark.insert100000Map   avgt    3   6308.408 ±  484.931  us/op
TrieMapBenchMark.insert100000Trie  avgt    3  10698.976 ± 1085.481  us/op
TrieMapBenchMark.insert1000Map     avgt    3     32.420 ±    1.191  us/op
TrieMapBenchMark.insert1000Trie    avgt    3     52.714 ±   37.215  us/op
TrieMapBenchMark.read100000Map     avgt    3   7720.364 ±  471.905  us/op
TrieMapBenchMark.read100000Trie    avgt    3  12483.032 ±  310.445  us/op
TrieMapBenchMark.read1000Map       avgt    3     24.361 ±    3.241  us/op
TrieMapBenchMark.read1000Trie      avgt    3     34.499 ±    0.903  us/op  
  
  
  
  
TrieMapMemoryConsumption.java:
  
memoryUsedByHashMap: used 2,451,968 bytes for size 50000
memoryUsedByTrieMap: used 1,981,032 bytes for size 50000  
