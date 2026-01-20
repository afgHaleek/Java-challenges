package challenge14.app;

import challenge14.consumer.JobConsumer;
import challenge14.executor.JobExecutor;
import challenge14.model.Job;
import challenge14.producer.JobProducer;
import challenge14.queue.JobQueue;
import challenge14.store.JobStore;

import java.nio.file.Path;
import java.util.List;

public class JobSystemApp {
    public static void main(String[] args) {

        JobQueue queue = new JobQueue(50);
        JobStore store = new JobStore(Path.of("data", "pending-jobs.ser"));
        int consumerCount = 3;

        //load pending jobs
        List<Job> pending = store.load();
        if (!pending.isEmpty()) {
            System.out.println("Loaded " + pending.size() + " pending jobs");
            pending.forEach(job -> {
                try {
                    queue.submit(job);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            store.clear();
        }

        //creating executors and workers
        JobExecutor executor = new JobExecutor(6);

        executor.addConsumer(new JobConsumer("consumer-1", queue));
        executor.addConsumer(new JobConsumer("consumer-2", queue));
        executor.addConsumer(new JobConsumer("consumer-3", queue));

        executor.addProducer(new JobProducer("producer-1", queue, 30));
        executor.addProducer(new JobProducer("producer-2", queue, 30));

        //shutdown hook: persist remaining jobs
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nshutdown requested...");

            for (int i = 0; i < consumerCount; i++) {
                try {
                    queue.submit(Job.poisonPill());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            executor.shutdown();

            List<Job> remaining = queue.drainAll();

            if (!remaining.isEmpty()) {
                System.out.println("Persisting " + remaining.size() + " remaining jobs");
                store.save(remaining);
            } else {
                System.out.println("No remaining jobs to persist");
            }

            System.out.println("shutdown complete");
        }));

        System.out.println("starting job system...");
        executor.start();

        // App keeps running while consumers are alive
        // You can stop with CTRL+C to trigger shutdown hook.
    }
}
