package com.teamagile.bfEmployeeApplication.entity;

import java.sql.Date;
import java.util.List;

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
public class Address {
	@Id
	Integer id;
	
	String addressLine1;
	
	String addressLine2;
	
	String city;
	
	String state;
	
	String zipCode;

}