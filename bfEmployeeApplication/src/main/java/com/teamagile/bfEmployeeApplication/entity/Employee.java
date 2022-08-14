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
public class Employee {
	@Id
	String Id;
	
	Integer userId;
	
	String firstName;
	
	String lastName;
	
	String middleName;
	
	String preferredName;
	
	String email;
	
	String cellPhone;
	
	String alternatePhone;
	
	String gender;
	
	String ssn;
	
	String dob;

	String startDate;

	String endDate;
	
	String driverLicense;

	String driverLicenseExpiration;
	
	Integer houseId;
	
	List<Contact> contact;

	List<Address> address;
	
	List<VisaStatus> visaStatus;

	List<PersonalDocument> personalDocument;
}
