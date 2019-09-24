package com.gym.trainee.service.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class SequenceUtil {
    private AtomicLong id = new AtomicLong();

    public long next() {
        return id.incrementAndGet();
    }
}

