package com.fanduel;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        System.out.println("Welcome to FanDuel!");

        ClassLoader classLoader = Main.class.getClassLoader();
        URL fgpFile = classLoader.getResource("v1/FGP.csv");
        URL mdsFile = classLoader.getResource("v1/MDS.csv");
        URL xsellFile = classLoader.getResource("v1/XSELL.csv");

        Path fgsPath = Paths.get(fgpFile.toURI());
        Path mdsPath = Paths.get(mdsFile.toURI());
        Path xsellPath = Paths.get(xsellFile.toURI());

        LogsParser logsParser = new LogsParser();
        var fgpLogs = logsParser.parseLogsFile(fgsPath);
        var mdsLogs = logsParser.parseLogsFile(mdsPath);
        mdsLogs.putAll(logsParser.parseLogsFile(xsellPath));

        var deltaCalculator = new DeltaCalculator();
        var deltaUserIds = deltaCalculator.calculateDelta(fgpLogs, mdsLogs);
        System.out.println("Matched events by user id: " + deltaUserIds.size());

        deltaUserIds.forEach((userId, delta) ->
            System.out.println("UserID: " + userId + ", Delta (ms): " + delta)
        );

        var metricCalculator = new MetricCalculator();

        metricCalculator.average(deltaUserIds.values());
        metricCalculator.p95(deltaUserIds.values());
        metricCalculator.p99(deltaUserIds.values());
    }
}
