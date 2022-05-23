package lt.bit.sandelys.aspect;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lt.bit.sandelys.entities.Log;
import lt.bit.sandelys.entities.Preke;
import lt.bit.sandelys.services.LogService;
import lt.bit.sandelys.services.PrekesService;

@Aspect
@Component
public class CrudAspect {
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private PrekesService prekesService;
	
	
	@After("execution(public lt.bit.sandelys.entities.Preke lt.bit.sandelys.controllers.PrekesController.add(..))")
	public void afterAddingNewPreke(JoinPoint jp) throws IOException {
		Log log=new Log();
		String pavadinimas=((Preke)jp.getArgs()[0]).getName();
		Authentication a=SecurityContextHolder.getContext().getAuthentication();
		String now=LocalDateTime.now().toString();
		
		
		FileWriter fileWriter=new FileWriter("c:/JAVA/log.txt", true);
		PrintWriter pw=new PrintWriter(fileWriter);
		pw.println("["+now+"]"+a.getName()+" pridejo nauja preke, pavadinimas: "+pavadinimas);
		pw.close();
		
		log.setDate(now);
		log.setUsername(a.getName());
		log.setAction("Prideta nauja preke: "+pavadinimas);
		logService.save(log);
	}
	
	@Before("execution(public * lt.bit.sandelys.controllers.PrekesController.delete(..))")
	public void afterDeletingPreke(JoinPoint jp) throws IOException {
		Log log=new Log();
		Integer id=((Integer)jp.getArgs()[0]);
		
		Preke p=prekesService.getPreke(id);
		Authentication a=SecurityContextHolder.getContext().getAuthentication();
		String now=LocalDateTime.now().toString();
		
		FileWriter fileWriter=new FileWriter("c:/JAVA/log.txt", true);
		PrintWriter pw=new PrintWriter(fileWriter);
		pw.println("["+now+"]"+a.getName()+" bande istrinti preke, pavadinimas: "+p.getName());
		pw.close();
		
		log.setDate(now);
		log.setUsername(a.getName());
		log.setAction("Bandyta trinti preke: "+p.getName());
		logService.save(log);
	}
	
	/**
		@Before("execution(* saveSauktinis(..))")
		public void beforeNewSauktinisCorrectPhone(JoinPoint jp) {
			Sauktinis s=(Sauktinis)jp.getArgs()[0];
			s.setPhone("+370"+s.getPhone());
		}
		
		@AfterReturning(pointcut = "execution(* getSauktiniai())", returning = "result")
		public void afterHideSurnames(List<Sauktinis> result) {
			Authentication a=SecurityContextHolder.getContext().getAuthentication();
			if (! a.getName().equals("gediminas")) {
				for(Sauktinis s:result) {
					s.setSurname("***");
				}
			}
		}
	 */
	
	
	/**
	 * Pries issaugant preke i sandeli konvertuoja pirmaja pavadinimo raide i Didziaja
	 * @param jp
	 */
	@Before("execution(* lt.bit.sandelys.repositories.PrekeRepository.save(..))")
	public void beforeSavingPreke(JoinPoint jp) {
		Preke p=(Preke)jp.getArgs()[0];
		String str=p.getName().substring(0, 1).toUpperCase();
		p.setName(str+p.getName().substring(1));
		System.out.println(p.getName());
	}
	
	/**
	 * Prideda pvm prie kiekvienos kainos
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* getPrekes())", returning = "result")
	public void afterAddVat(List<Preke> result) {
		for(Preke p:result) {
			p.setPrice(p.getPrice()*1.21);
		}
		
	}
	
	
	
	
	
}
