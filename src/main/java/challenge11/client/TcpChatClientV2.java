package challenge11.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpChatClientV2 {

    public static void main(String... args) {
        int port = 9090;
        String host = "localhost";

        System.out.println("Connecting to server " + host + ":" + port);

        try (
                Socket socket = new Socket(host, port);
                BufferedReader serverIn = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                PrintWriter serverOut = new PrintWriter(
                        socket.getOutputStream(), true);
                BufferedReader userIn = new BufferedReader(
                        new InputStreamReader(System.in)
                )
        ) {

            System.out.println("Connected to server");
            System.out.println("Commands: NAME <user>, MSG <text>, HELLO <name>, TIME(current time), QUIT(exit), WHO(see online users)");

            //thread B: always  read from server and print immediately
            Thread serverReader = getServerReader(serverIn);
            serverReader.start();

            //Thread A: read user input and send to server
            String userInput;
            while((userInput = userIn.readLine()) != null) {

                //send user input to server
                serverOut.println(userInput);

                if (serverOut.checkError()) {
                    System.out.println("Failed to send message. Server may be down.");
                    break;
                }

                if (userInput.trim().equalsIgnoreCase("quit")) {
                    System.out.println("Disconnected from server.");
                    break;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Thread getServerReader(BufferedReader serverIn) {
        Thread serverReader = new Thread(() -> {
            try {
                String msgFromServer;
                while ((msgFromServer = serverIn.readLine()) != null) {
                    System.out.println(msgFromServer);
                }
            } catch (Exception ignored) {}
            finally {
                System.out.println("Server disconnected");
            }
        });


        serverReader.setDaemon(true); //allows jvm to exit when main finishes
        return serverReader;
    }
}
