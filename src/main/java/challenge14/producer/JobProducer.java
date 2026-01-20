package challenge14.producer;

import challenge14.model.Job;
import challenge14.queue.JobQueue;

public class JobProducer implements Runnable{

    private final String producerName;
    private final JobQueue queue;
    private final int jobsToProduce;

    public JobProducer(String producerName, JobQueue queue, int jobsToProduce) {
        this.producerName = producerName;
        this.queue = queue;
        this.jobsToProduce = jobsToProduce;
    }

    @Override
    public void run() {
        for (int i = 1; i <= jobsToProduce; i++) {
            try {
                Job job = new Job("Job from " + producerName + "  #" + i);
                queue.submit(job);

                System.out.println(producerName + " submitted: " + job.payload());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(producerName + " interrupted while submitting job");
                break;
            }
        }
    }
}
