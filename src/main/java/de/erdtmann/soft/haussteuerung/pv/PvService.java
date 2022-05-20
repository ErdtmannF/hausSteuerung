package de.erdtmann.soft.haussteuerung.pv;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.erdtmann.soft.haussteuerung.core.CoreService;
import de.erdtmann.soft.haussteuerung.pv.entities.BattLadungE;
import de.erdtmann.soft.haussteuerung.pv.entities.LeistungE;
import de.erdtmann.soft.haussteuerung.pv.exceptions.PvException;
import de.erdtmann.soft.haussteuerung.pv.model.BatterieDaten;
import de.erdtmann.soft.haussteuerung.pv.model.NetzDaten;
import de.erdtmann.soft.haussteuerung.pv.model.Phase;
import de.erdtmann.soft.haussteuerung.pv.model.PvDaten;
import de.erdtmann.soft.haussteuerung.pv.model.Verbrauch;
import de.erdtmann.soft.haussteuerung.pv.utils.BatterieFloatRegister;
import de.erdtmann.soft.haussteuerung.pv.utils.NetzFloatRegister;
import de.erdtmann.soft.haussteuerung.pv.utils.PvFloatRegister;


@ApplicationScoped
public class PvService {

	Logger log = Logger.getLogger(PvService.class);
	
	@Inject
	PvModbusClient pvModbusClient;

	@Inject
	PvRepository pvRepo;

