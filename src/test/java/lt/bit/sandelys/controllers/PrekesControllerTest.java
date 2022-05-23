package lt.bit.sandelys.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.hql.internal.ast.ErrorReporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lt.bit.sandelys.entities.Preke;
import lt.bit.sandelys.exceptions.ErrorResponse;
import lt.bit.sandelys.exceptions.PrekeException;
import lt.bit.sandelys.services.PrekesService;

@SpringBootTest
class PrekesControllerTest {
	
	Preke p;
	Preke gr;
	//cia yra springo mockito autowired
	@Mock
	PrekesService prekesService;
	
	//sumeta auksciau sukurtus mockus i sita kontroleri automatiskai autowiredas
	@InjectMocks
	PrekesController controller;

	@BeforeEach
	void setUp() throws Exception {
		p=new Preke( 2,  "tusinukas",  "raso",  1.3,  5,  "img");
		gr=new Preke( 1,  "kede",  "stovi",  1.3,  5,  "img");
	}

	
	@Test
	void successAddMethodReturnsPrekeObject() {
		
		
		when(prekesService.getByName(p.getName())).thenReturn(null);
		when(prekesService.savePreke(p)).thenReturn(gr);
		Preke pr=controller.add(p);
		
		assertEquals(gr, pr);
//		assertSame(p, pr);
		verify(prekesService).getByName(p.getName());
		verify(prekesService).savePreke(p);
		
	}
	
	@Test
	void ifNameIsTakenReturnsException() {
		Preke p=new Preke( null,  "tusinukas",  "raso",  1.3,  5,  "img");
		when(prekesService.getByName(p.getName())).thenReturn(p);
		assertThrows(PrekeException.class, ()->{
			controller.add(p);
		});
		verify(prekesService, times(1)).getByName(p.getName());
		verify(prekesService, times(0)).savePreke(p);
	}
	
	
	@Test
	void arIndexGrazinaPrekiuSarasa() {
		List<Preke> prekes=new ArrayList<>();
		prekes.add(p);
		prekes.add(gr);
		prekes.add(new Preke());
		when(prekesService.getPrekes()).thenReturn(prekes);
		assertEquals(prekes, controller.index());
		verify(prekesService).getPrekes();
		
	}
	
	@Test
	void arMetaPrekeExceptionJeiKainaArbaKiekisMaziauUzNuli() {
		assertThrows(PrekeException.class, ()->{
			p.setPrice(1.0);
			p.setAmmount(-1);
			controller.update(null, p);
		});
		
		assertThrows(PrekeException.class, ()->{
			p.setPrice(-1.0);
			p.setAmmount(1);
			controller.update(null, p);
		});
	}
	
	@Test
	void tikrinaArMetaExceptionaJeiPrekeNeNullArbaJeiSutampaId() {
		
		Preke tusinukas=new Preke(2,  "tusinukas",  "raso",  1.3,  5,  "img");
		Preke failTusinukas=new Preke(1,  "tusinukas",  "stovi",  1.3,  5,  "img");
		
		assertThrows(PrekeException.class, ()->{
			when(prekesService.getByName("tusinukas")).thenReturn(tusinukas);
			controller.update(failTusinukas.getId(), failTusinukas);
		});
		
		verify(prekesService,times(1)).getByName("tusinukas");
		
	}
	
	@Test
	void tikrinaArNesuveikiaExceptionasKaiPrekeGrazinamaNeNull() {
		when(prekesService.updatePreke(gr)).thenReturn(gr);
		when(prekesService.getByName("tusinukas")).thenReturn(null);
		Preke grazinta = controller.update(gr.getId(), gr);
		assertEquals(gr ,grazinta );
		
		verify(prekesService,times(1)).updatePreke(gr);
		verify(prekesService, times(1)).getByName(gr.getName());
	}
	
	@Test
	void tikrinaArIstrinaPrekeJeiguKiekisYraNulis() {
		gr.setAmmount(0);
		when(prekesService.getPreke(gr.getId())).thenReturn(gr);
		assertTrue(controller.delete(gr.getId()));
		
		verify(prekesService, times(1)).getPreke(gr.getId());
	}
	
	@Test
	void tikrinaArDeletePrekeIsmetaErrorJeiSandelyjePrekiuNeNulis() {
		when(prekesService.getPreke(gr.getId())).thenReturn(gr);
		assertThrows(PrekeException.class, ()->{
			controller.delete(gr.getId());
		});
		
		verify(prekesService).getPreke(gr.getId());
	}
	
	@Test
	void tikrinaArUpdateNameGrazinaTrueJeiPrekeNeNullIrIdSutampa() {
		when(prekesService.getByName(gr.getName())).thenReturn(gr);
		assertTrue(controller.updateName(gr.getName(), gr.getId()));
		
		verify(prekesService, times(1)).getByName(gr.getName());
	}
	
	@Test
	void tikrinaArUpdateNameGrazinaFalseJeiPrekeNeNullIrIdNesutampa() {
		when(prekesService.getByName(gr.getName())).thenReturn(gr);
		assertFalse(controller.updateName(gr.getName(), 2));
		
		verify(prekesService, times(1)).getByName(gr.getName());
	}
	
	@Test
	void tikrinaArUpdateNameGrazinaTrueJeiPrekeNull() {
		when(prekesService.getByName(gr.getName())).thenReturn(null);
		assertTrue(controller.updateName(gr.getName(), gr.getId()));
		verify(prekesService, times(1)).getByName(gr.getName());
	}
	
	@Test
	void tikrinaArGetPrekeGrazinaPrekeJeiPrekeNeNull() {
		when(prekesService.getPreke(gr.getId())).thenReturn(gr);
		assertEquals(gr, controller.get(gr.getId()));
		
		
		verify(prekesService, times(1)).getPreke(gr.getId());
	}
	
	@Test
	void tikrinaArMetaExceptionaKaiPrekeNull() {
		when(prekesService.getPreke(gr.getId())).thenReturn(null);
		assertThrows(PrekeException.class, ()->{
			controller.get(gr.getId());
		});
		
		
		verify(prekesService, times(1)).getPreke(gr.getId());
	}
	
	@Test
	void arMetaTrueJeiNerandaTokioName() {
		when(prekesService.getByName(gr.getName())).thenReturn(null);
		assertTrue(controller.newName(gr.getName()));
	}
	
	@Test
	void arMetaFalseJeiSurandaPagalName() {
		when(prekesService.getByName(gr.getName())).thenReturn(gr);
		assertFalse(controller.newName(gr.getName()));
	}
	
	@Test
	void arPrekeExceptionGrazinaResponsaSuTokiuPaciuKodu() {
		PrekeException e=new PrekeException("Preke nerasta", 404);
		ResponseEntity<ErrorResponse> r= controller.prekeException(e);
		assertEquals(r.getStatusCodeValue(), e.getCode());
	}
	
	
	@Test
	void arIsmetaTeisingaErrorResponseJeiIskvieciamInvalidDataMetoda() {
		ResponseEntity<ErrorResponse> er=new ResponseEntity<>(new ErrorResponse("Neleistini duomenys","",400) , HttpStatus.BAD_REQUEST); 
		ResponseEntity<ErrorResponse> r=controller.invalidData();
		assertEquals(er.getStatusCode(), r.getStatusCode());
	}
	
	@Test
	void arPadavusExceptionaMetodasDefaultExceptionIsmetaTeisingaPranesima() {
		Exception e=new Exception();
		ResponseEntity<ErrorResponse> r=controller.defaultException(e);
		assertEquals(r.getBody().getName(), "Unknown error ");
	}
	
}
