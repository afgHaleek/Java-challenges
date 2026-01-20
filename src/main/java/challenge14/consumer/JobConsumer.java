package challenge14.consumer;

import challenge14.model.Job;
import challenge14.queue.JobQueue;

public class JobConsumer  implements Runnable {

    private final String consumerName;
    private final JobQueue queue;

    public JobConsumer(String consumerName, JobQueue queue) {
        this.consumerName = consumerName;
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Job job = queue.take();

                if (job.isPoison()) {
                    System.out.println(consumerName + " received poison pill. Exiting...");
                    break;
                }
                process(job);
            }
        } catch (InterruptedException e) {
            System.out.println(consumerName + " interrupted. Exiting...");
            Thread.currentThread().interrupt();
        }
    }

    private void process(Job job) {
        System.out.println(
                consumerName + " processing: " + job.payload()
        );

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
