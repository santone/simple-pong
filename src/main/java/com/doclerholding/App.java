package com.doclerholding;

import com.doclerholding.config.CommandConfigFactory;
import com.doclerholding.config.Config;
import com.doclerholding.processor.IcmpPingProcessor;
import com.doclerholding.processor.TcpPingProcessor;
import com.doclerholding.processor.TraceProcessor;
import com.doclerholding.property.CommandProperty;
import com.doclerholding.property.CommandPropertyLoader;
import com.doclerholding.reporter.Reporter;
import com.doclerholding.result.ResultStorer;
import com.doclerholding.task.IcmpPingTask;
import com.doclerholding.task.TaskManager;
import com.doclerholding.task.TcpPingTask;
import com.doclerholding.task.TraceTask;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class App 
{
    private static final String CONFIG_FILE = "configuration.xml";

    public static void main( String[] args )
    {
        log.debug( " =========================================================== " );
        log.debug( " ========== Simple Ping Application started  =============== " );
        log.debug( " =========================================================== " );


        CommandProperty commandProperties = new CommandPropertyLoader().load(CONFIG_FILE);

        List<String> hosts = Arrays.asList(args);
        List<Config> configs = CommandConfigFactory.create(commandProperties, hosts);

        ResultStorer resultStorer = new ResultStorer();
        Reporter reporter = new Reporter(commandProperties.getReportUrl(), resultStorer, HttpClient.newHttpClient());
        TaskManager taskManager = new TaskManager();

        configs.forEach(
                config -> {

                    taskManager.scheduleAtFixDelay(
                            new IcmpPingTask(new IcmpPingProcessor(resultStorer, reporter, () -> config.getIcmpPingConfig().getPingCommand()), config.getHost()),
                            config.getIcmpPingConfig().getDelayInSec());

                    taskManager.scheduleAtFixDelay(
                            new TcpPingTask(
                                new TcpPingProcessor(resultStorer, reporter, config, HttpClient.newHttpClient()), config.getHost()),
                                config.getTcpPingConfig().getDelayInSec()
                            );

                    taskManager.scheduleAtFixDelay(
                            new TraceTask(new TraceProcessor(resultStorer, reporter, () -> config.getTraceConfig().getTraceCommand()), config.getHost()),
                            config.getTraceConfig().getDelayInSec());

                });
    }
}
