package com.project.back_end.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.back_end.models.Prescription;
import com.project.back_end.services.AppointmentService;
import com.project.back_end.services.PrescriptionService;
import com.project.back_end.services.Service;

@RestController
@RequestMapping("${api.path}" + "prescription")
public class PrescriptionController {
    
// 1. Set Up the Controller Class:
//    - Annotate the class with `@RestController` to define it as a REST API controller.
//    - Use `@RequestMapping("${api.path}prescription")` to set the base path for all prescription-related endpoints.
//    - This controller manages creating and retrieving prescriptions tied to appointments.


// 2. Autowire Dependencies:
//    - Inject `PrescriptionService` to handle logic related to saving and fetching prescriptions.
//    - Inject the shared `Service` class for token validation and role-based access control.
//    - Inject `AppointmentService` to update appointment status after a prescription is issued.
	@Autowired
	PrescriptionService prescriptionService;
	@Autowired
	Service service;
	@Autowired
	AppointmentService appointmentService;

// 3. Define the `savePrescription` Method:
//    - Handles HTTP POST requests to save a new prescription for a given appointment.
//    - Accepts a validated `Prescription` object in the request body and a doctor’s token as a path variable.
//    - Validates the token for the `"doctor"` role.
//    - If the token is valid, updates the status of the corresponding appointment to reflect that a prescription has been added.
//    - Delegates the saving logic to `PrescriptionService` and returns a response indicating success or failure.
	@PostMapping("/{token}")
	public ResponseEntity<String> savePrescription(@RequestBody Prescription presription,
		@PathVariable String token) {
		if( service.validateToken(token, "doctor")) {
			try {
				prescriptionService.savePrescription(presription);
				return ResponseEntity.status(HttpStatus.CREATED).body("Prescription created");
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Can not save!");
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Authorized!");
	}

// 4. Define the `getPrescription` Method:
//    - Handles HTTP GET requests to retrieve a prescription by its associated appointment ID.
//    - Accepts the appointment ID and a doctor’s token as path variables.
//    - Validates the token for the `"doctor"` role using the shared service.
//    - If the token is valid, fetches the prescription using the `PrescriptionService`.
//    - Returns the prescription details or an appropriate error message if validation fails.
	@GetMapping("/{appointmentId}/{token}")
	public ResponseEntity<Map<String, Object>> getPrescription(@PathVariable Long appointmentId,
		@PathVariable String token) {
		if( service.validateToken(token, "doctor")) {
			try {
				ResponseEntity<Map<String, Object>> res = prescriptionService.getPrescription(appointmentId);
				if( "true".equals(res.getBody().get("success"))) {
					return res;
				}
				return ResponseEntity.status(HttpStatus.CREATED).body( Map.of("success","false","message","No prescriptions found"));
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.CREATED).body( Map.of("success","false","message","internal error"));
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED).body( Map.of("success","false","message","Not Authorized"));
	}


}
