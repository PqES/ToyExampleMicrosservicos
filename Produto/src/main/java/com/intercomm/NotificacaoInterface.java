package com.intercomm;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ProductDAO;
import com.entities.Product;

@Service
public class NotificacaoInterface {
	@Autowired
	private ProductDAO productDAO;
	
	
	
}
