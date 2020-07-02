package com.doclerholding.task;

import com.doclerholding.processor.TcpPingProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.MockitoAnnotations.initMocks;

public class TcpPingTaskTest {
    private static final String HOST = "host";

    @Mock
    private TcpPingProcessor processor;
    private TcpPingTask task;

    @BeforeEach
    public void init(){
        initMocks(this);
        task = new TcpPingTask(processor, HOST);
    }

    @Test
    public void testExecute(){
        task.execute();
        Mockito.verify(processor).process(HOST);
    }
}
