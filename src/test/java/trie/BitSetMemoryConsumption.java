package trie;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BitSetMemoryConsumption {

    public static void main(String[] args) {

//        memoryUsedByBitSet(20000);
        memoryUsedByBit32Set(20000);

        /*
            memoryUsedByBitSet: used 3,154,512 bytes for size 50000
            memoryUsedByBit32Set: used 1,341,936 bytes for size 50000
            1182 KB
         */
    }

    public static void memoryUsedByBit32Set(int size) {

        List<Bit32Set> list = new ArrayList<>();
        long before = memoryUsed();
        for (int i = 0; i < size; i++) {
            list.add(new Bit32Set());
        }

        long used = memoryUsed() - before;
        if (used == 0) {
            throw new AssertionError("You need to run this with -XX:-UseTLAB for accurate accounting");
        }
        System.out.printf("memoryUsedByTrieMap: used %,d bytes for size %d %n", used, size);

        //        System.out.println(ClassLayout.parseInstance(trieMap).toPrintable());
    }

    public static void memoryUsedByBitSet(int size) {

        List<BitSet> list = new ArrayList<>();
        long before = memoryUsed();
        for (int i = 0; i < size; i++) {
            list.add(new BitSet());
        }

        long used = memoryUsed() - before;
        if (used == 0) {
            throw new AssertionError("You need to run this with -XX:-UseTLAB for accurate accounting");
        }
        System.out.printf("memoryUsedByHashMap: used %,d bytes for size %d %n", used, size);

        //        System.out.println(ClassLayout.parseInstance(hashMap).toPrintable());

    }

    public static long memoryUsed() {

        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }
}
