package com.doclerholding.property;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandPropertyLoaderTest {
    @Test
    public void testLoad() {
        CommandProperty properties = new CommandPropertyLoader().load("configuration-test.xml");

        String reportUrl = properties.getReportUrl();
        IcmpPingProperty icmpPingProperty = properties.getIcmpPingProperty();
        TcpPingProperty tcpPingProperty = properties.getTcpPingProperty();
        TraceProperty traceProperty = properties.getTraceProperty();

        Assertions.assertEquals("https://dummyUrl", reportUrl);
        Assertions.assertEquals(1l, icmpPingProperty.getDelayInSec());
        Assertions.assertEquals("ping -n 5 HOST", icmpPingProperty.getPingCommand());
        Assertions.assertEquals(3l, tcpPingProperty.getDelayInSec());
        Assertions.assertEquals(4l, tcpPingProperty.getTimeoutInSec());
        Assertions.assertEquals(2l, traceProperty.getDelayInSec());
        Assertions.assertEquals("tracert HOST",traceProperty.getTraceCommand());

    }
}
