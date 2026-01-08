# Challenge 12 — In-Memory Job Processing System (Concurrency + Collections)

This challenge builds a small, production-style **background job processor** in pure Java.  
You can submit work (jobs), run it concurrently using a thread pool, track job lifecycle, retrieve results, cancel running jobs, and shut the system down safely.

The goal is to learn the most important Java concurrency building blocks by implementing a real pattern used in backend systems (job runners, schedulers, async processing, workers).

---

## What this mini project does

### You can:
- Submit a job (`Callable<T>`) for asynchronous execution
- Track job status:
    - `PENDING → RUNNING → SUCCESS / FAILED / CANCELLED`
- Store job results and errors in a consistent structure (`JobResult<T>`)
- Cancel jobs using `Future.cancel(true)` (interruption-based cancellation)
- Shut down the worker pool gracefully (timeout + fallback to forced shutdown)
- Query job state via snapshot methods (safe read-only views of current data)

---

## Key concepts practiced

### Concurrency
- `ExecutorService` (fixed thread pool)
- `Callable` vs `Runnable` (result-returning tasks vs fire-and-forget)
- `Future` (control handle: cancel / wait / check done)
- Cancellation and interruption (`cancel(true)` + `InterruptedException`)
- Graceful shutdown:
    - `shutdown()`
    - `awaitTermination(...)`
    - `shutdownNow()` fallback
- `volatile` flag for visibility across threads (reject submissions after shutdown starts)

### Collections
- `ConcurrentHashMap` for thread-safe shared state
- Snapshot copies for reporting (`new HashMap<>(map)`) to avoid unstable views while maps are being updated by workers

---


---

## How it works 

1. **Submit** a `Job<T>` with an id + description + `Callable<T>`
2. `JobExecutor` marks it `PENDING`, then submits a wrapped task to the pool
3. When a worker starts the job, status becomes `RUNNING`
4. On completion:
    - `SUCCESS` + value stored in `JobResult.success(value)`
    - or `FAILED` + exception stored in `JobResult.failed(error)`
5. The returned `Future` is stored so jobs can be cancelled or awaited later
6. Cancellation uses `Future.cancel(true)` and records `CANCELLED`
7. Shutdown stops new submissions and optionally waits for workers to finish

---

## Running the demo

Run the `DemoMain` class:

- It submits:
    - one quick success job
    - one slow job (cancelled)
    - one failing job
- Prints status snapshots, results, and counts
- Shuts down gracefully

---

## Running tests

Run all tests:

```bash
mvn test

