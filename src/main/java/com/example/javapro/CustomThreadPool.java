package com.example.javapro;

import java.util.LinkedList;
import java.util.List;

class CustomThreadPool {
    private final LinkedList<Runnable> taskQueue = new LinkedList<>();
    private final List<Worker> workers = new LinkedList<>();
    private boolean isShutdown = false;

    public CustomThreadPool(int capacity) {
        for (int i = 0; i < capacity; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker).start();
        }
    }

    public static void main(String[] args) {
        CustomThreadPool pool = new CustomThreadPool(3);

        for (int i = 0; i < 10; i++) {
            int taskNumber = i;
            pool.execute(() -> {
                System.out.println("Running task " + taskNumber + " in thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            });
        }

        pool.shutdown();
        pool.awaitTermination();
        System.out.println("All tasks completed.");
    }

    public void execute(Runnable task) {
        synchronized (taskQueue) {
            if (isShutdown) {
                throw new IllegalStateException("ThreadPool is shut down, cannot accept new tasks");
            }
            taskQueue.addLast(task);
            taskQueue.notify();
        }
    }

    public void shutdown() {
        synchronized (taskQueue) {
            isShutdown = true;
            taskQueue.notifyAll();
        }
    }

    public void awaitTermination() {
        for (Worker worker : workers) {
            worker.awaitFinish();
        }
    }

    private class Worker implements Runnable {
        private boolean isRunning = true;
        private Thread currentThread;

        @Override
        public void run() {
            currentThread = Thread.currentThread();
            while (isRunning) {
                Runnable task;
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty() && !isShutdown) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException ignored) {}
                    }

                    if (taskQueue.isEmpty() && isShutdown) {
                        break;
                    }

                    task = taskQueue.removeFirst();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.err.println("Task execution failed: " + e.getMessage());
                }
            }
        }

        public void awaitFinish() {
            try {
                if (currentThread != null) {
                    currentThread.join();
                }
            } catch (InterruptedException ignored) {}
        }
    }
}