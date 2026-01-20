package challenge14.executor;

import challenge14.consumer.JobConsumer;
import challenge14.producer.JobProducer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JobExecutor {

    private final ExecutorService executorService;
    private final List<Runnable> producers = new ArrayList<>();
    private final List<Runnable> consumers = new ArrayList<>();

    public JobExecutor(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void addProducer(JobProducer producer) {
        producers.add(producer);
    }

    public void addConsumer(JobConsumer consumer) {
        consumers.add(consumer);
    }

    public void start() {
        consumers.forEach(executorService::submit);
        producers.forEach(executorService::submit);
    }

    public void shutdown() {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(3, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
