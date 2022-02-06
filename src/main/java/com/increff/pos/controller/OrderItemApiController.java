package com.increff.pos.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.OrderItemDto;
import com.increff.pos.model.ItemData;
import com.increff.pos.model.ItemForm;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class OrderItemApiController {
	
	@Autowired
	private OrderItemDto dto;
	
	@ApiOperation(value = "Check an order")
	@RequestMapping(path = "/api/orderitem/check", method = RequestMethod.POST)
	public ItemData checkbarcode(@RequestBody OrderItemForm form) throws ApiException {
		return dto.checkbarcode(form);
	}

	@ApiOperation(value = "Adds an order")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
	public void add(@RequestBody List<ItemForm> form) throws ApiException, IOException {
		dto.add(form);
	}

	
	@ApiOperation(value = "Download a pdf")
	@RequestMapping(path = "/api/orderitem/download", method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		  try{
		    FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		    //Setup a buffer to obtain the content length
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    //Setup FOP
		    Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
		    TransformerFactory factory = TransformerFactory.newInstance();
		    Transformer transformer = factory.newTransformer(new StreamSource("src\\main\\resources\\com\\increff\\pos\\organization.xsl"));
		    //Make sure the XSL transformation's result is piped through to FOP
		    Result res = new SAXResult(fop.getDefaultHandler());

		    //Setup input
		    Source src = new StreamSource(new File("src\\main\\resources\\com\\increff\\pos\\organization.xml"));

		    //Start the transformation and rendering process
		    transformer.transform(src, res);

		    //Prepare response
		    response.setContentType("application/pdf");
		    response.setContentLength(out.size());

		    //Send content to Browser
		    response.getOutputStream().write(out.toByteArray());
		    response.getOutputStream().flush();
		  }catch(Exception e){
		    e.printStackTrace();
		  }
		}
	
//	@ApiOperation(value = "Deletes an order")
//	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.DELETE)
//	public void delete(@PathVariable int id) {
//		dto.delete(id);
//	}

//	@ApiOperation(value = "Gets a order by ID")
//	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.GET)
//	public OrderItemData get(@PathVariable int id) throws ApiException {
//		return dto.get(id);
//	}

//	@ApiOperation(value = "Gets list of all Orders")
//	@RequestMapping(path = "/api/orderitem", method = RequestMethod.GET)
//	public List<OrderItemData> getAll() throws ApiException {
//		return dto.getAll();
//	}
//
//	@ApiOperation(value = "Updates an order")
//	@RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.PUT)
//	public void update(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
//		dto.update(id,f);
//	}
	
}
