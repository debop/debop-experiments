package com.apress.springbatch.chapter4;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

/**
 * com.apress.springbatch.chapter4.ParameterValidator
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 28. 오후 4:22
 */
@Slf4j
public class ParameterValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        String name = jobParameters.getString("name");

        log.debug("name=[{}]", name);

        if (!StringUtils.isAlpha(name)) {
            throw new JobParametersInvalidException("Name is not alphabetic");
        }
    }
}
