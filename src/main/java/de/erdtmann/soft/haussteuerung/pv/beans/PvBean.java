package de.erdtmann.soft.haussteuerung.pv.beans;


import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.LinearAxis;

import de.erdtmann.soft.haussteuerung.pv.PvService;
import de.erdtmann.soft.haussteuerung.pv.entities.BattLadungE;
import de.erdtmann.soft.haussteuerung.pv.entities.LeistungE;
import de.erdtmann.soft.haussteuerung.pv.model.BatterieDaten;
import de.erdtmann.soft.haussteuerung.pv.model.NetzDaten;
import de.erdtmann.soft.haussteuerung.pv.model.PvDaten;
import de.erdtmann.soft.haussteuerung.pv.model.Verbrauch;


@Named
@ViewScoped
public class PvBean implements Serializable {

	private static final long serialVersionUID = -1428278999862364206L;

	Logger log = Logger.getLogger(PvBean.class);
	
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

	private Integer progressChart = 0;
	
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
		progressChart = 0;
		battLadungTag = pvService.ladeBattLadungTag(datum);
		progressChart = progressChart + 15;
		
		tagesVerbrauchBatt = pvService.ladeVerbrauchTagTyp(datum, 1);
		progressChart = progressChart + 17;
		
		tagesVerbrauchPv = pvService.ladeVerbrauchTagTyp(datum, 2);
		progressChart = progressChart + 17;
		
		tagesVerbrauchNetz = pvService.ladeVerbrauchTagTyp(datum, 3);
		progressChart = progressChart + 17;
		
		hausverbrauchGesamt = pvService.ladeVerbrauchTagTyp(datum, 8);
		progressChart = progressChart + 17;
		
		pvLeistung = pvService.ladeVerbrauchTagTyp(datum, 6);
		progressChart = progressChart + 17;
		
		LineChartSeries battLadungChartSerie = new LineChartSeries();
		battLadungChartSerie.setLabel("Ladestand");
		battLadungChartSerie.setShowMarker(false);
		battLadungChartSerie.setFill(false);
		battLadungChartSerie.setDisableStack(true);
		battLadungChartSerie.setXaxis(AxisType.X);
		battLadungChartSerie.setYaxis(AxisType.Y2);
		
		LineChartSeries batterieChartSerie = new LineChartSeries();
		batterieChartSerie.setLabel("Verbrauch von Batterie");
		batterieChartSerie.setShowMarker(false);
		batterieChartSerie.setFill(true);
		batterieChartSerie.setFillAlpha(1);
		
		LineChartSeries pvChartSerie = new LineChartSeries();
		pvChartSerie.setLabel("Verbrauch von PV");
		pvChartSerie.setShowMarker(false);
		pvChartSerie.setFill(true);
		pvChartSerie.setFillAlpha(0.9);
		
		LineChartSeries netzChartSerie = new LineChartSeries();
		netzChartSerie.setLabel("Verbrauch von Netz");
		netzChartSerie.setShowMarker(false);
		netzChartSerie.setFill(true);
		netzChartSerie.setFillAlpha(0.75);
	
		LineChartSeries pvDachChartSerie = new LineChartSeries();
		pvDachChartSerie.setLabel("PV Leistung");
		pvDachChartSerie.setDisableStack(true);
		pvDachChartSerie.setShowMarker(false);
		pvDachChartSerie.setFill(true);
		pvDachChartSerie.setFillAlpha(0.50);

		LineChartSeries hausverbrauchChartSerie = new LineChartSeries();
		hausverbrauchChartSerie.setLabel("Hausverbrauch");
		hausverbrauchChartSerie.setDisableStack(true);
		hausverbrauchChartSerie.setShowMarker(false);
		hausverbrauchChartSerie.setFill(false);
		

		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		for (BattLadungE value : battLadungTag) {
			battLadungChartSerie.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : tagesVerbrauchBatt) {
			batterieChartSerie.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : tagesVerbrauchNetz) {
			netzChartSerie.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : tagesVerbrauchPv) {
			pvChartSerie.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : pvLeistung) {
			pvDachChartSerie.set(value.getZeit().format(dtf), value.getWert());
		}
		for (LeistungE value : hausverbrauchGesamt) {
			hausverbrauchChartSerie.set(value.getZeit().format(dtf), value.getWert());
		}
		

		pvChart = new LineChartModel();
		pvChart.setZoom(true);
		pvChart.setShadow(false);
		pvChart.setAnimate(true);
		pvChart.setStacked(true);
		
		pvChart.addSeries(netzChartSerie);
		pvChart.addSeries(pvChartSerie);
		pvChart.addSeries(batterieChartSerie);
		
		
		pvChart.addSeries(pvDachChartSerie);
		pvChart.addSeries(battLadungChartSerie);
		
		pvChart.addSeries(hausverbrauchChartSerie);
		
		pvChart.setSeriesColors("fc0303,00fc19,006dfc,fcee30,000000,666666");

		// 00fc19 gr�n
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
	
	public void showProgress() {
		PrimeFaces.current().dialog().openDynamic("progressDialog");
	}
	
	public void hideProgress() {
		PrimeFaces.current().dialog().closeDynamic(null);
		progressChart = 0;		
	}
	
	public void datumPlus() {
		log.warn("Vor Show Progress");
		showProgress();
		log.warn("Nach Show Progress");
		datum = datum.plusDays(1);
		log.warn("Vor LadeChart");
		ladeChart();
		log.warn("Nach LadeChart");
		log.warn("Vor Hide Progress");
//		hideProgress();
		log.warn("Nach Hide Progress");
	}
	
	public void datumMinus() {
		log.warn("Vor Show Progress");
		showProgress();
		log.warn("Nach Show Progress");
		datum = datum.minusDays(1);
		log.warn("Vor LadeChart");
		ladeChart();
		log.warn("Nach LadeChart");
		log.warn("Vor Hide Progress");
//		hideProgress();
		log.warn("Nach Hide Progress");
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

	public Integer getProgressChart() {
		return progressChart;
	}
	
	
	
}
