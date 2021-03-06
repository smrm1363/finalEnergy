package com.mohammadreza.mirali.energyconsumption.domain.meter.validation;

import com.mohammadreza.mirali.energyconsumption.domain.TestCaseData;
import com.mohammadreza.mirali.energyconsumption.domain.common.ValidationException;
import com.mohammadreza.mirali.energyconsumption.domain.meter.MeterEntity;
import org.junit.Test;


public class MeterShouldBeLowerThanPrevTest {
    @Test
    public void validate() throws Exception {
        MeterEntity meterEntity = TestCaseData.getPreperedMeter();
        MeterShouldBeLowerThanPrev meterShouldBeLowerThanPrev = new MeterShouldBeLowerThanPrev();
        meterShouldBeLowerThanPrev.validate(meterEntity);
    }

    @Test(expected = ValidationException.class)
    public void validateEcpectedException() throws Exception {
        MeterEntity meterEntity = TestCaseData.getPreperedMeter();
        meterEntity.getMeterReadingEntityList().get(0).setReadedMeter(100.0);
        MeterShouldBeLowerThanPrev meterShouldBeLowerThanPrev = new MeterShouldBeLowerThanPrev();
        meterShouldBeLowerThanPrev.validate(meterEntity);
    }
}