package employeeService.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class FileDownloadInfo {
	
	private byte[] file_bytestream;
	
	private String file_name;
	
	private String file_contentType;
	
	private String file_contentDisposition;

}
