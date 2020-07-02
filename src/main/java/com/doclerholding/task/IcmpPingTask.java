package com.doclerholding.task;

import com.doclerholding.processor.IcmpPingProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IcmpPingTask extends CommandTask {

    public IcmpPingTask(IcmpPingProcessor commandProcessor, String host) {
        super(commandProcessor, host);
    }

    @Override
    public void execute() {
        try {
            log.debug("START PING WITH ICMP Command for host {}", host);
            commandProcessor.process(host);
        } catch (Exception e) {
            log.error("There is an exception happened while ping icmp command running", e);
        }
    }
}
