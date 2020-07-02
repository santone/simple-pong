package com.doclerholding.processor;

import com.doclerholding.config.Config;
import com.doclerholding.config.TcpPingConfig;
import com.doclerholding.property.CommandType;
import com.doclerholding.property.TcpPingProperty;
import com.doclerholding.reporter.Reporter;
import com.doclerholding.result.Result;
import com.doclerholding.result.ResultStorer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TcpPingProcessorTest {
    private final static String HOST = "host";
    private final static long DELAY = 1;
    private final static long TIMEOUT = 2;
    private final String OUTPUT = "output";

    private TcpPingProcessor processor;

    @Mock
    private ResultStorer resultStorer;

    @Mock
    private Reporter reporter;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<String> response;

    private Config config;

    private HttpRequest request;


    @BeforeEach
    public void init(){
        initMocks(this);
        config = Config.builder()
                .host(HOST)
                .tcpPingConfig(TcpPingConfig.create(new TcpPingProperty(DELAY, TIMEOUT)))
                .build();
        processor = new TcpPingProcessor(resultStorer, reporter, config, httpClient);

        request = HttpRequest.newBuilder()
                .uri(URI.create( "https://" + config.getHost()))
                .GET()
                .timeout(Duration.ofSeconds(config.getTcpPingConfig().getTimeoutInSec()))
                .build();
    }

    @Test
    public void testGetResult() throws IOException, InterruptedException {

        final int okStatus = 200;

        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenReturn(response);
        when(response.statusCode()).thenReturn(okStatus);
        when(response.body()).thenReturn(OUTPUT);

        Result result = processor.getResult();

        Assertions.assertNotNull(result);
        Assertions.assertEquals("https://" + config.getHost(), result.getUrl());
        Assertions.assertNotNull(result.getStartTime());
        Assertions.assertEquals(OUTPUT, result.getResult());
        Assertions.assertEquals(okStatus, result.getHttpStatus());
        Assertions.assertEquals(CommandType.TCP_PING, result.getCommandType());
    }

    @Test
    public void testGetResultWhenConnectionException() throws IOException, InterruptedException {

        final int serverError = 500;

        when(httpClient.send(request, HttpResponse.BodyHandlers.ofString())).thenThrow(new ConnectException(OUTPUT));

        Result result = processor.getResult();

        Assertions.assertNotNull(result);
        Assertions.assertEquals("https://" + config.getHost(), result.getUrl());
        Assertions.assertNotNull(result.getStartTime());
        Assertions.assertEquals(serverError, result.getHttpStatus());
        Assertions.assertEquals(OUTPUT, result.getResult());

        Assertions.assertEquals(CommandType.TCP_PING, result.getCommandType());
    }


    @Test
    public void testShouldNotCallReport() {
        Result result = Result.builder().httpStatus(200).build();
        Assertions.assertFalse(processor.needToCallReport(result));
    }

    @Test
    public void testCallReport() {
        Result result = Result.builder().httpStatus(400).build();
        Assertions.assertTrue(processor.needToCallReport(result));
    }

}
