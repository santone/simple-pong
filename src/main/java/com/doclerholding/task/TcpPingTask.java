package com.doclerholding.task;

import com.doclerholding.processor.TcpPingProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TcpPingTask extends CommandTask {
    public TcpPingTask(TcpPingProcessor commandProcessor, String host) {
        super(commandProcessor, host);
    }

    @Override
    public void execute() {
        try {
            log.debug("START PING WITH TCP Command for host {}", host);
            commandProcessor.process(host);
        } catch (Exception e) {
            log.error("There is an exception happened while ping tcp command running", e);
        }
    }
}
