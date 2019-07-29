# KVStore

KVStore is a Key-Value Store based on B+Tree for Memory & Disk (for on disk, keys and values must be fixed length) for Java. Project is Open source (Apache License, Version 2.0) 

API is similar to [TreeMap](http://docs.oracle.com/javase/6/docs/api/java/util/TreeMap.html).

### Current Stable Version is [1.0.1](https://search.maven.org/#search|ga|1|g%3Aorg.javastack%20a%3Akvstore)

---

## DOC

#### Usage Example

```java
import java.util.Iterator;
import org.javastack.kvstore.KVStoreFactory;
import org.javastack.kvstore.Options;
import org.javastack.kvstore.holders.IntHolder;
import org.javastack.kvstore.structures.btree.BplusTree.TreeEntry;
import org.javastack.kvstore.structures.btree.BplusTreeFile;

public class Example {
	private static final String btreeFile = "/tmp/test";

	//
	public static void main(final String[] args) throws Exception {
		final int[] keys = new int[] { 5, 7, -11, 111, 0 };
		//
		KVStoreFactory<IntHolder, IntHolder> factory = new KVStoreFactory<IntHolder, IntHolder>(
				IntHolder.class, IntHolder.class);
		Options opts = factory.createTreeOptionsDefault()
				.set(KVStoreFactory.FILENAME, btreeFile);
		BplusTreeFile<IntHolder, IntHolder> tree = factory.createTreeFile(opts);
		//
		// Open & Recovery tree if needed
		try {
			if (tree.open())
				System.out.println("open tree ok");
		} catch (InvalidDataException e) {
			System.out.println("open tree error, recovery needed");
			if (tree.recovery(false) && tree.open()) {
				System.out.println("recovery ok, tree opened");
			} else {
				throw new Exception("Fatal Error Opening Tree");
			}
		}
		// clear all previous content if any
		tree.clear();
		// ============== PUT
		for (int i = 0; i < keys.length; i++) {
			final IntHolder key = IntHolder.valueOf(keys[i]);
			final IntHolder value = IntHolder.valueOf(i);
			tree.put(key, value);
		}
		tree.sync();
		// ============== GET
		System.out.println("tree.get(7)=" + tree.get(IntHolder.valueOf(7)));
		// ============== REMOVE
		tree.remove(IntHolder.valueOf(7));
		// ============== ITERATOR
		for (Iterator<TreeEntry<IntHolder, IntHolder>> i = tree.iterator(); i
				.hasNext();) {
			TreeEntry<IntHolder, IntHolder> e = i.next();
			System.out.println("Key=" + e.getKey() + " Value=" + e.getValue());
		}
		// ============== FIRST / LAST
		System.out.println("tree.firstKey()=" + tree.firstKey());
		System.out.println("tree.lastKey()=" + tree.lastKey());
		//
		tree.sync();
		tree.close();
	}
}
```

##### The Result:

	tree.get(7)=1
	Key=-11 Value=2
	Key=0 Value=4
	Key=5 Value=0
	Key=111 Value=3
	tree.firstKey()=-11
	tree.lastKey()=111


* More examples in [Test package](https://github.com/ggrandes/kvstore/tree/master/src/main/java/org/javastack/kvstore/test)

---

## MAVEN

Add the KVStore dependency to your pom.xml:

    <dependency>
        <groupId>org.javastack</groupId>
        <artifactId>kvstore</artifactId>
        <version>1.0.1</version>
    </dependency>

---

## TODOs

* A lot of Doc
* Describe disk formats
* HashMap on disk
* Separate size of read cache and write cache

## DONEs

* BlockStore (Fixed length chunks)
    * Support for mmaped files
* StreamStore (Variable length chunks)
* B+Tree for Index
    * Buffer reuse
    * Memory mode
    * Persistence on disk (BlockStore)
        * Cache of nodes
        * Reuse free blocks on disk
        * Redo log
        * Recovery system
        * Allow open without populate read cache
        * Allow open readOnly mode
        * File locking (multi-process)
* HashMaps for natives (memory) 
* Holders for data and NIO serialization
    * Fixed length
        * Boolean
        * Byte
        * Short
        * Integer
        * Long
        * Null
    * Variable length
        * Strings
* Create Factory
* Options object for factory
* Use Log4J
* Maven repository


## MISC

---

## Benchmarks

###### Values are not accurate, but orientative. Higher better. All test Running on Laptop { Windows Vista (32bits), Core 2 Duo 1.4Ghz (U9400), 4GB Ram, Magnetic Disk (WDC-WD5000BEVT-22ZAT0) }.

<table>
  <tr>
    <th>Test-1</th>
    <th>Writes/s</th>
    <th>Reads/s</th>
  </tr>
  <tr>
    <th>BlockStore (RAF)</th>
    <td>46k</td>
    <td>58k</td>
  </tr>
  <tr>
    <th>BlockStore (MMAP)</th>
    <td>67k</td>
    <td>182k</td>
  </tr>
  <tr>
    <th>StreamStore</th>
    <td>101k</td>
    <td>55k</td>
  </tr>
</table>

###### Test-1 (org.javastack.kvstore.test.BenchMarkDiskStore): Registry { count=1e6, datalen=256bytes } BlockStore { blockSize=512 (2reg/block), fileSize=250MB } StreamStore { outBufferSize=0x10000, align=true, fileSize=256MB } 

<table>
  <tr>
    <th>Test-2</th>
    <th>Put/s</th>
    <th>Get/s</th>
    <th>Remove/s</th>
  </tr>
  <tr>
    <th>BplusTreeMemory</th>
    <td>457k</td>
    <td>1041k</td>
    <td>324k</td>
  </tr>
  <tr>
    <th>IntHashMap</th>
    <td>1154k</td>
    <td>31250k</td>
    <td>16000k</td>
  </tr>
  <tr>
    <th>IntLinkedHashMap</th>
    <td>1114k</td>
    <td>31250k</td>
    <td>11696k</td>
  </tr>
</table>

###### Test-2 (org.javastack.kvstore.test.BenchMarkMemoryStructures): Registry { count=2e6, datalen=256bytes } BplusTreeMemory { key=Integer, b-order=511 } IntHashMap { initialSize=(2e6 * 2) } 

---
Inspired in [Book: Open Data Structures in Java](http://opendatastructures.org/ods-java/14_2_B_Trees.html), [Perl DB_File](http://search.cpan.org/~pmqs/DB_File-1.827/DB_File.pm), [JDBM3](https://github.com/jankotek/JDBM3) and [H2-Database](http://www.h2database.com/), this code is Java-minimalistic version.
