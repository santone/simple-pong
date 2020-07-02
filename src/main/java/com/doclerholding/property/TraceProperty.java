package com.doclerholding.property;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TraceProperty {
    private final String traceCommand;
    private final long delayInSec;
}
