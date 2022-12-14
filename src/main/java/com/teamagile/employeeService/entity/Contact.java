package com.teamagile.employeeService.entity;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class Contact {

	@Id
	Integer id;
	
	String firstName;
	
	String lastName;
	
	String cellPhone;
	
	String alternatePhone;
	
	String email;
	
	String relationship;
	
	String type;
}
