package com.doclerholding.processor;

import com.doclerholding.config.Config;
import com.doclerholding.property.CommandType;
import com.doclerholding.reporter.Reporter;
import com.doclerholding.result.Result;
import com.doclerholding.result.ResultStorer;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Date;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TcpPingProcessor extends CommandProcessor {
    private final Config config;
    private final HttpClient httpClient;

    public TcpPingProcessor(ResultStorer resultStorer,
                            Reporter reporter,
                            Config config,
                            HttpClient httpClient) {
        super(resultStorer, reporter);
        this.config = config;
        this.httpClient = httpClient;
    }

    @Override
    public Result getResult() {
        String url = "https://" + config.getHost();
        Date startDate = new Date();
        long startTime = System.nanoTime();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(config.getTcpPingConfig().getTimeoutInSec()))
                    .build();

            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            long endTime = System.nanoTime();

            return Result.builder()
                    .startTime(startDate)
                    .commandType(CommandType.TCP_PING)
                    .httpStatus(response.statusCode())
                    .result(response.body())
                    .runningTimeInSec(SECONDS.convert(endTime - startTime, NANOSECONDS))
                    .url(url)
                    .build();
        } catch (ConnectException ex) {
            long endTime = System.nanoTime();
            return Result.builder()
                    .startTime(startDate)
                    .commandType(CommandType.TCP_PING)
                    .httpStatus(500)
                    .result(ex.getMessage())
                    .runningTimeInSec(SECONDS.convert(endTime - startTime, NANOSECONDS))
                    .url(url)
                    .build();

        } catch (Exception ex) {
            throw new RuntimeException("Error when tcp ping request", ex);
        }
    }

    @Override
    public boolean needToCallReport(Result result) {
        return !is2xx(result.getHttpStatus());
    }

    private static boolean is2xx(int httpStatus){
        return httpStatus > 199 && httpStatus < 300;
    }
}
