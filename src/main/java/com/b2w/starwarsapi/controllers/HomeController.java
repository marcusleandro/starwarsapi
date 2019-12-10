package com.b2w.starwarsapi.controllers;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public InfoHome home(HttpServletRequest request) {
		
		String docUrl = getDocumentationUrlFrom(request);
		String message = "Welcome to Star Wars API project using Java, Spring Boot and MongoDB, for more information please see the documentation";
		return new InfoHome(message, docUrl);
				
//		return "Welcome to Star Wars API project using Java, Spring boot and MongoDB, "
//				+ "for more information please see the documentation in " + docUrl;
	}
	

	private String getDocumentationUrlFrom(HttpServletRequest request) {
		String scheme = request.getScheme();             // http
	    String serverName = request.getServerName();     // hostname.com
	    int serverPort = request.getServerPort();        // 80
	    
	    // Reconstruct original requesting URL
	    StringBuilder url = new StringBuilder();
	    url.append(scheme).append("://").append(serverName);

	    if (serverPort != 80 && serverPort != 443)
	        url.append(":").append(serverPort);

	    url.append("/swagger-ui.html");
		String docUrl = url.toString();
		return docUrl;
	}
	
	
	// Inner Class
	
	public class InfoHome implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String message;
		private String docUrl;
		
		
		public InfoHome() { }
		
		public InfoHome(String message, String docUrl) {
			this.message = message;
			this.docUrl = docUrl;
		}

		
		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getDocUrl() {
			return docUrl;
		}

		public void setDocUrl(String docUrl) {
			this.docUrl = docUrl;
		}
		
	} 
}
