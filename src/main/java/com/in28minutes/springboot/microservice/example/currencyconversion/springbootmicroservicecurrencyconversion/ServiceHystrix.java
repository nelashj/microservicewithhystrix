package com.in28minutes.springboot.microservice.example.currencyconversion.springbootmicroservicecurrencyconversion;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ServiceHystrix {

	  @Autowired
	  private CurrencyExchangeServiceProxy proxy;
	  
	  @HystrixCommand(fallbackMethod="returnDummy")
	  public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
		      @PathVariable BigDecimal quantity) {

		    CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);


		    return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
		        quantity.multiply(response.getConversionMultiple()), response.getPort());
		  }

	  public CurrencyConversionBean returnDummy( String from,  String to,
		       BigDecimal quantity){
		  CurrencyConversionBean bean= new CurrencyConversionBean(new Long(1000), "EURO", "INR", new BigDecimal("1.1"), new BigDecimal("1000"), new BigDecimal("1100"), 0000);
				  return bean;
	  }
}
