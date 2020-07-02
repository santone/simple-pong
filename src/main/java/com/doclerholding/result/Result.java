package com.doclerholding.result;

import com.doclerholding.property.CommandType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class Result {
    private final CommandType commandType;
    private final Date startTime;
    private final String result;
    private final int httpStatus;
    private final long runningTimeInSec;
    private final String url;

}
