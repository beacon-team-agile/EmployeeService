package com.teamagile.bfEmployeeApplication.domain.request;

import java.io.File;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonalDocumentUploadRequest {
	
	private String title;
	
	private String description;
	
	private String comment;

}
