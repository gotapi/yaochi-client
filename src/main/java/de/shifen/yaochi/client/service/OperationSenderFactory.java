package de.shifen.yaochi.client.service;

import lombok.Data;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ms404 <yaochi.github@404.ms>
 */
public class OperationSenderFactory {
    @Data
    public static class DefaultAsyncThreadFactory implements ThreadFactory {
        private final AtomicInteger counter = new AtomicInteger(0);

        public AtomicInteger getCounter() {
            return counter;
        }

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "operation_send_thread_" + counter.incrementAndGet());
        }
    }
}
