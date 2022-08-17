package employeeService.domain.request;

import java.io.File;

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

}
