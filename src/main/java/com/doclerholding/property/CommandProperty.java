package com.doclerholding.property;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommandProperty {
    private final String reportUrl;
    private final IcmpPingProperty icmpPingProperty;
    private final TcpPingProperty tcpPingProperty;
    private final TraceProperty traceProperty;
}
