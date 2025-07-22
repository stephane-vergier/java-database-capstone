package com.project.back_end.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Doctor;
import com.project.back_end.services.DoctorService;
import com.project.back_end.services.PrescriptionService;
import com.project.back_end.services.Service;

@RestController
@RequestMapping("${api.path}" + "doctor") 
public class DoctorController {

// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to define it as a REST controller that serves JSON responses.
//    - Use `@RequestMapping("${api.path}doctor")` to prefix all endpoints with a configurable API path followed by "doctor".
//    - This class manages doctor-related functionalities such as registration, login, updates, and availability.

	@Autowired
	DoctorService doctorService;
	@Autowired
	Service service;

// 2. Autowire Dependencies:
//    - Inject `DoctorService` for handling the core logic related to doctors (e.g., CRUD operations, authentication).
//    - Inject the shared `Service` class for general-purpose features like token validation and filtering.


// 3. Define the `getDoctorAvailability` Method:
//    - Handles HTTP GET requests to check a specific doctor’s availability on a given date.
//    - Requires `user` type, `doctorId`, `date`, and `token` as path variables.
//    - First validates the token against the user type.
//    - If the token is invalid, returns an error response; otherwise, returns the availability status for the doctor.


// 4. Define the `getDoctor` Method:
//    - Handles HTTP GET requests to retrieve a list of all doctors.
//    - Returns the list within a response map under the key `"doctors"` with HTTP 200 OK status.
	@GetMapping("/availability/{user}/{doctorId}/{date}/{token}")
	public ResponseEntity<Map<String,Object>> getDoctor( @PathVariable String user,
			@PathVariable Long doctorId,
			@PathVariable String date,
			@PathVariable String token
			) {
		try {
			if( service.validateToken(token, "patient") ) {
				boolean found = false;
				for(String avail : doctorService.getDoctorAvailability() ) {
					if( avail.equals(date) ) {
						found = true;
					}
				}
				if( found ) {
					return ResponseEntity.ok(Map.of("success","true","message","appointment available"));
				} else {
					return ResponseEntity.ok(Map.of("success","false","message","not abailable"));
				}
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
 	

// 5. Define the `saveDoctor` Method:
//    - Handles HTTP POST requests to register a new doctor.
//    - Accepts a validated `Doctor` object in the request body and a token for authorization.
//    - Validates the token for the `"admin"` role before proceeding.
//    - If the doctor already exists, returns a conflict response; otherwise, adds the doctor and returns a success message.
	@GetMapping
	public ResponseEntity<Map<String,Object>> getDoctors() {
		try {
			List<Doctor> doctors = doctorService.getDoctors();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
					Map.of("success",true,"doctors",doctors)
					);
		} catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/{token}")
	public ResponseEntity<Map<String,Object>> addNewDoctor(
			@RequestBody Doctor doctor,
			@PathVariable String token
			) {
		try {
			if( doctorService.saveDoctor(doctor) == 1 ) {
				return ResponseEntity.status(HttpStatus.CREATED).body(Map.of( "success","true") );
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( "success","false") );
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( "success","false") );
		}
	}



// 6. Define the `doctorLogin` Method:
//    - Handles HTTP POST requests for doctor login.
//    - Accepts a validated `Login` DTO containing credentials.
//    - Delegates authentication to the `DoctorService` and returns login status and token information.
	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> doctorLogin(@RequestBody Login login) {
		try {
			return doctorService.validateDoctor(login);
		} catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
 	


// 7. Define the `updateDoctor` Method:
//    - Handles HTTP PUT requests to update an existing doctor's information.
//    - Accepts a validated `Doctor` object and a token for authorization.
//    - Token must belong to an `"admin"`.
//    - If the doctor exists, updates the record and returns success; otherwise, returns not found or error messages.
	@PutMapping("/{token}")
	public ResponseEntity<Map<String,Object>> updateDoctor(
			@RequestBody Doctor doctor,
			@PathVariable String token
			) {
		if( service.validateToken(token, "doctor") ) {
			try {
				if( doctorService.updateDoctor(doctor) == 1 ) {
					return ResponseEntity.status(HttpStatus.OK).body(Map.of( "success","true") );
				}
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( "success","false") );
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( "success","false") );
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of( "success","false") );
	}

// 8. Define the `deleteDoctor` Method:
//    - Handles HTTP DELETE requests to remove a doctor by ID.
//    - Requires both doctor ID and an admin token as path variables.
//    - If the doctor exists, deletes the record and returns a success message; otherwise, responds with a not found or error message.
	@DeleteMapping("/{id}/{token}")
	public ResponseEntity<Map<String,Object>> deleteDoctor(
			@RequestBody Long id,
			@PathVariable String token
			) {
		if( service.validateToken(token, "doctor") ) {
			try {
				if( doctorService.deleteDoctor(id) == 1 ) {
					return ResponseEntity.status(HttpStatus.OK).body(Map.of( "success","true") );
				}
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( "success","true") );
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of( "success","false") );
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of( "success","false") );
	}
 	


// 9. Define the `filter` Method:
//    - Handles HTTP GET requests to filter doctors based on name, time, and specialty.
//    - Accepts `name`, `time`, and `speciality` as path variables.
//    - Calls the shared `Service` to perform filtering logic and returns matching doctors in the response.
	@GetMapping("/filter/{name}/{time}/{speciality}")
	public ResponseEntity<Map<String,Object>> filter( @PathVariable String user,
			@PathVariable String name,
			@PathVariable String time,
			@PathVariable String speciality
			) {
		try {
			return ResponseEntity.ok( service.filterDoctor(name, speciality, time) );
		} catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
 	


}
