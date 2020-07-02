package com.doclerholding.reporter;

import com.doclerholding.property.CommandType;
import com.doclerholding.result.Result;
import com.doclerholding.result.ResultStorer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ReporterTest {
    private static final String HOST = "host";
    private static final String URL = "https://dummy";

    @Mock
    private ResultStorer resultStorer;

    @Mock
    private HttpClient httpClient;

    private Reporter reporter;

    @BeforeEach
    public void init(){
        initMocks(this);
        reporter = new Reporter(URL, resultStorer, httpClient);;
    }

    @Test
    public void testReport() throws IOException, InterruptedException {

        // GIVEN
        Result r1 = Result.builder()
                .commandType(CommandType.ICMP_PING)
                .result("a").build();

        Result r2 = Result.builder()
                .commandType(CommandType.TCP_PING)
                .result("b").build();

        Result r3 = Result.builder()
                .commandType(CommandType.TRACE)
                .result("c").build();

        // WHEN
        when(resultStorer.getResultsByHost(HOST)).thenReturn(List.of(r1, r2, r3));
        reporter.report(HOST);

        // THEN
        Mockito.verify(httpClient).send(HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .POST(HttpRequest.BodyPublishers.ofString("{\"host\":\"host\",\"trace\":\"c\",\"icmp_ping\":\"a\",\"tcp_ping\":\"b\"}"))
                .build(), HttpResponse.BodyHandlers.ofString());

    }
}
