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

public class IcmpPingProcessorTest {
    private static final String OUTPUT = "test";
    private static final String CMD = "echo " + OUTPUT;
    private static final String GOOD_RESULT = "(0% loss)";
    private static final String BAD_RESULT = "Bad";
    private IcmpPingProcessor processor;

    @Mock
    private ResultStorer resultStorer;

    @Mock
    private Reporter reporter;

    @BeforeEach
    public void init(){
        initMocks(this);
        processor = new IcmpPingProcessor(resultStorer, reporter, () -> CMD);
    }

    @Test
    public void testGetResult(){
        Result result = processor.getResult();
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getStartTime());
        Assertions.assertTrue(result.getResult().contains(OUTPUT));
        Assertions.assertEquals(CommandType.ICMP_PING, result.getCommandType());
    }

    @Test
    public void testNeedToCallReport() {
        Result result = Result.builder().result(BAD_RESULT).build();
        Assertions.assertTrue(processor.needToCallReport(result));
    }

    @Test
    public void testShouldNotCallReport() {
        Result result = Result.builder().result(GOOD_RESULT).build();
        Assertions.assertFalse(processor.needToCallReport(result));
    }

}
