# Challenge 13 – Thread-Safe LRU Cache (Java) Overview


#### This challenge implements a thread-safe Least Recently Used (LRU) cache using core Java collections and concurrency primitives.
#### An LRU cache automatically removes the least recently accessed item when the cache reaches its maximum capacity. This is a very common real-world requirement in backend systems where memory is limited but fast access is critical.
#### This implementation is intentionally built from scratch using core Java, without external libraries, to deeply understand how caching, collections, and concurrency work under the hood.

### ❓ What Problem Does This Solve?

##### In many real applications, you cannot keep all data in memory forever.

#### Examples:
* Caching database query results
* Caching user sessions
* Caching API responses
* Caching computed values (expensive calculations)
* Limiting memory usage in long-running services

##### Without a cache:
* Performance suffers (repeated expensive operations)
* Memory grows uncontrollably

##### With an LRU cache:
* Most recently used data stays fast
* Old, unused data is automatically evicted
* Memory usage stays bounded

### How This Cache Works

* The cache has a fixed capacity
* Every get() marks an entry as recently used
* When capacity is exceeded:
  * The least recently used entry is removed automatically
* The cache is thread-safe, meaning multiple threads can safely access it

##### _This behavior is implemented using Java’s LinkedHashMap with access-order tracking._

### Key Design Decisions

**1. LinkedHashMap with Access Order**

`new LinkedHashMap<>(capacity, 0.75f, true)`

* Maintains entries ordered by recent access
* Automatically tracks which entry is least recently used

**2. Automatic Eviction Hook**

`   protected boolean removeEldestEntry(Map.Entry<K, V> eldest)
`

* Called by the map after every insertion
* Evicts the oldest entry when size exceeds capacity

**3. Thread Safety**

* All public operations are synchronized
* Prevents race conditions under concurrent access

**4. Defensive API Design**

* snapshot() returns a copy, not the internal map
* Prevents external mutation of internal state


### Features

* Fixed-size cache
* LRU eviction policy
* Thread-safe operations
* Generic key/value support
* Manual removal and clearing
* Snapshot for debugging/testing
* Clean and minimal public API

### Main API
`put(K key, V value)`
`get(K key)`
`remove(K key)`
`clear()`
`size()`
`isEmpty()`
`containsKey(K key)`
`snapshot()`

### Testing

##### The cache is fully covered by JUnit tests:

* Eviction behavior
* Access-order updates
* Thread-safety under concurrent access
* Utility methods (remove, clear, snapshot)
* Edge cases (invalid capacity)

### What This Challenge Teaches

##### By completing this challenge, you learn:

* How Java collections behave internally
* Why LinkedHashMap is perfect for LRU caches
* How eviction hooks work
* How to design thread-safe APIs
* When and why to use synchronization
* How to test concurrent behavior
* How real caches work in backend systems

### Where This Is Used in Real Systems

* Web servers (session caches)
* Microservices (response caching)
* Databases (query result caching)
* Authentication systems
* In-memory data grids
* Rate limiting systems
* This same pattern appears in frameworks like:
* Spring Cache
* Redis clients
* Guava Cache
* Caffeine Cache

### Running all tests:

`bash
mvn test`