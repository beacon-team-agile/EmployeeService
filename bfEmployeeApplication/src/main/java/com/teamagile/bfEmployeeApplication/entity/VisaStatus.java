package com.teamagile.bfEmployeeApplication.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VisaStatus {


	@Id
	Integer id;
	
	String visaType;
	
	Boolean activeFlag;


	String startDate;

	String endDate;

	String lastModificationDate;
}
