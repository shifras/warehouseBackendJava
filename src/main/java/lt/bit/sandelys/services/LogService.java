package lt.bit.sandelys.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lt.bit.sandelys.entities.Log;
import lt.bit.sandelys.entities.Preke;
import lt.bit.sandelys.repositories.LogRepository;
import lt.bit.sandelys.repositories.PrekeRepository;

@Service
public class LogService {
	
	@Autowired
	private LogRepository logRepository;
	
	public void save(Log log) {
		System.out.println("Logas irasytas logo lenteleje. iraso tekstas: \n"+log.getAction());
		logRepository.save(log);
	}
}
