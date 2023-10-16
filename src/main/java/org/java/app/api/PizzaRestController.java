package org.java.app.api;

import java.util.List;
import java.util.Optional;

import org.java.app.api.dto.PizzaDto;
import org.java.app.db.pojo.Pizza;
import org.java.app.db.repo.PizzaRepo;
import org.java.app.db.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<List<Pizza>> getAllPizzas() {
		List<Pizza> pizzas = pizzaService.findAll();
		
		return new ResponseEntity<List<Pizza>>(pizzas, HttpStatus.OK);
	}
	
	@GetMapping("/filter/{filter}")
	public ResponseEntity<List<Pizza>> getFilteredPizzas(@PathVariable String filter) {
		List<Pizza> filteredPizzas = pizzaRepo.findByNameContaining(filter);
		
		return new ResponseEntity<List<Pizza>>(filteredPizzas, HttpStatus.OK);
	}
	
	@PostMapping("/{id}")
	public ResponseEntity<Pizza> getPizza(@PathVariable int id) {
		Optional<Pizza> optPizza = pizzaService.findById(id);
		
		if (optPizza.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		
		Pizza pizza = optPizza.get();
		
		return new ResponseEntity<Pizza>(pizza, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Integer> savePizza(@RequestBody PizzaDto pizzaDto) {
		Pizza pizza = new Pizza(pizzaDto);
		
		pizzaService.savePizza(pizza);
		
		return new ResponseEntity<Integer>(pizza.getId(), HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Pizza> updatePizza(@PathVariable int id, @RequestBody PizzaDto pizzaDto) {
		Optional<Pizza> optPizza = pizzaService.findById(id);
		
		if (optPizza.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		
		Pizza pizza = new Pizza(pizzaDto);
		pizza.setId(id);
		
		pizzaService.savePizza(pizza);
		
		return new ResponseEntity<Pizza>(pizza, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deletePizza(@PathVariable int id) {
		Optional<Pizza> optPizza = pizzaService.findById(id);
		
		if (optPizza.isEmpty())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		
		pizzaService.deleteById(id);
		
		return new ResponseEntity<String>("Successfully removed", HttpStatus.OK);
	}
}
