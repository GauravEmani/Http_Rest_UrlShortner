package com.example.assignment.url.shorten.repository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by gaurav on 2017-03-23.
 */
public class ShortenUrlRequest {
    @NotNull
    @Size(min = 5, max = 1024)
    private String url;
    private Integer redirectType;

    public Integer getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(Integer redirectType) {
		this.redirectType = redirectType;
	}

	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
