package com.doclerholding.processor;

import com.doclerholding.reporter.Reporter;
import com.doclerholding.result.ResultStorer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;
import static org.mockito.MockitoAnnotations.initMocks;

public class CommandProcessorTest {
    private final static String HOST = "HOST";

    private DummyProcessor processorCallingReporter;
    private DummyProcessor processorNotCallingReporter;

    @Mock
    private ResultStorer resultStorer;

    @Mock
    private Reporter reporter;

    @BeforeEach
    public void init(){
        initMocks(this);
        processorCallingReporter = new DummyProcessor(resultStorer, reporter, true);
        processorNotCallingReporter = new DummyProcessor(resultStorer, reporter, false);
    }

    @Test
    public void shouldProcessCallingReporter() {
        processorCallingReporter.process(HOST);
        Mockito.verify(resultStorer).store(HOST, processorCallingReporter.getResult());
        Mockito.verify(reporter).report(HOST);
    }

    @Test
    public void shouldProcessNotCallingReporter() {
        processorNotCallingReporter.process(HOST);
        Mockito.verify(resultStorer).store(HOST, processorNotCallingReporter.getResult());
        Mockito.verify(reporter, never()).report(HOST);
    }
}
