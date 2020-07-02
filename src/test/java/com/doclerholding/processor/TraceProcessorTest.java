package com.doclerholding.processor;

import com.doclerholding.property.CommandType;
import com.doclerholding.reporter.Reporter;
import com.doclerholding.result.Result;
import com.doclerholding.result.ResultStorer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

public class TraceProcessorTest {
    private static final String OUTPUT = "test";
    private static final String CMD = "echo " + OUTPUT;
    private TraceProcessor processor;

    @Mock
    private ResultStorer resultStorer;

    @Mock
    private Reporter reporter;

    @BeforeEach
    public void init(){
        initMocks(this);
        processor = new TraceProcessor(resultStorer, reporter, () -> CMD);
    }

    @Test
    public void testGetResult(){
        Result result = processor.getResult();

        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getStartTime());
        Assertions.assertTrue(result.getResult().contains(OUTPUT));
        Assertions.assertEquals(CommandType.TRACE, result.getCommandType());
    }


    @Test
    public void testShouldNotCallReport() {
        Result result = Result.builder().build();
        Assertions.assertFalse(processor.needToCallReport(result));
    }

}
