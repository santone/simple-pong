package com.doclerholding.config;

import com.doclerholding.property.TcpPingProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TcpPingConfig {
    private final long delayInSec;
    private final long timeoutInSec;

    public static TcpPingConfig create(TcpPingProperty property) {
        return new TcpPingConfig(property.getDelayInSec(), property.getTimeoutInSec());
    }
}
