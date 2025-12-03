package com.fanduel;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class DeltaCalculator {

    public Map<Long, Long> calculateDelta(Map<Long, Instant> fgsWins, Map<Long, Instant> mdsWins) {
        Map<Long, Long> deltaUserIds = new HashMap<>();

        for (Map.Entry<Long, Instant> entry : mdsWins.entrySet()) {
            Long userId = entry.getKey();
            Instant mdsTimestamp = entry.getValue();

            if (fgsWins.containsKey(userId)) {
                Instant fgsTimestamp = fgsWins.get(userId);

                Long delta = mdsTimestamp.toEpochMilli() - fgsTimestamp.toEpochMilli();
                deltaUserIds.put(userId, delta);
            }
        }

        return deltaUserIds;
    }
}
