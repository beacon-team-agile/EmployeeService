package employeeService.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import employeeService.domain.FileDownloadInfo;
import employeeService.domain.response.common.ResponseStatus;

@Service
public class AWSFileService {
	private AmazonS3 s3;
	@Autowired
	public AWSFileService(AmazonS3 s3) {
		this.s3 = s3;
	}
    private static final String BUCKET = "bfagi-userdocs";
    public static String get_bucket_name() {return BUCKET;};
	
	public ResponseStatus upload(String fileName, InputStream inputStr, Map<String, String> md){
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			md.forEach((s1, s2) -> {metadata.addUserMetadata(s1, s2);});
			PutObjectResult res = s3.putObject(BUCKET, fileName, inputStr, metadata);
			return ResponseStatus.builder().is_success(true).message("File uploaded").build();
		}catch(Exception e) {
			return ResponseStatus.builder().is_success(false).message(e.getMessage()).build();
			
		}
    }
	
    public FileDownloadInfo download(String key) {
        try {
            S3Object object = s3.getObject(BUCKET, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            ObjectMetadata omd = object.getObjectMetadata();
            FileDownloadInfo newFile = FileDownloadInfo.builder()
            		.file_name(object.getKey())
            		.file_contentType(omd.getContentType())
            		.file_contentDisposition("attachment; filename=\"" + key + "\"")
            		.file_bytestream(IOUtils.toByteArray(objectContent)).build();
            return newFile;
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }

}
