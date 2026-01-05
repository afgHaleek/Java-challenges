package challenge11.server;

import challenge11.protocol.CommandProcessorV2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class ClientHandlerV2 implements Runnable{

    private final Socket clientSocket;
    private final CommandProcessorV2 commandProcessor;
    private final Set<Socket> clients;
    Map<Socket, String> usernames;

    public ClientHandlerV2(Socket clientSocket, CommandProcessorV2 commandProcessor, Set<Socket> clients, Map<Socket, String> usernames) {
        this.clientSocket = clientSocket;
        this.commandProcessor = commandProcessor;
        this.clients = clients;
        this.usernames = usernames;
    }

    @Override
    public void run() {
        String clientId = clientSocket.getRemoteSocketAddress().toString();

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String line;
            while((line = in.readLine()) != null) {

                String response = commandProcessor.processCommand(line);

                if (response.startsWith("OK NAME ")) {
                    String username = response.substring("OK NAME ".length()).trim();


                    boolean taken = usernames.values().stream()
                                    .anyMatch(u -> u.equalsIgnoreCase(username));
                    if (taken) {
                        out.println("ERROR Username already taken. Try another one.");
                        continue;
                    }

                    usernames.put(clientSocket, username);
                    out.println("Name registered as " + username);

                    broadcastToAll("*** " + username + " joined the chat ***" );
                    continue;
                }

                if (response.startsWith("BROADCAST ")) {
                    if (!usernames.containsKey(clientSocket)) {
                        out.println("ERROR Set your name first using: NAME <username>");
                        continue;
                    }

                    String message = response.substring("BROADCAST ".length());

                    String sender = usernames.getOrDefault(clientSocket, "anonymous");

                    for (Socket client : clients) {
                        if (client.equals(clientSocket)) {
                            sendTo(client, "[ME]: " + message);
                        } else {
                            sendTo(client, "[" + sender + "]: " + message);
                        }

                    }

                    continue;
                }

                if (response.equals("WHO")) {

                    if (!usernames.containsKey(clientSocket)) {
                        out.println("ERROR Set your name first using: NAME <username>");
                        continue;
                    }

                    String me = usernames.get(clientSocket);
                    String users = usernames.values().stream()
                                    .map(u -> u.equals(me) ? u + " (You)" : u)
                                            .reduce((a, b) -> a + ", " + b)
                                                    .orElse("(none)");

                    out.println("Online Users: " + users);
                    continue;
                }

                out.println(response);

                if (line.trim().equalsIgnoreCase("QUIT")) {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Handler error for client " + clientId + ": " + e.getMessage());
        } finally {

                String username = usernames.get(clientSocket);
                if (username != null) {
                    broadcastToAll("*** " + username + " left the chat ***");
                }

            clients.remove(clientSocket);
            usernames.remove(clientSocket);
            try {
                clientSocket.close();
            } catch (IOException ignored) {}

            System.out.println("Client disconnected: " + clientId + " | active clients: " + clients.size());
        }
    }

    private void sendTo(Socket socket, String message) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(message);
        } catch (IOException ignored) {}
    }

    private void broadcastToAll(String message) {
        for (Socket client : clients) {
            sendTo(client, message);
            }
    }
}

