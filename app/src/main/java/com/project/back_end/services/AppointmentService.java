package com.project.back_end.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.back_end.models.Appointment;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;
import com.project.back_end.repo.PatientRepository;

import jakarta.transaction.Transactional;

//1. **Add @Service Annotation**:
//- To indicate that this class is a service layer class for handling business logic.
//- The `@Service` annotation should be added before the class declaration to mark it as a Spring service component.
//- Instruction: Add `@Service` above the class definition.

@Service
public class AppointmentService {
	

    private final AppointmentRepository appointmentRepository ; // for accessing appointment data
    private final PatientRepository patientRepository ; // for accessing patient data
    private final DoctorRepository doctorRepository; // for accessing doctor data
    private final TokenService tokenService; // for extracting tokens from the request
    
// 2. **Constructor Injection for Dependencies**:
//    - The `AppointmentService` class requires several dependencies like `AppointmentRepository`, `Service`, `TokenService`, `PatientRepository`, and `DoctorRepository`.
//    - These dependencies should be injected through the constructor.
//    - Instruction: Ensure constructor injection is used for proper dependency management in Spring.
	public AppointmentService( AppointmentRepository appointmentRepository , // for accessing appointment data
			PatientRepository patientRepository ,
    		DoctorRepository doctorRepository ,
    		TokenService tokenService ) {
		this.appointmentRepository = appointmentRepository;
		this.patientRepository = patientRepository;
		this.doctorRepository = doctorRepository;
		this.tokenService = tokenService;
	}
	
// 3. **Add @Transactional Annotation for Methods that Modify Database**:
//    - The methods that modify or update the database should be annotated with `@Transactional` to ensure atomicity and consistency of the operations.
//    - Instruction: Add the `@Transactional` annotation above methods that interact with the database, especially those modifying data.

// 4. **Book Appointment Method**:
//    - Responsible for saving the new appointment to the database.
//    - If the save operation fails, it returns `0`; otherwise, it returns `1`.
//    - Instruction: Ensure that the method handles any exceptions and returns an appropriate result code.
	@Transactional
	public boolean bookAppointment(Appointment appointment) {
		try {
			appointmentRepository.save(appointment);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
// 5. **Update Appointment Method**:
//    - This method is used to update an existing appointment based on its ID.
//    - It validates whether the patient ID matches, checks if the appointment is available for updating, and ensures that the doctor is available at the specified time.
//    - If the update is successful, it saves the appointment; otherwise, it returns an appropriate error message.
//    - Instruction: Ensure proper validation and error handling is included for appointment updates.
	@Transactional
	public ResponseEntity<Map<String, String>> updateAppointment(Appointment appointment) {
		try {
			appointmentRepository.save(appointment);
			return ResponseEntity.ok().body(Map.of("success","true","message","successfully saved!"));
		} catch(Exception e) {
			return ResponseEntity.status(500).body(Map.of("success","false","message",e.getMessage()));
		}
	}

// 6. **Cancel Appointment Method**:
//    - This method cancels an appointment by deleting it from the database.
//    - It ensures the patient who owns the appointment is trying to cancel it and handles possible errors.
//    - Instruction: Make sure that the method checks for the patient ID match before deleting the appointment.
	@Transactional
	public ResponseEntity<Map<String, String>> cancelAppointment(long id) {
		Optional<Appointment> app = appointmentRepository.findById(id);
		if( app.isPresent() ) {
			try {
				appointmentRepository.delete(app.get());
				return ResponseEntity.ok().body(Map.of("success","true","message","successfully deleted!"));
			} catch(Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success","false","message",e.getMessage()));
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("success","false","message","Appointment not found"));
	}

// 7. **Get Appointments Method**:
//    - This method retrieves a list of appointments for a specific doctor on a particular day, optionally filtered by the patient's name.
//    - It uses `@Transactional` to ensure that database operations are consistent and handled in a single transaction.
//    - Instruction: Ensure the correct use of transaction boundaries, especially when querying the database for appointments.
	@Transactional
	public Map<String, Object> getAppointment(Long doctorId, String pname, LocalDate date, String token ) {
		DateTimeFormatter fmt = new DateTimeFormatterBuilder().toFormatter();
		return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
				doctorId,
				LocalDateTime.of(date, LocalTime.of(0, 0)),
				LocalDateTime.of(date, LocalTime.of(23, 59))
				).stream().collect(Collectors.toMap(o -> o.getAppointmentTimeOnly().format(fmt), o -> o));
	}

}
