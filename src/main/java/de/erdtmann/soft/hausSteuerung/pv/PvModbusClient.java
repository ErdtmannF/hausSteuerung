package de.erdtmann.soft.hausSteuerung.pv;

import java.net.UnknownHostException;
import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.jboss.logging.Logger;

import de.erdtmann.soft.hausSteuerung.pv.utils.ModbusRegister;
import de.erdtmann.soft.hausSteuerung.pv.utils.PvConstants;
import de.re.easymodbus.exceptions.ModbusException;
import de.re.easymodbus.modbusclient.ModbusClient;

@ApplicationScoped
public class PvModbusClient {

	Logger log = Logger.getLogger(PvModbusClient.class);

	private ModbusClient modbus;

	@PostConstruct
	private void init() {

		// Modbus initialisieren
		modbus = new ModbusClient(PvConstants.INVERTER_URL, PvConstants.INVERTER_PORT);
		// UnitID dem Client mitteilen
		modbus.setUnitIdentifier((byte) PvConstants.INVERTER_UNIT_ID);
	}

	private int[] holeHoldingRegister(ModbusRegister register) throws IOException, ModbusException {

		return modbus.ReadHoldingRegisters(register.getAddr(), register.getAnzahl());
	}

	public String holeModbusRegisterString(ModbusRegister register) {

		String wert = null;

		try {
			if (!modbus.isConnected()) {
				// Verbindung aufbauen

				modbus.Connect();
			}

			wert = ModbusClient.ConvertRegistersToString(holeHoldingRegister(register), 0, register.getAnzahl());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ModbusException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				modbus.Disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return wert;
	}

	public Float holeModbusRegisterFloat(ModbusRegister register) {
		Float wert = null;
		
		try {
			if (!modbus.isConnected()) {
				modbus.Connect();
			}
			wert = ModbusClient.ConvertRegistersToFloat(holeHoldingRegister(register));

		} catch (Exception e) {
			log.error("Fehler beim Abruf des Modbus Float");
			log.error(e.getMessage());
		}

		return wert;
	}

}
