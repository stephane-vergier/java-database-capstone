package com.project.back_end.DTO;

public class Login {
    
// 1. 'email' field:
//    - Type: private String
//    - Description:
//      - Represents the email address used for logging into the system.
//      - The email field is expected to contain a valid email address for user authentication purposes.

// 2. 'password' field:
//    - Type: private String
//    - Description:
//      - Represents the password associated with the email address.
//      - The password field is used for verifying the user's identity during login.
//      - It is generally hashed before being stored and compared during authentication.

// 3. Constructor:
//    - No explicit constructor is defined for this class, as it relies on the default constructor provided by Java.
//    - This class can be initialized with setters or directly via reflection, as per the application's needs.

// 4. Getters and Setters:
//    - Standard getter and setter methods are provided for both 'email' and 'password' fields.
//    - The 'getEmail()' method allows access to the email value.
//    - The 'setEmail(String email)' method sets the email value.
//    - The 'getPassword()' method allows access to the password value.
//    - The 'setPassword(String password)' method sets the password value.


    private String identifier; // The unique identifier of the user attempting to log in (email for Doctor/Patient, username for Admin)
    private String password; // The password provided by the user
    
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
    

}
