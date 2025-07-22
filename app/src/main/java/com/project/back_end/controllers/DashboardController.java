
package com.project.back_end.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.back_end.services.Service;

@Controller
public class DashboardController {
	
	@Autowired
	Service service;
	
	@GetMapping("/adminDashboard/{token}")
	public String adminDashboard(@PathVariable String token) {
		if( service.validateToken(token, "admin") == HttpStatus.OK ) {
			return "admin/adminDashboard";
		}
		return "redirect:/login";
	}

	@GetMapping("/doctorDashboard/{token}")
	public String doctorDashboard(@PathVariable String token) {
		if( service.validateToken(token, "doctor") == HttpStatus.OK ) {
			return "admin/doctorDashboard";
		}
		return "redirect:/login";
	}

}

