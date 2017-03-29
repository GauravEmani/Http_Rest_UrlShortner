package com.example.assignment.url.shorten.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gaurav on 2017-03-23.
 */
@RestController
public class UrlController {	
	
	@Autowired
	UrlService urlService;
	
	private static Map<String, String> urlByIdMap = new ConcurrentHashMap<>();
	private static Map<String, String> urlStore= new ConcurrentHashMap<>();   

	@RequestMapping(value="/")
    public String displayStartPage() {
        return "Start the UI project using commands - npm install and then npm start"
        		+ "Start the Backend project using keyword - mvn spring-boot:run."
        		+ "Use /help for info ";
    }
	
    @RequestMapping("/help")
    public String showForm() {
        return "shortner";
    }
    
    @CrossOrigin
    @RequestMapping(value="/statistic/{id}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> getStatistics(String accountId) {
    	ResponseEntity<String> re = new ResponseEntity<String>(urlService.getStatistics(accountId), HttpStatus.OK);
		return re;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void redirectToUrl(@PathVariable String id, HttpServletResponse resp) throws Exception {
        final String url = urlService.findUrlById(id);        
        if (url != null) {
            resp.addHeader("Location", url);
            resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @CrossOrigin
    @RequestMapping(value="/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
	public ResponseEntity<String> shortenUrl(@RequestBody ShortenUrlRequest request) {
    	return urlService.getShortString(request);
    }
}
