package org.java.app.api;

import java.util.List;

import org.java.app.api.dto.PizzaDto;
import org.java.app.db.pojo.Pizza;
import org.java.app.db.repo.PizzaRepo;
import org.java.app.db.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		
		return new ResponseEntity<>(pizzas, HttpStatus.OK);
	}
	
	@GetMapping("/filter/{filter}")
	public ResponseEntity<List<Pizza>> getFiltered(@PathVariable String filter) {
		List<Pizza> filteredPizzas = pizzaRepo.findByNameContaining(filter);
		
		return new ResponseEntity<>(filteredPizzas, HttpStatus.OK);
	}
	
	@PostMapping("{id}")
	public ResponseEntity<Pizza> getPizza(@PathVariable int id) {
		Pizza pizza = pizzaService.findById(id);
		
		return new ResponseEntity<>(pizza, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Integer> savePizza(@RequestBody PizzaDto pizzaDto) {
		Pizza pizza = new Pizza(pizzaDto);
		
		pizzaService.savePizza(pizza);
		
		return new ResponseEntity<>(pizza.getId(), HttpStatus.OK);
	}
}
