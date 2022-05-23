package lt.bit.sandelys.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lt.bit.sandelys.entities.Preke;
import lt.bit.sandelys.exceptions.ErrorResponse;
import lt.bit.sandelys.exceptions.PrekeException;
import lt.bit.sandelys.services.PrekesService;

@RestController
@RequestMapping("/prekes")
public class PrekesController {
	
	@Autowired
	PrekesService prekesService;
	
	@ExceptionHandler(value = {InvalidFormatException.class}) //masyvas exeptioniu klasiu pavadinimu
	public ResponseEntity<ErrorResponse> invalidData() {
		System.out.println("klaida pagauta");
		return new ResponseEntity<>(new ErrorResponse("Neleistini duomenys","",400) , HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(value = {PrekeException.class})
	public ResponseEntity<ErrorResponse> prekeException(PrekeException e){
		return new ResponseEntity<>(new ErrorResponse(e.getMessage(),"ddd",e.getCode()), HttpStatus.valueOf(e.getCode()));
	}
	
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<ErrorResponse> defaultException(Exception e){
		System.out.println("suveike default exception handleris");
		return new ResponseEntity<>(new ErrorResponse("Unknown error ","",500), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@CrossOrigin
	@GetMapping("/")
	public List<Preke> index(){
		//cia tipo sumuliuojam kad ilgai siuncia
//		try {
//			Thread.sleep(1000+(int)Math.random()*3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return prekesService.getPrekes();
	}
	
	@CrossOrigin
	@PostMapping("/")
	public Preke add(@RequestBody Preke preke) {
		Preke p=prekesService.getByName(preke.getName());
		if (p==null) {
			return prekesService.savePreke(preke);
		} else {
			throw new PrekeException("Preke tokiu pavadinimu jau egzistuoja", 400);
		}
	}
	
	@CrossOrigin
	@GetMapping("/{id}")
	public Preke get(@PathVariable Integer id) {
		Preke p=prekesService.getPreke(id);
		if (p==null) {
			System.out.println("metam nauje exceptiona del blogo id");
			throw new PrekeException("NERASTA SU TOKIU ID", 404);
		}
		return p;
	}
	
	@CrossOrigin
	@PatchMapping("/{id}")
	public Preke update(@PathVariable Integer id, @RequestBody Preke p) {
		if (p.getPrice() <=0  || p.getAmmount() <0 ) {
			throw new PrekeException("Negalima ivesti neigiamu skaiciu", 404);
		}
		
		Preke pr=prekesService.getByName(p.getName());
		if (pr==null || pr.getId()==p.getId()) return prekesService.updatePreke(p);
		
		
		throw new PrekeException("Preke tokiu pavadinimu jau egzistuoja", 400);
		
	}
	
	@CrossOrigin
	@DeleteMapping("/{id}")
	public Boolean delete(@PathVariable Integer id) {
		Preke p=prekesService.getPreke(id);
		Integer a=p.getAmmount();
		if (a == 0) {
			prekesService.deletePreke(id);
			return true;
		} else {
			throw new PrekeException("Negalima istrinti "+p.getName()+", sandelyje likutis yra "+a+".", 500);
		}
	}
	
	@CrossOrigin
	@GetMapping("/{id}/name/{name}")
	public Boolean updateName(@PathVariable String name, @PathVariable Integer id) {
		Preke p=prekesService.getByName(name);
		if (p!=null) return p.getId()==id;
		else return true;
	}
	
	@CrossOrigin
	@GetMapping("/name/{name}")
	public Boolean newName(@PathVariable String name) {
			return prekesService.getByName(name)==null;
	}
	
}
