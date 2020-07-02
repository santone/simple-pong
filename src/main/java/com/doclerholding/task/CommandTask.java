package com.doclerholding.task;

import com.doclerholding.processor.CommandProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class CommandTask implements Task {

    protected final CommandProcessor commandProcessor;
    protected final String host;

    public abstract void execute();
}
