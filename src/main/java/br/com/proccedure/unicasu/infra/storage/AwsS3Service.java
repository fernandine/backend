package br.com.proccedure.unicasu.infra.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AwsS3Service {

	@Autowired
	private AmazonS3 s3Service;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	public void uploadFile(String keyName, MultipartFile file) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(file.getSize());
			s3Service.putObject(bucketName, keyName, file.getInputStream(), metadata);
		} catch(IOException ioe) {
			System.out.println("IOException: " + ioe.getMessage());
		} catch (AmazonServiceException ase) {
			throw ase;
        } catch (AmazonClientException ace) {
            throw ace;
        }
	}

}