package de.erdtmann.soft.hausSteuerung.timer;

import javax.ejb.Singleton;
import javax.ejb.Schedule;
import javax.inject.Inject;

import org.jboss.logging.Logger;


@Singleton
public class PoolTimer {
	
	Logger log = Logger.getLogger(PoolTimer.class);
	
//	@Inject
//	CoreService coreService;
	
	@Schedule(second = "0", minute = "*/5", hour = "*", persistent = false)
	public void alleFuenfMinuten() {
//			coreService.speichereTemperaturen();
	}

	@Schedule(second = "0", minute = "*/10", hour = "*", persistent = false)
	public void alleZehnMinuten() {
//			coreService.poolSteuerung();
	}

	
//	@Schedule(minute = "00", hour = "23", dayOfWeek="*", persistent = false)
//	public void startePumpeAbends() {
//		coreService.schaltePumpe(Pumpe.AN);
//	}
//	
//	@Schedule(minute = "00", hour = "07", dayOfWeek="*", persistent = false)
//	public void stopePumpeMorgens() {
//		coreService.schaltePumpe(Pumpe.AUS);
//	}
}
