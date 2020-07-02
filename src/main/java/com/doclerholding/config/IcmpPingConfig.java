package com.doclerholding.config;

import com.doclerholding.property.IcmpPingProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class IcmpPingConfig {
    private static final String HOST_PARAM = "HOST";

    private final String pingCommand;
    private final long delayInSec;

    public static IcmpPingConfig create(IcmpPingProperty property, String host) {
        return new IcmpPingConfig(property.getPingCommand().replaceAll(HOST_PARAM, host), property.getDelayInSec());
    }
}
