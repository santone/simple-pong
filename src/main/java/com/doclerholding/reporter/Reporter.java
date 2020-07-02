package com.doclerholding.reporter;

import com.doclerholding.result.Result;
import com.doclerholding.result.ResultStorer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
@Data
@RequiredArgsConstructor
public class Reporter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String url;
    private final ResultStorer resultStorer;
    private final HttpClient httpClient;

    public void report(String host){
        List<Result> resultsByHost = resultStorer.getResultsByHost(host);
        log.warn("Results for host: {} is {}", host, resultsByHost);
        Report report = new Report();
        report.setHost(host);
        resultsByHost.forEach( r -> {
            switch (r.getCommandType()){

                case ICMP_PING: report.setIcmpPing(r.getResult()); break;
                case TCP_PING: report.setTcpPing(r.getResult());break;
                case TRACE: report.setTrace(r.getResult()); break;

                default: throw new RuntimeException("Unknown command type: " + r.getCommandType());
            }
        });

        postReport(report);
    }

    public void postReport(Report report) {
        try {
            String serializedReport =  objectMapper.writerFor(Report.class).writeValueAsString(report);
            log.warn("Post report: {}", serializedReport);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(serializedReport))
                    .build();

            httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            log.warn("Can not report {}", e.getMessage());
        }
    }
}
