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
public class Address {
	
	String id;
	
	String addressLine1;
	
	String addressLine2;
	
	String city;
	
	String state;
	
	Integer zipCode;

}
