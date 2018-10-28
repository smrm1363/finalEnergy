package com.mohammadreza.mirali.energyconsumption.domain.meter.validation;

import com.mohammadreza.mirali.energyconsumption.domain.TestCaseData;
import com.mohammadreza.mirali.energyconsumption.domain.common.ValidationException;
import com.mohammadreza.mirali.energyconsumption.domain.meter.MeterEntity;
import org.junit.Test;


public class FractionShoudBeFoundTest {
    @Test
    public void validate() throws Exception {
        MeterEntity meterEntity = TestCaseData.getPreperedMeter();
        meterEntity.setProfileEntity(TestCaseData.getPreperedProfile());
        FractionShoudBeFound fractionShoudBeFound = new FractionShoudBeFound();
        fractionShoudBeFound.validate(meterEntity);
    }

    @Test(expected = ValidationException.class)
    public void validateExpectedException() throws Exception {
        MeterEntity meterEntity = TestCaseData.getPreperedMeter();
        meterEntity.setProfileEntity(TestCaseData.getPreperedProfile());
        meterEntity.getProfileEntity().getProfileFractionEntityList().remove(0);
        FractionShoudBeFound fractionShoudBeFound = new FractionShoudBeFound();
        fractionShoudBeFound.validate(meterEntity);
    }

}