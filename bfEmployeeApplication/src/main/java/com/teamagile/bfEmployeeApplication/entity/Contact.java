package com.teamagile.bfEmployeeApplication.entity;

import java.sql.Date;
import java.util.List;

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
public class Contact {

	String id;
	
	String firstName;
	
	String lastName;
	
	String cellPhone;
	
	String alternatePhone;
	
	String email;
	
	String relationship;
	
	String type;
}
