package com.apress.springbatch.chapter4;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.util.Date;

/**
 * com.apress.springbatch.chapter4.DailyJobTimestamper
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 9:31
 */
@Slf4j
public class DailyJobTimestamper implements JobParametersIncrementer {

    /** Increment the current.data parameter. */
    @Override
    public JobParameters getNext(JobParameters parameters) {
        Date today = new Date();
        if (parameters != null && !parameters.isEmpty()) {
            Date oldDate = parameters.getDate("current.date", new Date());
            today = DateUtils.addDays(oldDate, 1);
        }
        return new JobParametersBuilder().addDate("current.date", today)
                                         .toJobParameters();
    }
}
