package com.openclassrooms.chatopbackend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class StorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) throws FileNotFoundException {

        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();
        if(estExtensionImageValide(fileName))
            return fileName;
        return null;
    }



    private File convertMultiPartFileToFile(MultipartFile file) throws FileNotFoundException {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch (IOException e){
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    private static boolean estExtensionImageValide(String nomFichier) {
        String extension = nomFichier.substring(nomFichier.lastIndexOf(".") + 1).toLowerCase();
        String[] extensionsValides = {"jpg", "jpeg", "png"};
        for (String ext : extensionsValides) {
            if (ext.equals(extension)) {
                return true;
            }
        }
        return false;

    }




    public byte[] downloadFile(String fileName){
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try{
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName){
        amazonS3.deleteObject(bucketName, fileName);
        return fileName + " removed ...";
    }
}
