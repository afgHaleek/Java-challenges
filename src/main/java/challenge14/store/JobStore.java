package challenge14.store;

import challenge14.model.Job;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JobStore {


    private final Path filePath;

    public JobStore(Path filePath) {
        this.filePath = filePath;
    }

    public void save(List<Job> jobs) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) Files.createDirectories(parent);

            try (ObjectOutputStream out =
                    new ObjectOutputStream(
                            new BufferedOutputStream(
                                    Files.newOutputStream(filePath)
                            )
                    )) {
                out.writeObject(jobs);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to save jobs to file: " + filePath, e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Job> load() {
        if (!Files.exists(filePath)) {
            return List.of();
        }

        try (ObjectInputStream in =
                new ObjectInputStream(
                        new BufferedInputStream(
                                Files.newInputStream(filePath)
                        )
                )) {
            Object object =  in.readObject();

            if (object == null) {
                return List.of();
            }

            return (List<Job>) object;

        } catch (EOFException e) {
            return List.of();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load jobs from file: " + filePath, e);
        }
    }

    public void clear() {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException(" failed to clear job store file ", e);
        }
    }
}
