package com.doclerholding.processor;

import com.doclerholding.reporter.Reporter;
import com.doclerholding.result.Result;
import com.doclerholding.result.ResultStorer;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RequiredArgsConstructor
public abstract class CommandProcessor {
    protected final ResultStorer resultStorer;
    protected final Reporter reporter;

    public void process(String host) {
        Result result = getResult();
        resultStorer.store(host, result);
        if (needToCallReport(result)) {
            reporter.report(host);
        }
    }

    protected String getOutputFromCmdLine(String command) throws IOException {
        Process process = Runtime.getRuntime().exec(command);

        StringBuilder result = new StringBuilder();
        try (BufferedReader inputStream = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line = "";
            while ((line = inputStream.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }

    public abstract Result getResult();
    public abstract boolean needToCallReport(Result result);
}
