# **********************************************************Java Challenges Repository**********************************************************

🎯 

### ****Purpose & Vision****

#### _This repository is a progressive collection of pure Java challenges designed to deeply understand core Java concepts by building real, working systems — not just solving small syntax problems._

****The goal of this repository is to:****
1. [ ] Master Java fundamentals and advanced core concepts
2. [ ] Learn Java the way it is used under the hood in real systems
3. [ ] Practice clean design, testable code, and professional structure
4. [ ] Build confidence before moving to frameworks like Spring
5. [ ] All challenges are implemented using plain Java (no frameworks) and are written incrementally, where each challenge builds on concepts learned in previous ones.

This repository is intentionally framework-free so the focus stays on:
___How Java actually works___

### ****Learning Philosophy****
1. [ ] Instead of jumping directly into frameworks, this repo focuses on:
2. [ ] Understanding why things work, not just how
3. [ ] Separating logic from I/O
4. [ ] Writing testable, maintainable code
5. [ ] Learning through real-world scenarios (authentication, networking, concurrency, etc.)

#### ****Each challenge:****
1. [ ] Solves a concrete problem
2. [ ] Introduces new Java concepts step by step
3. [ ] Includes tests where applicable
4. [ ] Is independent and runnable on its own

### Technologies & Tools
1. [ ] Java 21
2. [ ] JUnit 5 — unit testing
3. [ ] Maven — build & dependency management
4. [ ] Standard Java APIs only (no Spring, no external frameworks)

### **Project Structure**

1. [ ] java-challenges/
2. [ ] │
3. [ ] ├─ src/main/java/
4. [ ] │   ├─ challenge00/   // Basic Java & syntax
5. [ ] │   ├─ challenge01/
6. [ ] │   ├─ challenge02/
7. [ ] │   ├─ challenge03/
8. [ ] │   ├─ challenge04/
9. [ ] │   ├─ challenge05/
10. [ ] │   ├─ challenge06/   // Validation & custom exceptions
11. [ ] │   ├─ challenge07/   // User registration & repositories
12. [ ] │   ├─ challenge08/   // Authentication & account locking
13. [ ] │   ├─ challenge09/   // OTP & two-factor authentication
14. [ ] │   ├─ challenge10/   // TCP client/server networking
15. [ ] │   ├─ challenge11/   // Multi-client broadcast chat server
16. [ ] │      |-- challenge12/ // In-memory job processing system
17. [ ] └─ src/test/java/
18. [ ] └─ ...            // JUnit tests per challenge

##### _Each challenge lives in its own package and can be understood independently._

### **Topics Covered So Far**
1. [ ] Core Java & OOP:
2. [ ] Classes, records, immutability
3. [ ] Interfaces and implementations
4. [ ] Encapsulation & separation of concerns
5. [ ] Custom exceptions
6. [ ] Defensive programming

### **Testing**:
1. [ ] JUnit 5
2. [ ] Testing business logic in isolation
3. [ ] Designing code to be testable

### **Data & Design:**
1. [ ] In-memory repositories
2. [ ] Maps, lists, sets
3. [ ] Dependency injection (manual, no frameworks)

### **Authentication & Security Concepts:**
1. [ ] Password hashing
2. [ ] Login attempt tracking
3. [ ] Account locking & unlocking
4. [ ] Time-based logic with testable clocks
5. [ ] OTP (one-time passwords)
6. [ ] Two-factor authentication flows

### **Networking (TCP):**
1. [ ] ServerSocket and Socket
2. [ ] Blocking I/O streams
3. [ ] Multi-client servers
4. [ ] Thread pools (ExecutorService)
5. [ ] Graceful shutdown
6. [ ] Real-time broadcast messaging
7. [ ] Client identity management
8. [ ] Simple text-based protocols

### **How to Run**
1. Clone the repository
   git clone https://github.com/afgHaleek/java-challenges.git
   cd java-challenges

2. Compile the project
   mvn compile

3. Run a specific challenge
   mvn exec:java -Dexec.mainClass="challenge01.SumTwoNumbers"

4. Replace the class name with any runnable challenge (e.g. networking servers, clients).

#### **Run all tests**
mvn test

### **What’s Coming Next:**
#### This repository will continue to grow with challenges focused on:

##### **Java Collections & Concurrency**

1. [ ] Deep dive into List, Set, Map
2. [ ] Thread safety & race conditions
3. [ ] Executors, synchronization, concurrent collections

##### **I/O & NIO**

1. [ ] Files & streams
2. [ ] Buffer-based I/O
3. [ ] Introduction to non-blocking I/O concepts

##### **Advanced Networking**

1. [ ] UDP
2. [ ] Timeouts & heartbeats
3. [ ] Protocol design
4. [ ] Scalability considerations

##### **Serialization**

1. [ ] Java serialization
2. [ ] Object persistence concepts
3. [ ] Data transfer formats

##### **JVM Basics**

1. [ ] Memory model
2. [ ] Stack vs heap
3. [ ] Garbage collection concepts
4. [ ] How Java runs code internally

#### __And Many More Java Topics...__

#### After mastering these Topics, a separate repository will focus on Spring & enterprise Java.

### **Contact**

1. [ ] Daoud Shagiwal
2. [ ] Email: daoud.shagiwal@yahoo.com
3. [ ] GitHub: https://github.com/afgHaleek