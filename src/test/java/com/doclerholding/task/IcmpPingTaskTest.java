package com.doclerholding.task;

import com.doclerholding.processor.IcmpPingProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.MockitoAnnotations.initMocks;

public class IcmpPingTaskTest {
    private static final String HOST = "host";

    @Mock
    private IcmpPingProcessor processor;
    private IcmpPingTask task;

    @BeforeEach
    public void init(){
        initMocks(this);
        task = new IcmpPingTask(processor, HOST);
    }

    @Test
    public void testExecute(){
        task.execute();
        Mockito.verify(processor).process(HOST);
    }
}
