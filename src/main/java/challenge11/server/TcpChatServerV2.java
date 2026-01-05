package challenge11.server;

import challenge11.protocol.CommandProcessorV2;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TcpChatServerV2 {

    private static volatile boolean running = true;

    public static void main(String... args) {
        int port = 9090;
        CommandProcessorV2 processor = new CommandProcessorV2();

        ExecutorService pool = Executors.newFixedThreadPool(10);

        Set<Socket> clients = ConcurrentHashMap.newKeySet();
        Map<Socket, String> usernames = new ConcurrentHashMap<>();

        System.out.println("Server starting on port " + port + "...");

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\nShutdown requested... stopping server.");
                running = false;
                try {
                    serverSocket.close();
                } catch (IOException ignored) {
                }

                //close all active clients sockets
                for (Socket s : clients) {
                    try {
                        s.close();
                    } catch (IOException ignored) {}
                }

                pool.shutdown();
                try {
                    if (!pool.awaitTermination(3, TimeUnit.SECONDS)) {
                        pool.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    pool.shutdownNow();
                    Thread.currentThread().interrupt();
                }

                System.out.println("server shutdown complete.");
            }));

            System.out.println("Server is listening... Press CTRL+C to stop.");


            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    clients.add(clientSocket);
                    System.out.println("client connected: " + clientSocket.getRemoteSocketAddress());

                    pool.submit(new ClientHandlerV2(clientSocket, processor, clients, usernames));
                } catch (IOException e) {
                    if (running) {
                        System.err.println("Error accepting client connection: " + e.getMessage());
                    }
                }
            }

            System.out.println("Server stopped.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}