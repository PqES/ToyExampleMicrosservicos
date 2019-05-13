package com.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dao.ProductDAO;
import com.entities.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.ProductService;

@RestController
@Transactional
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	

	@RequestMapping("/index")
	@ResponseBody
	public String index() {
		return "index";
	}

	public ProductController() {

	}

	@RequestMapping("*")
	@ResponseBody
	public String fallbackMethod() {
		return "fallback method";
	}

	@RequestMapping(value = "add/{name}/{description}/{number}/{value}", method = RequestMethod.GET)
	@ResponseBody
	public String addProduct(@PathVariable String name, @PathVariable String description, @PathVariable int number,
			@PathVariable double value) {
		try {
			Product p = new Product(name, description, number, value);
			productService.saveProduct(p);

		} catch (Exception ex) {
			return "Error creating the user: " + ex.toString();
		}
		return "Success! ";

	}

	@RequestMapping(value = "getProducts") 
	@ResponseBody
	public List<Product> getAllProducts() throws IOException {
		return productService.getAllProducts();

	}

	@RequestMapping(value = "/updateProduct/{qnt}/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String updateStock(@RequestParam(value = "qnt") String[] id, @RequestParam(value = "id") String[] qt) {
		int estoque[] = new int[id.length];
		Product products[] = new Product[id.length];
		for (int i = 0; i < estoque.length; i++) {
			products[i] = productService.findOneProduct(id[i]);
			estoque[i] = products[i].getNumber() - Integer.parseInt(qt[i]);
			if (estoque[i] < 0) {
				return "fail";
			}
		}
		for (int i = 0; i < estoque.length; i++) {
			products[i].setNumber(estoque[i]);
			productService.saveProduct(products[i]);
			//productDAO.save(products[i]);
		}
		return "success";
	}

	@RequestMapping(value = "/product/getProduct/{id}")
	@ResponseBody
	public String getProductByID(@PathVariable("id") String id) {
		Product productName;
		productName = productService.findProductByID(id);
		return productName.getName();
	}

	public String writeListToJsonArray(List<Product> lista) throws IOException {

		final StringWriter sw = new StringWriter();
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(sw, lista);

		sw.close();
		return sw.toString();
	}

}
