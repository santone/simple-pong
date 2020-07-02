package com.doclerholding.property;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TcpPingProperty {
    private final long delayInSec;
    private final long timeoutInSec;
}
