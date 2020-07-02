package com.doclerholding.processor;

import com.doclerholding.reporter.Reporter;
import com.doclerholding.result.Result;
import com.doclerholding.result.ResultStorer;

public class DummyProcessor extends CommandProcessor {

    private boolean needToCallReport;
    public DummyProcessor(ResultStorer resultStorer, Reporter reporter, boolean needToCallReport) {
        super(resultStorer, reporter);
        this.needToCallReport = needToCallReport;
    }

    @Override
    public Result getResult() {
        return Result.builder().build();
    }

    @Override
    public boolean needToCallReport(Result result) {
        return needToCallReport;
    }
}
