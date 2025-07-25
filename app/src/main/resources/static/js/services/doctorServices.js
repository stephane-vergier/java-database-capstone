import { API_BASE_URL } from "../config/config.js";
const DOCTOR_API = API_BASE_URL + '/doctor'

async function getDoctors() {
	try {
		const response = await fetch(DOCTOR_API);
		const json = await response.json();
		return json;
	} catch(e) {
		console.error(e);
	}
	return [];
}

async function deleteDoctor(id, token) {
	try {
		const response = await fetch(DOCTOR_API + "/" + token + "/" + id,{
			"method": "DELETE",
		});
		const json = await response.json();
		return { "success": true , "message" : "Doctor successfully deleted!" };
	} catch(e) {
		console.error(e);
		return { "success": false , "message" : "Doctor deletion failed!", error: e };
	}
}

async function saveDoctor(doctor, token) {
	try {
		const response = await fetch(DOCTOR_API + "/" + token,{
			"method": "POST",
			"body": JSON.stringify( doctor )
		});
		const json = await response.json();
		return { "success": true , "message" : "Doctor successfully created!", "doctor": json };
	} catch(e) {
		console.error(e);
		return { "success": false , "message" : "Doctor creation failed!", error: e };
	}
}


async function filterDoctors(name ,time ,specialty) {
	let url = DOCTOR_API + "/" + token;
	let params = [];
	if( name && name.trim() != "" ) {
		params.push("name=" + encodeURIComponent(name) );		
	}
	if( time && time.trim() != "" ) {
		params.push("time=" + encodeURIComponent(time) );		
	}
	if( specialty && specialty.trim() != "" ) {
		params.push("specialty=" + encodeURIComponent(specialty) );		
	}
	if( params.length > 0 ) {
		url += "?" + params.join("&");
	}
	try {
		const response = await fetch(url);
		const json = await response.json();
		return json;
	} catch(e) {
		console.error(e);
		return [];
	}
}

/*
  Import the base API URL from the config file
  Define a constant DOCTOR_API to hold the full endpoint for doctor-related actions


  Function: getDoctors
  Purpose: Fetch the list of all doctors from the API

   Use fetch() to send a GET request to the DOCTOR_API endpoint
   Convert the response to JSON
   Return the 'doctors' array from the response
   If there's an error (e.g., network issue), log it and return an empty array


  Function: deleteDoctor
  Purpose: Delete a specific doctor using their ID and an authentication token

   Use fetch() with the DELETE method
    - The URL includes the doctor ID and token as path parameters
   Convert the response to JSON
   Return an object with:
    - success: true if deletion was successful
    - message: message from the server
   If an error occurs, log it and return a default failure response


  Function: saveDoctor
  Purpose: Save (create) a new doctor using a POST request

   Use fetch() with the POST method
    - URL includes the token in the path
    - Set headers to specify JSON content type
    - Convert the doctor object to JSON in the request body

   Parse the JSON response and return:
    - success: whether the request succeeded
    - message: from the server

   Catch and log errors
    - Return a failure response if an error occurs


  Function: filterDoctors
  Purpose: Fetch doctors based on filtering criteria (name, time, and specialty)

   Use fetch() with the GET method
    - Include the name, time, and specialty as URL path parameters
   Check if the response is OK
    - If yes, parse and return the doctor data
    - If no, log the error and return an object with an empty 'doctors' array

   Catch any other errors, alert the user, and return a default empty result
*/
