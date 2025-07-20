# User Stories

## Admin User Stories

## Admin Login
**Title:**
_As a Administrator, I want to log into the portal with my username and password to manage the platform securely._

**Acceptance Criteria:**
1. Administrator can enter email
2. Administrator email must be unique
3. Administrator can enter password
4. Administrator can save his account

**Priority:** High
**Story Points:** 3
**Notes:**
- Role ADMIN

## Admin Logout
**Title:**
_As a Administrator, I want to Log out of the portal to protect system access._

**Acceptance Criteria:**
1. Administrator can click on lougout button
2. Once logged out, user is redirected to the default list

**Priority:** High
**Story Points:** 3
**Notes:**

## Admin Add doctor
**Title:**
_As a Administrator, I want to add a doctor's profile to the portal, so that the patient can book appointment with him._

**Acceptance Criteria:**
1. Administrator can create a profile page for the new doctor
2. Administrator can save or cancel the new profile
3. The new doctor is displayed i the list of doctor

**Priority:** Medium

**Story Points:** 5

**Notes:**
- Role ADMIN

## Admin Remove doctor
**Title:**
_As a Administrator, I want to delete doctor's profile from the portal, so that the patient cannot select him anymore._

**Acceptance Criteria:**
1. Administrator can select a doctor in the list of doctors
2. Administrator can delete the doctor's profile
3. Once removed, the doctor does not appear in the list of doctor anymore

**Priority:** Medium

**Story Points:** 5

**Notes:**
- Role ADMIN

## Admin Get statistics
**Title:**
_As a Administrator, I want to run a stored procedure in MySQL CLI to get the number of appointments per month and track usage statistics._

**Acceptance Criteria:**
1. Administrator can run stored procedures in MySQL CLI
2. stored procedures to track # of appointments and usage are created in MySQL DB

**Priority:** Low

**Story Points:** 3

**Notes:**


## Patient User Stories

## Explore
**Title:**
_As a Patient, I want to view a list of doctors without logging, so that i can explore options before registering._

**Acceptance Criteria:**
1. Unregistered users can see the list of doctors

**Priority:** High

**Story Points:** 3

**Notes:**

## Sign-in
**Title:**
_As a Patient, I want to Sign up using my email and password , so that i can book appointments._

**Acceptance Criteria:**
1. User can enter email
2. User email must be unique
3. User can enter password
4. User can save his account

**Priority:** High

**Story Points:** 3

**Notes:**
- Role PATIENT

## Patient login
**Title:**
_As a Patient, I want to log to the portal, so that i ca manage my bookings._

**Acceptance Criteria:**
1. User can enter email
2. User can enter password
3. User can submit email and password
4. if User is recognized he can access his bookings page
4. if User is NOT recognized a message is displayed

**Priority:** High

**Story Points:** 3

**Notes:**

## Patient log out
**Title:**
_As a Patient, I want to log out, so that i keep my account secure._

**Acceptance Criteria:**
1. Use can click on lougout button
2. Once logged out, user can not access his bookings and returns to the default list

**Priority:** High

**Story Points:** 3

**Notes:**

## Book an hour long appointment
**Title:**
_As a Patient, I want to log-in and book an hour long appointlment, so that i can see the doctor._

**Acceptance Criteria:**
1. User can select a doctor
2. List of availabilities is shown for the doctor
3. User can select a appointment when the doctor is available
4. User can submit his selction
5. New appointment appears in user's bookings list

**Priority:** High

**Story Points:** 3

**Notes:**

## Doctor User Stories

## Doctor Login
**Title:**
_As a Doctor, I want to login using my amil and password, so that i manage my appointments._

**Acceptance Criteria:**
1. Doctor can enter email
2. Doctor can enter password
3. Doctor can submit email and password
4. if Doctor is recognized he can access his bookings page
4. if Doctor is NOT recognized a message is displayed

**Priority:** High

**Story Points:** 3

**Notes:**
- Role DOCTOR

## Doctor Logout
**Title:**
_As a Doctor, I want to log out, so that i secure my account._

**Acceptance Criteria:**
1. Doctor can click on log out button
2. Once logged out, the doctor is redirected to default list.

**Priority:** High

**Story Points:** 3

**Notes:**

## Admin Add doctor
**Title:**
_As a Doctor, I want to ciew my appointment calenda, so that i can stay organized._

**Acceptance Criteria:**
1. Appointments are shown in a calendar

**Priority:** High

**Story Points:** 3

**Notes:**

## Doctor update profile
**Title:**
_As a Doctor, I want to update my profile with specialization and contact information so that patients have up-to-date information ._

**Acceptance Criteria:**
1. Doctor can access its profile page in edit mode
2. Doctor can change its specialization
3. Doctor can change its contact info
4. Doctor can save or cancel his changes.

**Priority:** Medium

**Story Points:** 3

**Notes:**

## Doctor
**Title:**
_As a Doctor, I want to mark my unavailabilty, so that patients are informed of only the available slots._

**Acceptance Criteria:**
1. Doctor can select the start date/time of his unavailabilty 
2. Doctor can select the end date/time of his unavailabilty 
3. Doctor can save his unavailabilty 

**Priority:** Medium

**Story Points:** 3

**Notes:**
