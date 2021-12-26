package de.erdtmann.soft.haussteuerung.pv;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import de.erdtmann.soft.haussteuerung.core.CoreService;
import de.erdtmann.soft.haussteuerung.pv.entities.BattLadungE;
import de.erdtmann.soft.haussteuerung.pv.entities.LeistungE;
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

	@Inject
	PvModbusClient pvModbusClient;

	@Inject
	PvRepository pvRepo;
	
	@Inject
	CoreService coreService;

	public void speichereDaten() {
				
		LocalDateTime zeit = LocalDateTime.now();
		
		float verbrauchVonBatt = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.VERBRAUCH_FROM_BAT);
		float verbrauchVonPv = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.VERBRAUCH_FROM_PV);
		float verbrauchVonNetz = pvModbusClient.holeModbusRegisterFloat(NetzFloatRegister.VERBRAUCH_FROM_GRID);
		float pvString1 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_1);
		float pvString2 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_2);
		
		
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
											.withWert(pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STAND))
											.withZeit(zeit)
											.build();
		
		pvRepo.speichereBattLadung(battLadung);
		
	}
	
	public List<BattLadungE> ladeBattLadungTag(LocalDate tag) {
		return pvRepo.holeBattLadungTag(tag);
	}
	
	public List<LeistungE> ladeVerbrauchTagTyp(LocalDate tag, int typ) {
		return pvRepo.holeLeistungTagTyp(tag, typ);
	}

	
	public BatterieDaten ladeBatterie() {

		return BatterieDaten.builder()
								.withLadeStrom(pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STROM))
								.withSpannung(pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_SPANNUNG))
								.withLadeZyklen(pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_ZYKLUS))
								.withLadeStand(pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STAND))
								.withTemperatur(pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_TEMP))
								.build();
								
	}

	public NetzDaten ladeNetz() {
		
		return NetzDaten.builder()
							.withLeistung(pvModbusClient.holeModbusRegisterFloat(NetzFloatRegister.GRID_LEISTUNG))
							.build();
	}

	public PvDaten ladePv() {
		
		Phase dcString1 = Phase.builder()
								.withStrom(pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_A_1))
								.withSpannung(pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_V_1))
								.withLeistung(pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_1))
								.build();

		Phase dcString2 = Phase.builder()
								.withStrom(pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_A_2))
								.withSpannung(pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_V_2))
								.withLeistung(pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_2))
								.build();
		
		float gesamtLeistung = dcString1.getLeistung() + dcString2.getLeistung();

		return PvDaten.builder()
						.withGesamtLeistung(gesamtLeistung)
						.withDcString1(dcString1)
						.withDcString2(dcString2)
						.build();
	}

	public Verbrauch ladeVerbrauch() {
		return Verbrauch.builder()
							.withVonBatt(pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.VERBRAUCH_FROM_BAT))
							.withVonPv(pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.VERBRAUCH_FROM_PV))
							.withVonNetz(pvModbusClient.holeModbusRegisterFloat(NetzFloatRegister.VERBRAUCH_FROM_GRID))
							.build();
	}
	
	public boolean isPvLeistungUeberMin() {
		float pvString1 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_1);
		float pvString2 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_2);
		int pvGesamt = (int) (pvString1 + pvString2);
		int minPv = coreService.holeMinPv();
		
		return pvGesamt > minPv;
	}
	
	public boolean isPvLeistungUeberMax() {
		float pvString1 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_1);
		float pvString2 = pvModbusClient.holeModbusRegisterFloat(PvFloatRegister.DC_W_2);
		int pvGesamt = (int) (pvString1 + pvString2);
		int maxPv = coreService.holeMaxPv();
		
		return pvGesamt > maxPv;
	}
	
	public boolean isBattLadungUeberMin() {
		float battLadung = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STAND);
		int minBatt = coreService.holeMinBatt();
		
		return battLadung >  minBatt;
	}

	public boolean isBattLadungUeberMax() {
		float battLadung = pvModbusClient.holeModbusRegisterFloat(BatterieFloatRegister.BATT_STAND);
		int maxBatt = coreService.holeMaxBatt();
		
		return battLadung >  maxBatt;
	}

}
