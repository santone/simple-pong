package com.doclerholding.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Config {
    private final String reportUrl;
    private final String host;
    private final IcmpPingConfig icmpPingConfig;
    private final TraceConfig traceConfig;
    private final TcpPingConfig tcpPingConfig;
}
