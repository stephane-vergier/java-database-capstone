package com.project.back_end.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.models.Patient;
import com.project.back_end.services.PatientService;
import com.project.back_end.services.Service;
import com.project.back_end.services.TokenService;

@RestController
@RequestMapping("/patient")
public class PatientController {

// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to define it as a REST API controller for patient-related operations.
//    - Use `@RequestMapping("/patient")` to prefix all endpoints with `/patient`, grouping all patient functionalities under a common route.


// 2. Autowire Dependencies:
//    - Inject `PatientService` to handle patient-specific logic such as creation, retrieval, and appointments.
//    - Inject the shared `Service` class for tasks like token validation and login authentication.

	@Autowired
	PatientService patientService;
	
	@Autowired
	Service service;

	@Autowired
	TokenService tokenService;

// 3. Define the `getPatient` Method:
//    - Handles HTTP GET requests to retrieve patient details using a token.
//    - Validates the token for the `"patient"` role using the shared service.
//    - If the token is valid, returns patient information; otherwise, returns an appropriate error message.

	@GetMapping("/{token}")
	public ResponseEntity<Map<String,Object>> getPatient(@PathVariable String token) {
		if( service.validateToken(token, "patient")) {
			return patientService.getPatientDetails(token);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
// 4. Define the `createPatient` Method:
//    - Handles HTTP POST requests for patient registration.
//    - Accepts a validated `Patient` object in the request body.
//    - First checks if the patient already exists using the shared service.
//    - If validation passes, attempts to create the patient and returns success or error messages based on the outcome.
	@GetMapping("/{token}")
	public ResponseEntity<Map<String,Object>> createPatient(@PathVariable String token,
			@RequestBody Patient patient) {
		if( service.validateToken(token, "admin")) {
			if( patientService.createPatient(patient) == 1 ) {
				return ResponseEntity.ok(Map.of("success","true"));
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}


// 5. Define the `login` Method:
//    - Handles HTTP POST requests for patient login.
//    - Accepts a `Login` DTO containing email/username and password.
//    - Delegates authentication to the `validatePatientLogin` method in the shared service.
//    - Returns a response with a token or an error message depending on login success.
	@GetMapping("/{token}")
	public ResponseEntity<Map<String,Object>> login(@PathVariable String token) {
		if( service.validateToken(token, "patient")) {
			
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}


// 6. Define the `getPatientAppointment` Method:
//    - Handles HTTP GET requests to fetch appointment details for a specific patient.
//    - Requires the patient ID, token, and user role as path variables.
//    - Validates the token using the shared service.
//    - If valid, retrieves the patient's appointment data from `PatientService`; otherwise, returns a validation error.
	@GetMapping("/{token}")
	public ResponseEntity<Map<String,Object>> getPatientAppointment(@PathVariable String token) {
		if( service.validateToken(token, "patient")) {
			
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}


// 7. Define the `filterPatientAppointment` Method:
//    - Handles HTTP GET requests to filter a patient's appointments based on specific conditions.
//    - Accepts filtering parameters: `condition`, `name`, and a token.
//    - Token must be valid for a `"patient"` role.
//    - If valid, delegates filtering logic to the shared service and returns the filtered result.
	@GetMapping("/{token}")
	public ResponseEntity<Map<String,Object>> filterPatientAppointment(@PathVariable String token) {
		if( service.validateToken(token, "patient")) {
			
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}



}


