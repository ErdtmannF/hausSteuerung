package de.erdtmann.soft.hausSteuerung.pv.beans;


import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

import de.erdtmann.soft.hausSteuerung.pv.PvService;
import de.erdtmann.soft.hausSteuerung.pv.entities.BattLadungE;
import de.erdtmann.soft.hausSteuerung.pv.entities.LeistungE;
import de.erdtmann.soft.hausSteuerung.pv.model.BatterieDaten;
import de.erdtmann.soft.hausSteuerung.pv.model.NetzDaten;
import de.erdtmann.soft.hausSteuerung.pv.model.PvDaten;
import de.erdtmann.soft.hausSteuerung.pv.model.Verbrauch;


@Named
@ViewScoped
public class PvBean implements Serializable {

	private static final long serialVersionUID = -1428278999862364206L;

	@Inject
	PvService pvService;
	
	private BatterieDaten batt;
	private PvDaten pv;
	private NetzDaten netz;
	private Verbrauch verbrauch;
	
	private LineChartModel pvChart;
	
	private List<BattLadungE> battLadungTag;
	
	private List<LeistungE> tagesVerbrauchBatt;
	private List<LeistungE> tagesVerbrauchNetz;
	private List<LeistungE> tagesVerbrauchPv;
	private List<LeistungE> hausverbrauchGesamt;
	private List<LeistungE> pvLeistung;

	private LocalDate datum;

	
	@PostConstruct
	public void init() {
		ladePvDaten();
		datum = LocalDate.now();
		ladeChart();	
	}

	public void ladePvDaten() {
		batt = pvService.ladeBatterie();
		pv = pvService.ladePv();
		netz = pvService.ladeNetz();
		verbrauch = pvService.ladeVerbrauch();
	}
	
	public void ladeChart() {
		battLadungTag = pvService.ladeBattLadungTag(datum);

		tagesVerbrauchBatt = pvService.ladeVerbrauchTagTyp(datum, 1);
		tagesVerbrauchPv = pvService.ladeVerbrauchTagTyp(datum, 2);
		tagesVerbrauchNetz = pvService.ladeVerbrauchTagTyp(datum, 3);
		hausverbrauchGesamt = pvService.ladeVerbrauchTagTyp(datum, 8);
		pvLeistung = pvService.ladeVerbrauchTagTyp(datum, 6);
		
		LineChartSeries battLadung = new LineChartSeries();
		battLadung.setLabel("Ladestand");
		battLadung.setShowMarker(false);
		battLadung.setFill(false);
		battLadung.setDisableStack(true);
		battLadung.setXaxis(AxisType.X);
		battLadung.setYaxis(AxisType.Y2);
		
		LineChartSeries batterie = new LineChartSeries();
		batterie.setLabel("Verbrauch von Batterie");
		batterie.setShowMarker(false);
		batterie.setFill(true);
		batterie.setFillAlpha(1);
		
		LineChartSeries pv = new LineChartSeries();
		pv.setLabel("Verbrauch von PV");
		pv.setShowMarker(false);
		pv.setFill(true);
		pv.setFillAlpha(0.9);
		
		LineChartSeries netz = new LineChartSeries();
		netz.setLabel("Verbrauch von Netz");
		netz.setShowMarker(false);
		netz.setFill(true);
		netz.setFillAlpha(0.75);
	
		LineChartSeries pvDach = new LineChartSeries();
		pvDach.setLabel("PV Leistung");
		pvDach.setDisableStack(true);
		pvDach.setShowMarker(false);
		pvDach.setFill(true);
		pvDach.setFillAlpha(0.50);

		LineChartSeries hausverbrauch = new LineChartSeries();
		hausverbrauch.setLabel("Hausverbrauch");
		hausverbrauch.setDisableStack(true);
		hausverbrauch.setShowMarker(false);
		hausverbrauch.setFill(false);
		

		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		for (BattLadungE value : battLadungTag) {
			battLadung.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : tagesVerbrauchBatt) {
			batterie.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : tagesVerbrauchNetz) {
			netz.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : tagesVerbrauchPv) {
			pv.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : pvLeistung) {
			pvDach.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : hausverbrauchGesamt) {
			hausverbrauch.set(value.getZeit().format(dtf), value.getWert());
		}
		

		pvChart = new LineChartModel();
		pvChart.setZoom(true);
		pvChart.setShadow(false);
		pvChart.setAnimate(true);
		pvChart.setStacked(true);
		
		pvChart.addSeries(netz);
		pvChart.addSeries(pv);
		pvChart.addSeries(batterie);
		
		
		pvChart.addSeries(pvDach);
		pvChart.addSeries(battLadung);
		
		pvChart.addSeries(hausverbrauch);
		
		pvChart.setSeriesColors("fc0303,00fc19,006dfc,fcee30,000000,666666");

		// 00fc19 grün
		// 006dfc blau
		// fc0303 rot
		// 000000 schwarz
		// fcee30 gelb
		// 666666 grau
		
		
		Axis yAxis = pvChart.getAxis(AxisType.Y);
		yAxis.setLabel("W");
		yAxis.setMin(0);
//		yAxis.setMax(9000);
		
		Axis y2Axis = new LinearAxis("%");
		y2Axis.setMin(0);
		y2Axis.setMax(100);
		pvChart.getAxes().put(AxisType.Y2, y2Axis);
		
		DateAxis xAxis = new DateAxis("Zeit");
		xAxis.setTickAngle(-45);
		xAxis.setTickFormat("%H:%M");
		pvChart.getAxes().put(AxisType.X, xAxis);

	}
	
	public void datumPlus() {
		datum = datum.plusDays(1);
		ladeChart();
	}
	
	public void datumMinus() {
		datum = datum.minusDays(1);
		ladeChart();
	}
	
	public BatterieDaten getBatt() {
		return batt;
	}

	public PvDaten getPv() {
		return pv;
	}

	public NetzDaten getNetz() {
		return netz;
	}

	public Verbrauch getVerbrauch() {
		return verbrauch;
	}

	public LineChartModel getPvChart() {
		return pvChart;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	
	
	
}
