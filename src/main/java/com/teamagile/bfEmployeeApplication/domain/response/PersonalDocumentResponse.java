package com.teamagile.bfEmployeeApplication.domain.response;

import java.io.File;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PersonalDocumentResponse {
	
	private String title;
	
	private String description;
	
	private String comment;

	private String createDate;
	
	private String updateDate;
	
	private File document;
}
