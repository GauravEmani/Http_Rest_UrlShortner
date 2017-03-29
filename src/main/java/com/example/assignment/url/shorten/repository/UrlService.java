package com.example.assignment.url.shorten.repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;

@Component
public class UrlService {
	
	private static Map<String, String> urlByIdMap = new ConcurrentHashMap<>();
	private static Map<String, String> urlStore = new ConcurrentHashMap<>();   

	public String getStatistics(String accountId) {
		String response = "";
    	
    	int count = 0;
    	String prefix = "{";
    	String postFix = "}";
    	for (Map.Entry<String, String> entry : urlByIdMap.entrySet())
    	{
    		count = count + 1;
    		response = response + "\"" + findUrlById(entry.getKey())+ "\"" + ":\"" + getRedirectTypeCountOfRegisteredUrls(entry.getKey()) + "\"";
    		//response = response + findUrlById(entry.getKey()) + ":" + getRedirectTypeCountOfRegisteredUrls(entry.getKey());
    		if(urlByIdMap.size() > 1 && count != urlByIdMap.size()) {
    		    response = response + ",";
    		}
    	    
    	}
    	String respStr = prefix + response + postFix;
    	// String redirectTypeCount = getRedirectTypeCountOfRegisteredUrls(accountId);
    	// Integer count = Integer.parseInt(redirectTypeCount);
    		    	
		return respStr;
	}
	
	public ResponseEntity<String> getShortString(ShortenUrlRequest request) {
		String response = "";
        String url = request.getUrl();
        boolean exists = false;
        String id = "";
        if (!isUrlValid(url)) {
        	request.setUrl("Invalid url format: " + url);
        	response = request.toString();
        	response = "{\"shortUrl\"" + ":\"" + response + "\"}";
        	ResponseEntity<String> re = new ResponseEntity<String>(response, HttpStatus.NOT_ACCEPTABLE);
    		return re; 
        }
        
        // check if this url has been redirected before
        for (Map.Entry<String, String> entry : urlByIdMap.entrySet())
    	{
        	if(url.equals(findUrlById(entry.getKey()))) {
        		exists = true;
        		id = entry.getKey();
        	}  
    	}
           if (isUrlValid(url)) {        	   
            id = Hashing.murmur3_32()
                .hashString(url, StandardCharsets.UTF_8).toString();
            if(!exists) {
            	storeUrl(id, url);
            	registerUrl(id, "0");
        	}   
            if(getRedirectTypeCountOfRegisteredUrls(id) != null) {
	            String redirectTypeCount = getRedirectTypeCountOfRegisteredUrls(id);
	            System.out.println("--------------------"+redirectTypeCount);
	            Integer count = Integer.parseInt(redirectTypeCount);
	            count = count + 1;
	            registerUrl(id, ""+count);            
            }
            
            response =  "http://short.com" + "/" + id;
            response = "{\"shortUrl\"" + ":\"" + response + "\"}";
        }
        ResponseEntity<String> re = new ResponseEntity<String>(response, HttpStatus.OK);
   		return re;

	}
	
	 private boolean isUrlValid(String url) {
	        boolean valid = true;
	        try {
	            new URL(url);
	        } catch (MalformedURLException e) {
	            valid = false;
	        }
	        return valid;
	    }

	    public String findUrlById(String id) {
	        return urlByIdMap.get(id);
	    }
	    
	    public void storeUrl(String id, String url) {
	        urlByIdMap.put(id, url);
	    }
	    
	    public String getRedirectTypeCountOfRegisteredUrls(String id) {
	        return urlStore.get(id);
	    }
	    
	    public void registerUrl(String id, String url) {
	    	urlStore.put(id, url);
	    }

}
