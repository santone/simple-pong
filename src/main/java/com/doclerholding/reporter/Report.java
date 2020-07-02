package com.doclerholding.reporter;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Report {
    private String host;

    @JsonProperty("icmp_ping")
    private String icmpPing;

    @JsonProperty("tcp_ping")
    private String tcpPing;

    private String trace;
}
