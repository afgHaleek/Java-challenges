package challenge10.protocol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandProcessor {

    public String processCommand(String inputCommand) {
        if (inputCommand == null) {
            return "ERROR Empty command";
        }

        String line = inputCommand.trim();

        if (line.isEmpty()) {
            return "ERROR Empty command";
        }

        //split into at most two parts: command + reset

        String[] parts = line.split("\\s+", 2);
        String   command = parts[0].toUpperCase();

        switch (command) {
            case "HELLO" -> {
                if (parts.length < 2 || parts[1].isBlank()) {
                    return "ERROR Usage: HELLO <name>";
                }

                return "WELCOME " + parts[1].trim() + "!";
            }

            case "ECHO" -> {
                if (parts.length < 2) {
                    return "ERROR Usage: ECHO <message>";
                }

                return parts[1];
            }

            case "TIME" -> {
                return "TIME " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            case "QUIT" -> {
                return "Goodbye!";
            }

            default -> {
                return "ERROR Unknown command -> " + command;
            }
        }
    }
}
