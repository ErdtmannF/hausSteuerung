package de.erdtmann.soft.haussteuerung.core.beans;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.primefaces.component.tabview.Tab;
import org.primefaces.event.TabChangeEvent;


@Named
@ViewScoped
public class CoreBean implements Serializable {

	private static final long serialVersionUID = -1902491341589172170L;

	Logger log = Logger.getLogger(CoreBean.class);

	private int tabIndex = 0;

	
	public void changeTab(TabChangeEvent event) {
//		Tab activeTab = event.getTab();
//		tabIndex = activeTab.
//		log.info("Tabindex: " + tabIndex);
	}
	
	public int getTabIndex() {
		return tabIndex;
	}
	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}
	
	
}
