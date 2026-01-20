package challenge14.queue;

import challenge14.model.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class JobQueue {

    private final BlockingQueue<Job> queue;

    public JobQueue(int capacity) {
        this.queue = new ArrayBlockingQueue<>(capacity);
    }

    public void submit(Job job) throws InterruptedException {
        queue.put(job);
    }

    public Job take() throws InterruptedException {
        return queue.take();
    }

    public int size() {
        return queue.size();
    }

    public List<Job> drainAll() {
        List<Job> jobs = new ArrayList<>();
        queue.drainTo(jobs);
        return jobs;
    }
}
