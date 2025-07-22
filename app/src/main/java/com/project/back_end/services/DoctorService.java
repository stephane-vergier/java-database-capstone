package com.project.back_end.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.back_end.DTO.Login;
import com.project.back_end.models.Doctor;
import com.project.back_end.repo.AppointmentRepository;
import com.project.back_end.repo.DoctorRepository;

import jakarta.transaction.Transactional;

@Service
public class DoctorService {

    private final Service service;

	private final DoctorRepository  doctorRepository;
	private final AppointmentRepository appointmentRepository;
	private final TokenService tokenService;

// 1. **Add @Service Annotation**:
//    - This class should be annotated with `@Service` to indicate that it is a service layer class.
//    - The `@Service` annotation marks this class as a Spring-managed bean for business logic.
//    - Instruction: Add `@Service` above the class declaration.

	public DoctorService(DoctorRepository  doctorRepository,
			AppointmentRepository appointmentRepository,
			TokenService tokenService
			, Service service) {
		this.doctorRepository = doctorRepository;
		this.appointmentRepository = appointmentRepository;
		this.tokenService = tokenService;
		this.service = service;
	}
	
// 2. **Constructor Injection for Dependencies**:
//    - The `DoctorService` class depends on `DoctorRepository`, `AppointmentRepository`, and `TokenService`.
//    - These dependencies should be injected via the constructor for proper dependency management.
//    - Instruction: Ensure constructor injection is used for injecting dependencies into the service.

// 3. **Add @Transactional Annotation for Methods that Modify or Fetch Database Data**:
//    - Methods like `getDoctorAvailability`, `getDoctors`, `findDoctorByName`, `filterDoctorsBy*` should be annotated with `@Transactional`.
//    - The `@Transactional` annotation ensures that database operations are consistent and wrapped in a single transaction.
//    - Instruction: Add the `@Transactional` annotation above the methods that perform database operations or queries.

// 4. **getDoctorAvailability Method**:
//    - Retrieves the available time slots for a specific doctor on a particular date and filters out already booked slots.
//    - The method fetches all appointments for the doctor on the given date and calculates the availability by comparing against booked slots.
//    - Instruction: Ensure that the time slots are properly formatted and the available slots are correctly filtered.

	public List<String> getDoctorAvailability() {
		return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(null, null, null)
				.stream()
				.map(app->app.getAppointmentTime().toString())
				.toList();
	}
	
// 5. **saveDoctor Method**:
//    - Used to save a new doctor record in the database after checking if a doctor with the same email already exists.
//    - If a doctor with the same email is found, it returns `-1` to indicate conflict; `1` for success, and `0` for internal errors.
//    - Instruction: Ensure that the method correctly handles conflicts and exceptions when saving a doctor.
	@Transactional
	public int saveDoctor(Doctor doctor) {
		Doctor opt = doctorRepository.findByEmail(doctor.getEmail());
		if( doctor == null ) {
	 		try {
				doctorRepository.save(doctor);
				return 1;
			} catch(Exception e) {
			}
		}
		return 0;
	}
	
// 6. **updateDoctor Method**:
//    - Updates an existing doctor's details in the database. If the doctor doesn't exist, it returns `-1`.
//    - Instruction: Make sure that the doctor exists before attempting to save the updated record and handle any errors properly.
	@Transactional
	public int updateDoctor(Doctor doctor) {
		Optional<Doctor> opt = doctorRepository.findById(doctor.getId());
		if( opt.isPresent() ) {
	 		try {
				doctorRepository.save(doctor);
				return 1;
			} catch(Exception e) {
				return 0;
			}
		}
		return -1;
	}
	
// 7. **getDoctors Method**:
//    - Fetches all doctors from the database. It is marked with `@Transactional` to ensure that the collection is properly loaded.
//    - Instruction: Ensure that the collection is eagerly loaded, especially if dealing with lazy-loaded relationships (e.g., available times). 
	public List<Doctor> getDoctors() {
		return doctorRepository.findAll();
	}
// 8. **deleteDoctor Method**:
//    - Deletes a doctor from the system along with all appointments associated with that doctor.
//    - It first checks if the doctor exists. If not, it returns `-1`; otherwise, it deletes the doctor and their appointments.
//    - Instruction: Ensure the doctor and their appointments are deleted properly, with error handling for internal issues.
	@Transactional
	public int deleteDoctor(long doctorId) {
		Optional<Doctor> opt = doctorRepository.findById(doctorId);
		if( opt.isPresent() ) {
	 		try {
				doctorRepository.delete(opt.get());
				return 1;
			} catch(Exception e) {
				return 0;
			}
		}
		return -1;
	}

// 9. **validateDoctor Method**:
//    - Validates a doctor's login by checking if the email and password match an existing doctor record.
//    - It generates a token for the doctor if the login is successful, otherwise returns an error message.
//    - Instruction: Make sure to handle invalid login attempts and password mismatches properly with error responses.
	public ResponseEntity<Map<String, String>> validateDoctor(Login login) {
		Doctor d = doctorRepository.findByEmail(login.getIdentifier());
		if( d != null ) {
			if( d.getPassword().equals(login.getPassword()) ) {
				String token = tokenService.generateToken(login.getIdentifier());
				return ResponseEntity.status(500).body(Map.of("success","true","token",token));
			}
		}
		return ResponseEntity.status(500).body(Map.of("success","false","message","Invalid credentials!"));
	}
// 10. **findDoctorByName Method**:
//    - Finds doctors based on partial name matching and returns the list of doctors with their available times.
//    - This method is annotated with `@Transactional` to ensure that the database query and data retrieval are properly managed within a transaction.
//    - Instruction: Ensure that available times are eagerly loaded for the doctors.
	public Map<String, Object> findDoctorByName(String name) {
		return doctorRepository.findByNameLike(name).stream().collect(
				Collectors.toMap( o -> o.getName() , o -> o )
				);
				
	}

// 11. **filterDoctorsByNameSpecilityandTime Method**:
//    - Filters doctors based on their name, specialty, and availability during a specific time (AM/PM).
//    - The method fetches doctors matching the name and specialty criteria, then filters them based on their availability during the specified time period.
//    - Instruction: Ensure proper filtering based on both the name and specialty as well as the specified time period.
	public Map<String, Object> filterDoctorsByNameSpecilityandTime(String name, String specialty, String amOrPm) {
		return doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name,specialty)
				.stream()
				.filter( o -> o.getAvailableTimes().size() > 0 ) // FIX IT
				.collect(
				Collectors.toMap( o -> o.getName() , o -> o )
				);
	}
