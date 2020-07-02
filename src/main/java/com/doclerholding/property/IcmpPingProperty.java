package com.doclerholding.property;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class IcmpPingProperty {
    private final String pingCommand;
    private final long delayInSec;
}
