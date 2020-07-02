package com.doclerholding.config;

import com.doclerholding.property.CommandProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CommandConfigFactory {
    public static List<Config> create(CommandProperty commandProperties, List<String> hosts) {
        log.debug( "Create configurations from properties ... ");

        List<Config> configs = hosts.stream().map(host -> Config.builder()
                .reportUrl(commandProperties.getReportUrl())
                .host(host)
                .icmpPingConfig(IcmpPingConfig.create(commandProperties.getIcmpPingProperty(), host))
                .tcpPingConfig(TcpPingConfig.create(commandProperties.getTcpPingProperty()))
                .traceConfig(TraceConfig.create(commandProperties.getTraceProperty(), host))
                .build())
                .collect(Collectors.toList());

        log.debug( "Created configurations: {}", configs);

        return configs;
    }
}
