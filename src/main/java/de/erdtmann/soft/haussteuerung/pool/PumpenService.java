package de.erdtmann.soft.haussteuerung.pool;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.pool.exceptions.PumpenException;
import de.erdtmann.soft.haussteuerung.pool.utils.Pumpe;


@ApplicationScoped
public class PumpenService {

	Logger log = Logger.getLogger(PumpenService.class);
	
//	@Inject
//	PumpenRepository pumpenRepo;
	
	@Inject
	PumpenRestClient restClient;
	
//	PumpenZeitE pumpenZeitE;
	
	public boolean holePumpenSatus() throws PumpenException {
		return (restClient.statusPumpe() == 1);
	}

	public int schaltePumpe(Pumpe pumpe) throws PumpenException {
		log.info("Pumpe: " + pumpe.name());
		
		int status = restClient.schaltePumpe(pumpe);
		
			if (status == 200) {
				if (pumpe.equals(Pumpe.AN)) {
					startePumpenLauf();
				} else if (pumpe.equals(Pumpe.AUS)) {
					beendePumpenLauf();
				}
			}
		return status;
	}	

	void startePumpenLauf() throws PumpenException {
//		pumpenZeitE = neuePumpenZeit();
//		
//		pumpenRepo.speicherePumpenZeit(pumpenZeitE);
	}
	
	void beendePumpenLauf() throws PumpenException {
//		pumpenZeitE.setAusgeschaltet(LocalDateTime.now());
//		
//		pumpenRepo.speicherePumpenZeit(pumpenZeitE);
//		
//		int tage = berechneTage();
//		
//		
//		
//		if (tage == 0) {
//			LocalDateTime anfang = pumpenZeitE.getEingeschaltet();
//			LocalDateTime ende = pumpenZeitE.getAusgeschaltet();
//			
//			PumpenLaufzeitE pumpenLaufzeitE = pumpenRepo.sucheLaufzeitByDatum(anfang.toLocalDate());
//			
//			if (pumpenLaufzeitE  == null) {
//				pumpenLaufzeitE = erzeugeNeueLaufzeit(anfang.toLocalDate());	
//			}
//			
//			long dauer = berechneLaufZeit(anfang, ende);
//			
//			pumpenLaufzeitE.setLaufzeit(pumpenLaufzeitE.getLaufzeit() + dauer);
//			
//			pumpenRepo.speicherePumpenLaufzeit(pumpenLaufzeitE);
//			
//		} else if (tage > 0) {
//			PumpenLaufzeitE pumpenLaufzeitE = pumpenRepo.sucheLaufzeitByDatum(pumpenZeitE.getEingeschaltet().toLocalDate());
//			
//			if (pumpenLaufzeitE  == null) {
//				pumpenLaufzeitE = erzeugeNeueLaufzeit(pumpenZeitE.getEingeschaltet().toLocalDate());	
//			}
//			
//			long dauer = berechneLaufZeit(pumpenZeitE.getEingeschaltet(), berechneMitternacht(pumpenZeitE.getEingeschaltet().toLocalDate().plusDays(1)));
//			
//			pumpenLaufzeitE.setLaufzeit(pumpenLaufzeitE.getLaufzeit() + dauer);
//			
//			pumpenRepo.speicherePumpenLaufzeit(pumpenLaufzeitE);
//			
//			for (int i=1; i <= tage; i++) {
//				pumpenLaufzeitE = pumpenRepo.sucheLaufzeitByDatum(pumpenZeitE.getEingeschaltet().toLocalDate().plusDays(i));
//				
//				if (pumpenLaufzeitE  == null) {
//					pumpenLaufzeitE = erzeugeNeueLaufzeit(pumpenZeitE.getEingeschaltet().toLocalDate().plusDays(i));	
//				}
//				
//				LocalDateTime anfang = berechneMitternacht(pumpenZeitE.getEingeschaltet().toLocalDate().plusDays(i));
//				
//				if (anfang.toLocalDate().equals(pumpenZeitE.getAusgeschaltet().toLocalDate())) {
//					dauer = berechneLaufZeit(anfang, pumpenZeitE.getAusgeschaltet());
//					
//					pumpenLaufzeitE.setLaufzeit(pumpenLaufzeitE.getLaufzeit() + dauer);
//					
//					pumpenRepo.speicherePumpenLaufzeit(pumpenLaufzeitE);
//				} else {
//					dauer = berechneLaufZeit(anfang, berechneMitternacht(anfang.toLocalDate().plusDays(1)));
//					
//					pumpenLaufzeitE.setLaufzeit(pumpenLaufzeitE.getLaufzeit() + dauer);
//					
//					pumpenRepo.speicherePumpenLaufzeit(pumpenLaufzeitE);
//				}
//			}
//		}
	}

//	private LocalDateTime berechneMitternacht(LocalDate anfang) {
//		
//		LocalTime mitternacht = LocalTime.of(00, 00);
//		
//		return LocalDateTime.of(anfang, mitternacht);
//	}
	
	
//	private PumpenLaufzeitE erzeugeNeueLaufzeit(LocalDate datum) {
//		return PumpenLaufzeitE.builder().withDatum(datum).withLaufzeit(0).build();	
//	}
//
//	PumpenZeitE neuePumpenZeit() {
//		return PumpenZeitE.builder().withEingeschaltet(LocalDateTime.now()).build();
//	}
	
//	int berechneTage() {
//		return Period.between(pumpenZeitE.getEingeschaltet().toLocalDate(), pumpenZeitE.getAusgeschaltet().toLocalDate()).getDays();
//	}
	
//	long berechneLaufZeit(LocalDateTime anfang, LocalDateTime ende) {
//		return Duration.between(anfang, ende).getSeconds();
//	}
}
