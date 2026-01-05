# Challenge 11 — TCP Broadcast Chat Room (Java Sockets)

This challenge extends the basic TCP client/server from Challenge 10 into a small **multi-client broadcast chat room**.

The focus is on learning:
- TCP networking basics
- multi-client server design
- thread pools (`ExecutorService`)
- shared state + thread-safe collections
- simple text protocol design

---

## What it Does

### ✅ Multi-client chat server (TCP)
- Server listens on `localhost:9090`
- Accepts multiple clients at the same time
- Uses a fixed thread pool (`Executors.newFixedThreadPool(10)`)
- Tracks connected client sockets in a thread-safe set
- Tracks usernames in a thread-safe map (`ConcurrentHashMap`)
- Graceful shutdown (CTRL+C):
    - stops accepting connections
    - closes all active client sockets
    - shuts down the thread pool

### ✅ Real-time client (non-blocking display)
- Client uses **two threads**:
    - one thread reads your keyboard input and sends to server
    - another thread continuously reads server messages and prints immediately
- This allows the client to receive broadcast messages even when the user is not typing.

---

## Supported Commands

### `NAME <username>`
Registers your username in the chat room.

Rules:
- Username is required (cannot be blank)
- **Duplicate usernames are rejected** (case-insensitive)

Example:
```text
NAME bob

Server responses:
Name registered as bob
or ERROR Username already taken. Try another one.

MSG <message>
Broadcasts a message to all connected clients.

Rules:
You must set NAME before using MSG

Behavior:
Sender sees: [ME]: <message>
Other users see: [username]: <message>

Example:
MSG hello everyone

WHO
Lists online users.

Example:
WHO

Response:
Online users: mohammad, ali, john

TIME
Returns server time.

Example:
TIME

HELLO <name>
Simple command (kept from Challenge 10).

Example:
HELLO John

QUIT
Disconnects cleanly from the server.

Example:
QUIT

Chat Room Events:

Join notification
When a user registers a name:
*** ali joined the chat ***

Leave notification
When a user disconnects (QUIT or closes client):
*** ali left the chat ***

Tests:

Unit tests cover the command parsing logic in CommandProcessorV2.
Networking flow (client/server interaction) is tested manually by running multiple clients.

Run tests:

mvn test

How to Run
1) Start the server
From IntelliJ: run
challenge11.server.TcpChatServerV2

Or terminal:
mvn -q package
java -cp target/classes challenge11.server.TcpChatServerV2

2) Start one or more clients (in separate terminals)
java -cp target/classes challenge11.client.TcpChatClientV2


Key Concepts Practiced:

ServerSocket / Socket
Streams: InputStream, OutputStream, BufferedReader, PrintWriter
Multi-client design with ClientHandler
Thread pooling with ExecutorService
Thread-safe shared state:
ConcurrentHashMap.newKeySet()
ConcurrentHashMap<Socket, String>
Real-time client output using a dedicated server-reader thread
Protocol parsing separated from networking I/O (testable design)
