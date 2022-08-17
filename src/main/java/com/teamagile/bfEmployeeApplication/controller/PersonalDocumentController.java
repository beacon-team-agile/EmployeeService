package com.teamagile.bfEmployeeApplication.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.teamagile.bfEmployeeApplication.domain.FileDownloadInfo;
import com.teamagile.bfEmployeeApplication.domain.request.PersonalDocumentUploadRequest;
import com.teamagile.bfEmployeeApplication.domain.response.SingleEmployeeResponse;
import com.teamagile.bfEmployeeApplication.domain.response.common.ResponseStatus;
import com.teamagile.bfEmployeeApplication.entity.Employee;
import com.teamagile.bfEmployeeApplication.entity.PersonalDocument;
import com.teamagile.bfEmployeeApplication.repository.EmployeeRepository;
import com.teamagile.bfEmployeeApplication.service.AWSFileService;

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
    public ResponseStatus UploadNewDocument(@RequestPart MultipartFile multiFile, @RequestParam String fileName) throws IllegalStateException, IOException {
    	//@RequestPart PersonalDocumentUploadRequest uploadRequest, 
    	
    	Map<String, String> metadata = new HashMap<>();
        metadata.put(HttpHeaders.CONTENT_TYPE, multiFile.getContentType());
        metadata.put(HttpHeaders.CONTENT_LENGTH, String.valueOf(multiFile.getSize()));
    	fileService.upload(fileName, multiFile.getInputStream(), metadata);
    	return ResponseStatus.builder().is_success(true).message("test").build();
    	
    }
    
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String fileName) {
      FileDownloadInfo newFile = fileService.download(fileName);
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
