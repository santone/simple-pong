package com.doclerholding.processor;

import com.doclerholding.property.CommandType;
import com.doclerholding.reporter.Reporter;
import com.doclerholding.result.Result;
import com.doclerholding.result.ResultStorer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;
import java.util.function.Supplier;

@Slf4j
public class TraceProcessor extends CommandProcessor {

    private final Supplier<String> commandSupplier;

    public TraceProcessor(ResultStorer resultStorer,
                          Reporter reporter,
                          Supplier<String> commandSupplier) {
        super(resultStorer, reporter);
        this.commandSupplier = commandSupplier;
    }


    @Override
    public Result getResult() {
        try {
            Date startDate = new Date();
            String output = getOutputFromCmdLine(commandSupplier.get());
            return Result.builder()
                    .commandType(CommandType.TRACE)
                    .startTime(startDate)
                    .result(output)
                    .build();
        } catch (IOException ex) {
            throw new RuntimeException("Error while read output of trace", ex);
        }
    }

    @Override
    public boolean needToCallReport(Result result) {
        return false;
    }
}
