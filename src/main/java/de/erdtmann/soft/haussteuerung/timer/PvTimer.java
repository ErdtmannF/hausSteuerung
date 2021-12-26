package de.erdtmann.soft.haussteuerung.timer;

import javax.ejb.Singleton;
import javax.ejb.Schedule;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.pv.PvService;

@Singleton
public class PvTimer {

	Logger log = Logger.getLogger(PvTimer.class);
	
	@Inject
	PvService pvService;
	
	@Schedule(second = "0", minute = "*/1", hour = "*", persistent = false)
	public void jedeMinute() {
			pvService.speichereDaten();
			
	}

//	@Schedule(second = "0", minute = "*/5", hour = "*", persistent = false)
//	public void alleFuenfMinuten() {
//			
//	}

}
