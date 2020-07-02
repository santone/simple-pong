package com.doclerholding.config;

import com.doclerholding.property.TraceProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TraceConfig {
    private static final String HOST_PARAM = "HOST";

    private final String traceCommand;
    private final long delayInSec;

    public static TraceConfig create(TraceProperty property, String host) {
        return new TraceConfig(property.getTraceCommand().replaceAll(HOST_PARAM, host), property.getDelayInSec());
    }
}
