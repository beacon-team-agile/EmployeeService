package com.teamagile.bfEmployeeApplication.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VisaStatus {

	String id;
	
	String visaType;
	
	Boolean activeFlag;
	
	Date startDate;
	
	Date endDate;
	
	Date lastModificationDate;
}
