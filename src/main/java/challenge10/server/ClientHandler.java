package challenge10.server;

import challenge10.protocol.CommandProcessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class ClientHandler implements Runnable{

    private final Socket clientSocket;
    private final CommandProcessor commandProcessor;
    private final Set<Socket> clients;

    public ClientHandler(Socket clientSocket, CommandProcessor commandProcessor, Set<Socket> clients) {
        this.clientSocket = clientSocket;
        this.commandProcessor = commandProcessor;
        this.clients = clients;
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

                out.println(response);

                if (line.trim().equalsIgnoreCase("QUIT")) {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Handler error for client " + clientId + ": " + e.getMessage());
        } finally {
            clients.remove(clientSocket);
            try {
                clientSocket.close();
            } catch (IOException ignored) {}

            System.out.println("Client disconnected: " + clientId + " | active clients: " + clients.size());
        }
    }
}
