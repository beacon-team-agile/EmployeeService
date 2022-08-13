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
public class Employee {
	String Id;
	
	Integer UserId;
	
	String firstName;
	
	String lastName;
	
	String middleName;
	
	String preferredName;
	
	String email;
	
	String cellPhone;
	
	String alternatePhone;
	
	String gender;
	
	String ssn;
	
	Date dob;
	
	Date startDate;
	
	Date endDate;
	
	String driverLicense;
	
	Date driverLicenseExpiration;
	
	Integer houseId;
	
	List<Contact> contactList;

	List<Address> addressList;
	
	List<VisaStatus> visaStatusList;

	List<PersonalDocument> personalDocumentList;
}
