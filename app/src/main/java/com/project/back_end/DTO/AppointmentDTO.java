package com.project.back_end.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentDTO {
// 1. 'id' field:
//    - Type: private Long
//    - Description:
//      - Represents the unique identifier for the appointment.
//      - This is the primary key for identifying the appointment in the system.

// 2. 'doctorId' field:
//    - Type: private Long
//    - Description:
//      - Represents the ID of the doctor associated with the appointment.
//      - This is a simplified field, capturing only the ID of the doctor (not the full Doctor object).

// 3. 'doctorName' field:
//    - Type: private String
//    - Description:
//      - Represents the name of the doctor associated with the appointment.
//      - This is a simplified field for displaying the doctor's name.

// 4. 'patientId' field:
//    - Type: private Long
//    - Description:
//      - Represents the ID of the patient associated with the appointment.
//      - This is a simplified field, capturing only the ID of the patient (not the full Patient object).

// 5. 'patientName' field:
//    - Type: private String
//    - Description:
//      - Represents the name of the patient associated with the appointment.
//      - This is a simplified field for displaying the patient's name.

// 6. 'patientEmail' field:
//    - Type: private String
//    - Description:
//      - Represents the email of the patient associated with the appointment.
//      - This is a simplified field for displaying the patient's email.

// 7. 'patientPhone' field:
//    - Type: private String
//    - Description:
//      - Represents the phone number of the patient associated with the appointment.
//      - This is a simplified field for displaying the patient's phone number.

// 8. 'patientAddress' field:
//    - Type: private String
//    - Description:
//      - Represents the address of the patient associated with the appointment.
//      - This is a simplified field for displaying the patient's address.

// 9. 'appointmentTime' field:
//    - Type: private LocalDateTime
//    - Description:
//      - Represents the scheduled date and time of the appointment.
//      - The time when the appointment is supposed to happen, stored as a LocalDateTime object.

// 10. 'status' field:
//    - Type: private int
//    - Description:
//      - Represents the status of the appointment.
//      - Status can indicate if the appointment is "Scheduled:0", "Completed:1", or other statuses (e.g., "Canceled") as needed.

// 11. 'appointmentDate' field (Custom Getter):
//    - Type: private LocalDate
//    - Description:
//      - A derived field representing only the date part of the appointment (without the time).
//      - Extracted from the 'appointmentTime' field.

// 12. 'appointmentTimeOnly' field (Custom Getter):
//    - Type: private LocalTime
//    - Description:
//      - A derived field representing only the time part of the appointment (without the date).
//      - Extracted from the 'appointmentTime' field.

// 13. 'endTime' field (Custom Getter):
//    - Type: private LocalDateTime
//    - Description:
//      - A derived field representing the end time of the appointment.
//      - Calculated by adding 1 hour to the 'appointmentTime' field.

// 14. Constructor:
//    - The constructor accepts all the relevant fields for the AppointmentDTO, including simplified fields for the doctor and patient (ID, name, etc.).
//    - It also calculates custom fields: 'appointmentDate', 'appointmentTimeOnly', and 'endTime' based on the 'appointmentTime' field.

// 15. Getters:
//    - Standard getter methods are provided for all fields: id, doctorId, doctorName, patientId, patientName, patientEmail, patientPhone, patientAddress, appointmentTime, status, appointmentDate, appointmentTimeOnly, and endTime.
//    - These methods allow access to the values of the fields in the AppointmentDTO object.


    private Long id; //  – Unique identifier for the appointment
    private Long doctorId; //  – ID of the doctor assigned to the appointment
    private String doctorName; //  – Full name of the doctor
    private Long patientId; //  – ID of the patient
    private String patientName; //  – Full name of the patient
    private String patientEmail; //  – Email address of the patient
    private String patientPhone; //  – Contact number of the patient
    private String patientAddress; //  – Residential address of the patient
    private LocalDateTime appointmentTime; //  – Full date and time of the appointment
    private int status; //  – Appointment status (e.g., scheduled, completed)
    private LocalDate appointmentDate; //  – Extracted date from appointmentTime
    private LocalTime appointmentTimeOnly; //  – Extracted time from appointmentTime
    private LocalDateTime endTime; //  – Calculated as appointmentTime + 1 hour
    
    public AppointmentDTO() {
    	appointmentTime = LocalDateTime.now();
        appointmentDate = appointmentTime.toLocalDate();
        appointmentTimeOnly = appointmentTime.toLocalTime();
        endTime = appointmentTime.plusHours(1);
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public String getPatientPhone() {
		return patientPhone;
	}
	public void setPatientPhone(String patientPhone) {
		this.patientPhone = patientPhone;
	}
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public LocalTime getAppointmentTimeOnly() {
		return appointmentTimeOnly;
	}
	public void setAppointmentTimeOnly(LocalTime appointmentTimeOnly) {
		this.appointmentTimeOnly = appointmentTimeOnly;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
}
