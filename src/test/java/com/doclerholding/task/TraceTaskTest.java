package com.doclerholding.task;

import com.doclerholding.processor.TraceProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.MockitoAnnotations.initMocks;

public class TraceTaskTest {
    private static final String HOST = "host";

    @Mock
    private TraceProcessor processor;
    private TraceTask task;

    @BeforeEach
    public void init(){
        initMocks(this);
        task = new TraceTask(processor, HOST);
    }

    @Test
    public void testExecute(){
        task.execute();
        Mockito.verify(processor).process(HOST);
    }
}
