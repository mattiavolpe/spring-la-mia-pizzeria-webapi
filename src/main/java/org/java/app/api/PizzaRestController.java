package org.java.app.api;

import java.util.List;

import org.java.app.db.pojo.Pizza;
import org.java.app.db.repo.PizzaRepo;
import org.java.app.db.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class PizzaRestController {
	
	@Autowired
	private PizzaRepo pizzaRepo;
	
	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping
	public ResponseEntity<List<Pizza>> getAll() {
		List<Pizza> pizzas = pizzaService.findAll();
		
		return new ResponseEntity<List<Pizza>>(pizzas, HttpStatus.OK);
	}
	
	@GetMapping("/filter/{filter}")
	public ResponseEntity<List<Pizza>> getFiltered(@PathVariable String filter) {
		List<Pizza> filteredPizzas = pizzaRepo.findByNameContaining(filter);
		
		return new ResponseEntity<List<Pizza>>(filteredPizzas, HttpStatus.OK);
	}
}
