# Challenge 10 — Java Networking (TCP Client/Server)

## Goal
Build a small TCP client/server application in pure Java to learn how network communication works.

The server accepts multiple clients, processes text-based commands, and sends responses back.  
Command parsing is separated into a standalone component that is fully unit tested.

---

## What I Built

### 1) TCP Server
- Listens on port **9090**
- Accepts multiple client connections
- Uses a **fixed thread pool** (`ExecutorService`) to handle clients safely
- Tracks active client sockets in a thread-safe set
- Supports graceful shutdown (CTRL+C):
    - stops accepting new clients
    - closes all connected client sockets
    - shuts down the thread pool cleanly

### 2) TCP Client
- Connects to `localhost:9090`
- Reads commands from the terminal
- Sends commands to the server
- Prints server responses
- Handles server disconnect gracefully

### 3) Command Protocol (CommandProcessor)
The server understands these commands:

- `HELLO <name>` → `WELCOME <name>!`
- `ECHO <message>` → echoes the message
- `TIME` → returns the server time
- `QUIT` → `Goodbye!` and closes the connection
- Unknown commands → `ERROR Unknown command -> <COMMAND>`

---

## Why This Design
- Socket code is kept minimal (I/O only)
- All parsing rules live in `CommandProcessor` (pure logic)
- The processor is easy to unit test with JUnit
- The server uses a thread pool to avoid unlimited thread creation

---

## How to Run

### 1) Run the server
From IntelliJ: run `challenge10.server.TcpChatServer`

Or from terminal:

```bash
mvn -q package
java -cp target/classes challenge10.server.TcpChatServer
