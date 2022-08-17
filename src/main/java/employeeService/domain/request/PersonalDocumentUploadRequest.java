package employeeService.domain.request;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonalDocumentUploadRequest {
	
	private String userid;
	
	private String title;
	
	private String comment;
	
	private MultipartFile multifile;

}
