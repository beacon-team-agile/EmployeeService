package employeeService.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import employeeService.domain.FileDownloadInfo;
import employeeService.domain.request.PersonalDocumentUploadRequest;
import employeeService.domain.response.SingleEmployeeResponse;
import employeeService.domain.response.common.ResponseStatus;
import employeeService.entity.Employee;
import employeeService.entity.PersonalDocument;
import employeeService.repository.EmployeeRepository;
import employeeService.service.AWSFileService;

@RestController
@RequestMapping("employee/document")
public class PersonalDocumentController {
	AWSFileService fileService;
    EmployeeRepository employeeRepository;
    private static final String BUCKET = "bfagi-userdocs";
	
	@Autowired
	public PersonalDocumentController(AWSFileService fileSvc, EmployeeRepository empRepo) {
		this.fileService = fileSvc;
		this.employeeRepository = empRepo;
	}
	
	
	
    @PostMapping(value = "upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseStatus UploadNewDocument(@RequestPart MultipartFile multifile, @RequestPart String filename) throws IllegalStateException, IOException {
    	//@RequestPart PersonalDocumentUploadRequest uploadRequest, 
    	
    	Map<String, String> metadata = new HashMap<>();
        metadata.put(HttpHeaders.CONTENT_TYPE, multifile.getContentType());
        metadata.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(multifile.getSize()));
    	ResponseStatus rStatus = fileService.upload(filename, multifile.getInputStream(), metadata);
    	return rStatus;
    	
    }
    
    @PostMapping(value = "upload_to_user", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseStatus UploadNewDocumentToUser(
    		@RequestPart MultipartFile multifile
    		,@RequestPart String userid, @RequestPart String title, @RequestPart String comment) throws IllegalStateException, IOException {
    	Employee oEmployee = employeeRepository.findById(userid).orElseThrow(); 
    	String fileTitle = oEmployee.getUserId() + "_" +  title;
    	
    	Map<String, String> metadata = new HashMap<>();
        metadata.put(HttpHeaders.CONTENT_TYPE, multifile.getContentType());
        metadata.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(multifile.getSize()));
        
        
    	ResponseStatus rStatus = fileService.upload(fileTitle, multifile.getInputStream(), metadata);
    	if(rStatus.is_success()) {
    		List<PersonalDocument> pd = oEmployee.getPersonalDocument();
    		pd.add(PersonalDocument.builder()
    				.path(fileTitle)
    				.title(title)
    				.comment(comment)
    				.createDate(Calendar.getInstance().toString())
    				.build());
        	return ResponseStatus.builder().is_success(true).message(rStatus.getMessage()).build();
 
    	}else {
    		return rStatus;
    	}
    	
    }
    
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String filename) {
      FileDownloadInfo newFile = fileService.download(filename);
      byte[] b = newFile.getFile_bytestream();
      ByteArrayResource resource = new ByteArrayResource(b);
      return ResponseEntity
          .ok()
          .contentLength(b.length)
          .header(HttpHeaders.CONTENT_TYPE, newFile.getFile_contentType())
          .header(HttpHeaders.CONTENT_DISPOSITION, newFile.getFile_contentDisposition())
          .body(resource);
    }

}
