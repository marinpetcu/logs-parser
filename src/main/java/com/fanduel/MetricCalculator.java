package com.fanduel;

import java.util.Collection;

public class MetricCalculator {

    public static final int THRESHOLD = 50_000;

    public void average(Collection<Long> values) {
        var average = values.stream()
                .filter(v -> v < THRESHOLD)
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);

        System.out.println("Average Delta (ms) : " + average);
    }

    public void p95(Collection<Long> values) {
        var sorted = values.stream()
                .filter(v -> v < THRESHOLD)
                .sorted()
                .toList();

        int index95 = (int) Math.ceil(sorted.size() * 0.95) - 1;
        Long p95 = sorted.get(index95);

        System.out.println("p95 Delta (ms) : " + p95);
    }

    public void p99(Collection<Long> values) {
        var sorted = values.stream()
                .filter(v -> v < THRESHOLD)
                .sorted()
                .toList();

        int index95 = (int) Math.ceil(sorted.size() * 0.99) - 1;
        Long p99 = sorted.get(index95);

        System.out.println("p99 Delta (ms) : " + p99);
    }
}
