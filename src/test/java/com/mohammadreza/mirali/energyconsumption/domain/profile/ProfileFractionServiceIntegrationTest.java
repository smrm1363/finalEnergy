package com.mohammadreza.mirali.energyconsumption.domain.profile;

import com.mohammadreza.mirali.energyconsumption.domain.TestCaseData;
import com.mohammadreza.mirali.energyconsumption.domain.common.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileFractionServiceIntegrationTest {

    @Autowired
    private
    ProfileFractionService profileFractionService;
    @Test
    public void crudProfile() throws Exception {
        profileFractionService.insertProfile(TestCaseData.getPreperedProfile());
        ProfileEntity profileEntity = profileFractionService.findProfileById(TestCaseData.getPreperedProfile().getId());
        assertEquals(profileEntity.getId(),TestCaseData.getPreperedProfile().getId());
        profileFractionService.deleteProfile(profileEntity.getId());
        profileEntity = profileFractionService.findProfileById(TestCaseData.getPreperedProfile().getId());
        assertNull(profileEntity);
    }

    @Test
    @Transactional
    public void convertToEntity() throws Exception {
        profileFractionService.convertToEntity(TestCaseData.getPreperedProfileFractionDto());
        ProfileEntity profileEntity = profileFractionService.findProfileById(TestCaseData.getPreperedProfile().getId());
        assertTrue(profileEntity != null);
    }


    @Test
    public void saveProfileList() throws Exception {
        List<ProfileEntity> profileEntityList = new ArrayList<>();
        profileEntityList.add(TestCaseData.getPreperedProfile());
        profileFractionService.saveProfileList(profileEntityList);
        ProfileEntity profileEntity = profileFractionService.findProfileById(TestCaseData.getPreperedProfile().getId());
        assertTrue(profileEntity != null);
    }

    @Test(expected = ValidationException.class)
    public void saveProfileListExpectedException() throws Exception {
        List<ProfileEntity> profileEntityList = new ArrayList<>();
        profileEntityList.add(TestCaseData.getPreperedProfile());
        profileEntityList.get(0).getProfileFractionEntityList().get(0).setFraction(2.0);
        profileFractionService.saveProfileList(profileEntityList);
        ProfileEntity profileEntity = profileFractionService.findProfileById(TestCaseData.getPreperedProfile().getId());
        assertTrue(profileEntity != null);
    }

    @javax.transaction.Transactional
    @Test(expected = ValidationException.class )
    public void uploadOldFile() throws Exception
    {
        Path directoryPath = FileSystems.getDefault().getPath(".").toAbsolutePath().getParent();
        directoryPath = Paths.get(directoryPath.toString(),"testData");
        Path filePath = Paths.get(directoryPath.toString(),"test.csv");
        File directory = new File(directoryPath.toString());
        if (! directory.exists()){
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        PrintWriter pw = new PrintWriter(new File(filePath.toString()));
        pw.write("Month,Profile,Fraction\n" +
                "JAN,A,0.2\n" +
                "JAN,B,0.18\n" +
                "MAR,A,0.0");
        pw.close();
        try {
            profileFractionService.uploadOldFile(filePath.toString(), ProfileFractionDto.class);
        }
        catch (ValidationException e) {
            File[] files = directory.listFiles();
            Long count = Arrays.stream(files).filter(file -> file.getName().endsWith(".log")).count();
            assertTrue(count > 0);
            Arrays.stream(files).forEach(file -> file.delete());
            throw e;
        }

    }




}