// 12. **filterDoctorByTime Method**:
//    - Filters a list of doctors based on whether their available times match the specified time period (AM/PM).
//    - This method processes a list of doctors and their available times to return those that fit the time criteria.
//    - Instruction: Ensure that the time filtering logic correctly handles both AM and PM time slots and edge cases.

// 13. **filterDoctorByNameAndTime Method**:
//    - Filters doctors based on their name and the specified time period (AM/PM).
//    - Fetches doctors based on partial name matching and filters the results to include only those available during the specified time period.
//    - Instruction: Ensure that the method correctly filters doctors based on the given name and time of day (AM/PM).
	
	public Map<String, Object> filterDoctorByNameAndTime(String name, String amOrPm) {
		return doctorRepository.findByNameLike(name)
				.stream()
				.filter( o -> o.getAvailableTimes().size() > 0 ) // FIX IT
				.collect(
				Collectors.toMap( o -> o.getName() , o -> o )
				);
	}
// 14. **filterDoctorByNameAndSpecility Method**:
//    - Filters doctors by name and specialty.
//    - It ensures that the resulting list of doctors matches both the name (case-insensitive) and the specified specialty.
//    - Instruction: Ensure that both name and specialty are considered when filtering doctors.
	public Map<String, Object> filterDoctorByNameAndSpecility(String name, String specialty) {
		return doctorRepository.findByNameContainingIgnoreCaseAndSpecialtyIgnoreCase(name,specialty)
				.stream()
				.collect(
				Collectors.toMap( o -> o.getName() , o -> o )
				);
	}
	

// 15. **filterDoctorByTimeAndSpecility Method**:
//    - Filters doctors based on their specialty and availability during a specific time period (AM/PM).
//    - Fetches doctors based on the specified specialty and filters them based on their available time slots for AM/PM.
//    - Instruction: Ensure the time filtering is accurately applied based on the given specialty and time period (AM/PM).
	public Map<String, Object> filterDoctorByTimeAndSpecility(String specialty, String amOrPm) {
		return doctorRepository.findBySpecialtyIgnoreCase(specialty)
				.stream()
				.filter( o -> o.getAvailableTimes().size() > 0 ) // FIX IT
				.collect(
				Collectors.toMap( o -> o.getName() , o -> o )
				);
	}
	
// 16. **filterDoctorBySpecility Method**:
//    - Filters doctors based on their specialty.
//    - This method fetches all doctors matching the specified specialty and returns them.
//    - Instruction: Make sure the filtering logic works for case-insensitive specialty matching.
	public Map<String, Object> filterDoctorBySpecility(String specialty) {
		return doctorRepository.findBySpecialtyIgnoreCase(specialty)
				.stream()
				.collect(
						Collectors.toMap( o -> o.getName() , o -> o )
				);
	}
// 17. **filterDoctorsByTime Method**:
//    - Filters all doctors based on their availability during a specific time period (AM/PM).
//    - The method checks all doctors' available times and returns those available during the specified time period.
//    - Instruction: Ensure proper filtering logic to handle AM/PM time periods.
	public Map<String, Object> filterDoctorsByTime(String amOrPm) {
		return filterDoctorsByTime(doctorRepository.findAll()
				.stream(), amOrPm )
				.collect(
						Collectors.toMap( o -> o.getName() , o -> o )
				);
	}

	private Stream<Doctor> filterDoctorsByTime(Stream<Doctor> docs,String amOrPm) {
		return docs.filter( d -> true );
	}

   
}
