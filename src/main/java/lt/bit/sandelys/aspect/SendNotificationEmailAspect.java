package lt.bit.sandelys.aspect;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lt.bit.sandelys.entities.Preke;

@Aspect
@Component
public class SendNotificationEmailAspect {
	/*
	@Autowired
	private JavaMailSender emailSender;
	
	
	@Before("execution(public *ties.Preke getPrek*(..))") //galima betkur padet * tada teksta supras kaip bet koki pvz *blabla* 
	public void beforeAnyGet() {
		/*
		SimpleMailMessage email=new SimpleMailMessage();
		email.setFrom("");
		email.setTo("");
		email.setSubject("Aspektinio programavimo testai");
		email.setText("Kazkoks aspektinio programavimo tekstas");
		emailSender.send(email);
		
		System.out.println("laiskas buvo perduotas i SMTP serveri issiuntimui");
	}
	 */
	
	@Before("execution(public lt.bit.sandelys.entities.Preke lt.bit.sandelys.controllers.PrekesController.add(..))") //galima padet gale * tada teksta supras kaip bet koki pvz zmog* 
	public void beforeAddingPreke(JoinPoint jp) throws IOException { //JoinPointas paims duomenis
		String pavadinimas=((Preke)jp.getArgs()[0]).getName();
		Authentication a=SecurityContextHolder.getContext().getAuthentication();
		System.out.println(a.getName()+" pridejo nauja preke, pavadinimas: "+pavadinimas);
		
		LocalDateTime now=LocalDateTime.now();
		FileWriter fileWriter=new FileWriter("c:/JAVA/log.txt", true);
		PrintWriter pw=new PrintWriter(fileWriter);
		pw.println("["+now+"]"+a.getName()+" pridejo nauja preke, pavadinimas: "+pavadinimas);
		pw.close();
		
	}
}
