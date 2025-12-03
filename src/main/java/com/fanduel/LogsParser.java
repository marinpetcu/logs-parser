package com.fanduel;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class LogsParser {

    public Map<Long, Instant> parseLogsFile(Path filePath) {
        Map<Long, Instant> result = new HashMap<>();
        try {
            var lines = Files.readAllLines(filePath);
            for (int i = 1; i < lines.size(); i++) { // start from 1 to skip header
                String[] parts = lines.get(i).split(",");
                if (parts.length == 2) {
                    Instant timestamp = Instant.parse(parts[0].trim());
                    Long userId = Long.parseLong(parts[1].trim());
                    result.put(userId, timestamp);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse FGS file", e);
        }
        return result;
    }
}
