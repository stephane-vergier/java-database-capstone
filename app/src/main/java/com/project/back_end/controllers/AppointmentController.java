package com.project.back_end.controllers;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.models.Appointment;
import com.project.back_end.models.Doctor;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.services.AppointmentService;
import com.project.back_end.services.DoctorService;
import com.project.back_end.services.Service;
import com.project.back_end.services.TokenService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final DoctorService doctorService;

    private final DoctorRepository doctorRepository;

// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to define it as a REST API controller.
//    - Use `@RequestMapping("/appointments")` to set a base path for all appointment-related endpoints.
//    - This centralizes all routes that deal with booking, updating, retrieving, and canceling appointments.


// 2. Autowire Dependencies:
//    - Inject `AppointmentService` for handling the business logic specific to appointments.
//    - Inject the general `Service` class, which provides shared functionality like token validation and appointment checks.
	@Autowired
	AppointmentService  appointmentService ;

	@Autowired
	Service  service ;

	@Autowired
	TokenService  tokenService ;

    AppointmentController(DoctorRepository doctorRepository, DoctorService doctorService) {
        this.doctorRepository = doctorRepository;
        this.doctorService = doctorService;
    }

// 3. Define the `getAppointments` Method:
//    - Handles HTTP GET requests to fetch appointments based on date and patient name.
//    - Takes the appointment date, patient name, and token as path variables.
//    - First validates the token for role `"doctor"` using the `Service`.
//    - If the token is valid, returns appointments for the given patient on the specified date.
//    - If the token is invalid or expired, responds with the appropriate message and status code.
	@GetMapping("/{date}/{patientName}/{token}")
	public ResponseEntity<Map<String,Object>> getAppointments(@PathVariable String date, 
			@PathVariable String patientName, 
			@PathVariable String token ) {
		try {
			String email = tokenService.extractEmail(token);
			Doctor doctor = doctorRepository.findByEmail(email);
			LocalDate l = LocalDate.parse(date);
			return ResponseEntity.ok( appointmentService.getAppointment(doctor.getId(), patientName, l, token) );
		} catch(Exception e) {
			//
		}
		return ResponseEntity.status(500).build();
	}

// 4. Define the `bookAppointment` Method:
//    - Handles HTTP POST requests to create a new appointment.
//    - Accepts a validated `Appointment` object in the request body and a token as a path variable.
//    - Validates the token for the `"patient"` role.
//    - Uses service logic to validate the appointment data (e.g., check for doctor availability and time conflicts).
//    - Returns success if booked, or appropriate error messages if the doctor ID is invalid or the slot is already taken.
	@PostMapping("/{token}")
	public ResponseEntity<String> bookAppointment(
			@PathVariable String token,
			@RequestBody Appointment appointment ) {
		
		try {
			if( service.validateToken(token, "patient") ) {
				
				if( service.validateAppointment(appointment) == 1 ) {
					if( appointmentService.bookAppointment(appointment) ) {
						return ResponseEntity.status(HttpStatus.CREATED).body("Appointment created");
					}
				}
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid token");
		} catch(Exception e) {
			return ResponseEntity.status(500).body("Erreur interne du serveur");
		}

	}


// 5. Define the `updateAppointment` Method:
//    - Handles HTTP PUT requests to modify an existing appointment.
//    - Accepts a validated `Appointment` object and a token as input.
//    - Validates the token for `"patient"` role.
//    - Delegates the update logic to the `AppointmentService`.
//    - Returns an appropriate success or failure response based on the update result.
	@PutMapping("/{token}")
	public ResponseEntity<Map<String, String>> updateAppointment(
			@PathVariable String token,
			@RequestBody Appointment appointment ) {
		
		try {
			if( service.validateToken(token, "patient") ) {
				
				if( service.validateAppointment(appointment) == 1 ) {
					return appointmentService.updateAppointment(appointment);
				}
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch(Exception e) {
			return ResponseEntity.status(500).build();
		}

	}

// 6. Define the `cancelAppointment` Method:
//    - Handles HTTP DELETE requests to cancel a specific appointment.
//    - Accepts the appointment ID and a token as path variables.
//    - Validates the token for `"patient"` role to ensure the user is authorized to cancel the appointment.
//    - Calls `AppointmentService` to handle the cancellation process and returns the result.
	@PutMapping("/{token}")
	public ResponseEntity<Map<String, String>> cancelAppointment(
			@PathVariable String token,
			@RequestBody Appointment appointment ) {
		
		try {
			if( service.validateToken(token, "patient") ) {
				return appointmentService.cancelAppointment(appointment.getId());
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} catch(Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
}
