package com.doclerholding.result;

import com.doclerholding.property.CommandType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class ResultStorer {
    private final Map<Key, Result> lastResults = new ConcurrentHashMap<>();

    public void store(String host, Result result){
        log.debug("Store result where host is {}, and result is: {}", host, result);
        lastResults.put(new Key(host, result.getCommandType()), result);
    }

    public List<Result> getResultsByHost(String host) {
       return lastResults.entrySet().stream().filter(e -> host.equals(e.getKey().host))
               .map(Map.Entry::getValue)
               .collect(Collectors.toList());
    }

    @Data
    @RequiredArgsConstructor
    class Key {
        private final String host;
        private final CommandType commandType;
    }
}
