package com.doclerholding.task;

import com.doclerholding.processor.TraceProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TraceTask extends CommandTask {

    public TraceTask(TraceProcessor commandProcessor, String host) {
        super(commandProcessor, host);
    }

    @Override
    public void execute() {
        try {
            log.debug("START Trace Command for host {}", host);
            commandProcessor.process(host);
        } catch (Exception e) {
            log.error("There is an exception happened while trace command running", e);
        }
    }
}
