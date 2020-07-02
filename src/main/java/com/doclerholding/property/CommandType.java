package com.doclerholding.property;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CommandType {
    ICMP_PING("icmp_ping"),
    TCP_PING("tcp_ping"),
    TRACE("trace"),
    ;
    final String type;
}
