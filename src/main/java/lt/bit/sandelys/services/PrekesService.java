package lt.bit.sandelys.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.bit.sandelys.entities.Preke;
import lt.bit.sandelys.repositories.PrekeRepository;

@Service
public class PrekesService {
	
	@Autowired
	PrekeRepository prekeRepository;
	
	public List<Preke> getPrekes(){
		return prekeRepository.findAll();
	}
	
	public Preke savePreke(Preke preke) {
		return prekeRepository.save(preke);
	}
	
	public Preke getPreke(Integer id) {
		return prekeRepository.findById(id).orElse(null);
	}
	
	public Preke updatePreke(Preke p) {
		Preke old=this.getPreke(p.getId());
		old.setName(p.getName());
		old.setDescription(p.getDescription());
		old.setAmmount(p.getAmmount());
		old.setPrice(p.getPrice());
		old.setImage(p.getImage());
		return prekeRepository.save(old);
	}
	
	public void deletePreke(Integer id) {
		prekeRepository.deleteById(id);
	}

	public Preke getByName(String name) {
		
		return prekeRepository.findByName(name);
		
	}
}
