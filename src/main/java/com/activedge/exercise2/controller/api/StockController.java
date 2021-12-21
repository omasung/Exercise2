package com.activedge.exercise2.controller.api;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.activedge.exercise2.model.Stock;
import com.activedge.exercise2.service.StockService;
import com.activedge.exercise2.utility.*;

@RestController
@RequestMapping(value = "/api")
public class StockController {

	@Autowired private StockService stockService;
	@Autowired private InputValidation inputValidation;
	@Autowired private DateTimeService dateTimeService;

	/**
	 * get the List of Stocks
	 * @return
	 */
	@RequestMapping(value = "/stocks", method = { RequestMethod.GET }, produces = "application/json")
	public Map<String, Object> getAllStock(HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();
			
		List<Stock> stock;
		stock = stockService.getAllStock();//Get the list of stocks
		
		if (!stock.isEmpty()) {//If the stock list is not empty						
				
			map.put("stock", stock);	
			map.put("message", "successful");
			map.put("status", HttpStatus.OK);	
			
			response.setStatus(200);
			
		} else {//No stock found. The stock list is empty
			
			map.put("message", "stock unavailable for now");
			map.put("status", HttpStatus.NOT_FOUND);
			
			response.setStatus(404);
			
		} 			
																	
		return map;
			
	}	
	
	/**
	 * get a single Stock from the list by its ID
	 * @param Id
	 * @return
	 */
	@RequestMapping(value = "/stocks/{Id}", method = { RequestMethod.GET }, produces = "application/json")
	public Map<String, Object> getSingleStock(@PathVariable String Id, HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (inputValidation.validateNumeric(Id).equals(true)) {//Validate Id
			
			Stock stock = null;
			stock = stockService.getStockByPk(Long.parseLong(Id));//Get Stock by Id
			
			if (stock != null) {//Check if stock exist						
					
				map.put("stock", stock);	
				map.put("message", "successful");
				map.put("status", HttpStatus.OK);
				
				response.setStatus(200);
				
			} else {//The stock Id passed not found i.e the stock does not exist
				
				map.put("message", "stock with the Id " + Id + " not found");
				map.put("status", HttpStatus.NOT_FOUND);
				
				response.setStatus(404);
				
			} 			
			
		} else {//The stock Id is invalid (when the Id is non numerical)
			
			map.put("message", "invalid stock id");
			map.put("status", HttpStatus.BAD_REQUEST);
			
			response.setStatus(400);
			
		}
																	
		return map;
			
	}		

	/**
	 * update the current_price/name of a single Stock
	 * @param Id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stocks/{Id}", method = { RequestMethod.PUT }, produces = "application/json")
	public Map<String, Object> updateStock(@PathVariable String Id, HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (inputValidation.validateNumeric(Id).equals(true)) {//Validate Id

			String name = request.getHeader("name");
			String current_price = request.getHeader("price");
			LocalDateTime dateTime = dateTimeService.PresentDateTime();
			
			Stock stock = null;
			stock = stockService.getStockByPk(Long.parseLong(Id));//Get Stock by Id
			
			if (stock != null) {//Check if the stock exist 
				
				if (inputValidation.validateAlphanumeric(name).equals(true)) {//If the stock name is not null
		
					stock.setName(name);//Set the new name
					stock.setLast_update(dateTime);//Set last update	
										
				}	
					
				if (inputValidation.validateNumeric(current_price).equals(true)) {//If the stock price is not null 
					
					stock.setCurrent_price(Double.parseDouble(current_price));//Set the new stock price
					stock.setLast_update(dateTime);//Set last update	
					
				}					
			
				if (inputValidation.validateAlphanumeric(name).equals(true)
						|| inputValidation.validateNumeric(current_price).equals(true)) {//Only update when either stock name or price is valid
					
					stockService.updateStock(stock);//Update stock
					
					map.put("message", "stock updated successful");
					map.put("status", HttpStatus.OK);
					
					response.setStatus(200);					
					
				} else {//Stock name and price is invalid
					
					map.put("message", "stock name and price is invalid");
					map.put("status", HttpStatus.BAD_REQUEST);
					
					response.setStatus(400);					
					
				}

				
			} else {//The stock Id passed not found i.e the stock does not exist
				
				map.put("message", "stock with the Id " + Id + " not found");
				map.put("status", HttpStatus.NOT_FOUND);
				
				response.setStatus(404);
				
			} 			
			
		} else {//The stock Id is invalid (Only when the Id is non numerical)
			
			map.put("message", "invalid stock id");
			map.put("status", HttpStatus.BAD_REQUEST);
			
			response.setStatus(400);
			
		}
																	
		return map;
			
	}		
	
	/**
	 * delete a single Stock by its ID
	 * @param Id
	 * @return
	 */
	@RequestMapping(value = "/stocks/{Id}", method = { RequestMethod.DELETE }, produces = "application/json")
	public Map<String, Object> deleteStock(@PathVariable String Id, HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (inputValidation.validateNumeric(Id).equals(false)) {//Validate Id

			map.put("message", "invalid stock Id");
			map.put("status", HttpStatus.BAD_REQUEST);
			
			response.setStatus(400);
			
		} else {
			
			Stock stock = null;
			stock = stockService.getStockByPk(Long.parseLong(Id));//Check if the stock exist
			
			if (stock != null) {//Stock exist
				
				stockService.deleteStock(Long.parseLong(Id));//Delete stock
				
				map.put("message", "stock deleted successful");
				map.put("status", HttpStatus.OK);
				
				response.setStatus(200);
				
			} else {//The stock Id is invalid (Only when the Id is non numerical)
				
				map.put("message", "stock with the Id " + Id + " not found");
				map.put("status", HttpStatus.NOT_FOUND);
				
				response.setStatus(404);
				
			}
					
		}
																	
		return map;
			
	}	

	/**
	 * create a new Stock
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/stocks", method = { RequestMethod.POST }, produces = "application/json")
	public Map<String, Object> addStock(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> map = new HashMap<String, Object>();

			String name = request.getHeader("name");
			String current_price = request.getHeader("price");
		
		if (inputValidation.validateAlphanumeric(name).equals(false)) {//If the stock name is empty

			map.put("message", "invalid stock name");
			map.put("status", HttpStatus.BAD_REQUEST);
			
			response.setStatus(400);
			
		} else if (inputValidation.validateNumeric(current_price).equals(false)) {//If the stock is empty or the value passed is non numerical
			
			map.put("message", "invalid stock price");
			map.put("status", HttpStatus.BAD_REQUEST);
			
			response.setStatus(400);
			
		} else {//All conditions fulfilled
			
			Stock stock = new Stock();//Create new stock object
			LocalDateTime dateTime = dateTimeService.PresentDateTime();//Get present date time
		
			stock.setName(name);//Set name
			stock.setCurrent_price(Double.parseDouble(current_price));//Set price
			stock.setCreate_date(dateTime);//Set created date
			stock.setLast_update(dateTime);//Set last update		
			stockService.addStock(stock);//Add stock
			
			map.put("message", "created successful");
			map.put("status", HttpStatus.CREATED);
			
			response.setStatus(201);
			
		}
																	
		return map;
			
	}	
	
}
