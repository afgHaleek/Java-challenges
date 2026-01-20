## ** Challenge 14 – Concurrent Job Processing System (Java) **

### ** Overview **
#### This challenge implements a concurrent job processing system using core Java.
The system demonstrates how real backend services manage asynchronous work, thread coordination, graceful shutdown, and recovery after restart.

##### It is designed to be simple, yet architecturally realistic.

##### ** Key Features:  **
* Producer–Consumer architecture using a bounded BlockingQueue
* Thread pool execution via ExecutorService
* Graceful shutdown using a poison-pill termination strategy
* Job persistence using Java serialization
* Automatic recovery of pending jobs after restart
* Thread-safe design with no race conditions

##### ** Architecture: **
* Job
     * Immutable, serializable unit of work
* JobQueue
     * Thread-safe blocking queue for job coordination
* JobProducer
     * Generates jobs and submits them to the queue
* JobConsumer
     * Processes jobs concurrently and exits cleanly on poison pill
* JobStore
     * Persists pending jobs to disk and restores them on startup
* JobSystemApp
     * Wires all components together and manages lifecycle

##### ** Graceful Shutdown Design: **
_###### The system uses a poison pill approach to stop workers safely:_

* One poison job is submitted per consumer
* Consumers finish current work before exiting
* Remaining jobs are persisted before shutdown
* System restarts without losing pending jobs
_###### This approach avoids abrupt thread termination and ensures data safety._

##### ** Technologies Used **
* Java 21
* ExecutorService
* BlockingQueue
* Java Serialization
* JUnit 5
* Maven