	public void speichereDaten() throws PvException {
				
		if (pvModbusClient != null) {
			LocalDateTime zeit = LocalDateTime.now();

			float verbrauchVonBatt = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.VERBRAUCH_FROM_BAT); 
			float verbrauchVonPv = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.VERBRAUCH_FROM_PV);
			float verbrauchVonNetz = pvModbusClient.holeModbusRegisterFloat(NetzFloatRegister.VERBRAUCH_FROM_GRID); 
			float pvString1 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_1);
			float pvString2 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_2);
			float battLadeStand = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STAND);
			
			LeistungE verbrauchBatt = LeistungE.builder()
												.withTyp(1)
												.withWert((verbrauchVonBatt < 0) ? 0 : verbrauchVonBatt)
												.withZeit(zeit)
												.build();
	
			LeistungE verbrauchPv = LeistungE.builder()
												.withTyp(2)
												.withWert((verbrauchVonPv < 0) ? 0 : verbrauchVonPv)
												.withZeit(zeit)
												.build();
	
			LeistungE verbrauchGrid = LeistungE.builder()
												.withTyp(3)
												.withWert((verbrauchVonNetz < 0) ? 0 : verbrauchVonNetz)
												.withZeit(zeit)
												.build();
	
			LeistungE pvLeistung1 = LeistungE.builder()
												.withTyp(4)
												.withWert((pvString1 < 0) ? 0 : pvString1)
												.withZeit(zeit)
												.build();
			
			LeistungE pvLeistung2 = LeistungE.builder()
												.withTyp(5)
												.withWert((pvString2 < 0) ? 0 : pvString2)
												.withZeit(zeit)
												.build();
			
			LeistungE pvLeistung = LeistungE.builder()
												.withTyp(6)
												.withWert(((pvString1 + pvString2) < 0) ? 0 : (pvString1 + pvString2))
												.withZeit(zeit)
												.build();
			
			LeistungE pvOhneVerbrauch = LeistungE.builder()
												.withTyp(7)
												.withWert((((pvString1 + pvString2) - verbrauchVonPv) < 0) ? 0 : ((pvString1 + pvString2) - verbrauchVonPv))
												.withZeit(zeit)
												.build();
	
			LeistungE hausverbrauchGesamt = LeistungE.builder()
												.withTyp(8)
												.withWert(verbrauchVonBatt + verbrauchVonPv + ((verbrauchVonNetz < 0) ? 0 : verbrauchVonNetz))
												.withZeit(zeit)
												.build();
	
			
			pvRepo.speichereLeistung(verbrauchPv);
			pvRepo.speichereLeistung(verbrauchBatt);
			pvRepo.speichereLeistung(verbrauchGrid);
			pvRepo.speichereLeistung(pvLeistung1);
			pvRepo.speichereLeistung(pvLeistung2);
			pvRepo.speichereLeistung(pvLeistung);
			pvRepo.speichereLeistung(pvOhneVerbrauch);
			pvRepo.speichereLeistung(hausverbrauchGesamt);
			
			BattLadungE battLadung = BattLadungE.builder()
												.withWert(battLadeStand)
												.withZeit(zeit)
												.build();
			
			pvRepo.speichereBattLadung(battLadung);
			
			log.info("PV Daten wurden gespeichert");
		}
	}
	
	public List<BattLadungE> ladeBattLadungTag(LocalDate tag) {
		return pvRepo.holeBattLadungTag(tag);
	}
	
	public List<LeistungE> ladeVerbrauchTagTyp(LocalDate tag, int typ) {
		return pvRepo.holeLeistungTagTyp(tag, typ);
	}

	
	public BatterieDaten ladeBatterie() throws PvException {

		float battStrom = 999;
		float battSpannung = 999;
		float battZyklus = 999;
		float battStand = 999;
		float battTemp = 999;
		
		if (pvModbusClient != null) {
			battStrom = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STROM);
			battSpannung = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_SPANNUNG);
			battZyklus = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_ZYKLUS);
			battStand = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STAND);
			battTemp = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_TEMP);
		}

		return BatterieDaten.builder()
							.withLadeStrom(battStrom)
							.withSpannung(battSpannung)
							.withLadeZyklen(battZyklus)
							.withLadeStand(battStand)
							.withTemperatur(battTemp)
							.build();
	}

	public NetzDaten ladeNetz() throws PvException {

		float gridLeistung = 999;
		
		if (pvModbusClient != null) {
			gridLeistung = pvModbusClient.holeModbusRegisterFloat(NetzFloatRegister.GRID_LEISTUNG);
		}
		return NetzDaten.builder()
				.withLeistung(gridLeistung)
				.build();

	}

	public PvDaten ladePv() throws PvException {

		float dcA1 = 999;
		float dcV1 = 999;
		float dcW1 = 999;

		float dcA2 = 999;
		float dcV2 = 999;
		float dcW2 = 999;

		float gesamtLeistung = 999;
		
		if (pvModbusClient != null) {

			dcA1 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_A_1);
			dcV1 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_V_1);
			dcW1 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_1);
			dcA2 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_A_2);
			dcV2 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_V_2);
			dcW2 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_2);
			
			gesamtLeistung = dcW1 + dcW2;
		}

		Phase dcString1 = Phase.builder()
								.withStrom(dcA1)
								.withSpannung(dcV1)
								.withLeistung(dcW1)
								.build();	

		Phase dcString2 = Phase.builder()
								.withStrom(dcA2)
								.withSpannung(dcV2)
								.withLeistung(dcW2)
								.build();
		
		return PvDaten.builder()
						.withGesamtLeistung(gesamtLeistung)
						.withDcString1(dcString1)
						.withDcString2(dcString2)
						.build();
	}

	
	public Verbrauch ladeVerbrauch() throws PvException {
		
		float verbrauchBatt = 999;
		float verbrauchPv = 999;
		float verbrauchGrid = 999;
		
		if (pvModbusClient != null) {
			verbrauchBatt = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.VERBRAUCH_FROM_BAT);
			verbrauchPv = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.VERBRAUCH_FROM_PV);
			verbrauchGrid = pvModbusClient.holeModbusRegisterFloat(NetzFloatRegister.VERBRAUCH_FROM_GRID);
		}
		
		return Verbrauch.builder()
						.withVonBatt(verbrauchBatt)
						.withVonPv(verbrauchPv)
						.withVonNetz(verbrauchGrid)
						.build();
	}

}
