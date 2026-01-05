package challenge11.protocol;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandProcessorV2 {

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

                //FOR CHALLENGE 11
                case "NAME" -> {
                    if (parts.length < 2 || parts[1].isBlank()) {
                        return "ERROR Usage: NAME <username>";
                    }
                    return "OK NAME " + parts[1].trim();
                }

                case "MSG" -> {
                    if (parts.length < 2 || parts[1].isBlank()) {
                        return "ERROR Usage: MSG <message>";
                    }
                    return "BROADCAST " + parts[1];
                }

                case "WHO" -> {
                    return "WHO";
                }

                //END OF COMMANDS FOR CHALLENGE 11

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
