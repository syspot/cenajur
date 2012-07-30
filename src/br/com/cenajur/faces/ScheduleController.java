package br.com.cenajur.faces;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@SessionScoped
@ManagedBean(name = "scheduleController")
public class ScheduleController {

	private ScheduleModel eventModel;  
    
    private ScheduleModel lazyEventModel;  
  
    private ScheduleEvent event = new DefaultScheduleEvent();  
      
    private String theme;  
    
    private Date previousDay8Pm(){
    	return new Date();
    }
    private Date previousDay11Pm(){
    	return new Date();
    }
    private Date today1Pm(){
    	return new Date();
    }
    private Date today6Pm(){
    	return new Date();
    }
    private Date nextDay9Am(){
    	return new Date();
    }
    private Date nextDay11Am(){
    	return new Date();
    }
    private Date theDayAfter3Pm(){
    	return new Date();
    }
    private Date fourDaysLater3pm(){
    	return new Date();
    }
  
    public ScheduleController() {  
        eventModel = new DefaultScheduleModel();  
        eventModel.addEvent(new DefaultScheduleEvent("Julgamento do Processo xxx", previousDay8Pm(), previousDay11Pm()));  
        eventModel.addEvent(new DefaultScheduleEvent("Aniversário do Presidente", today1Pm(), today6Pm()));  
        eventModel.addEvent(new DefaultScheduleEvent("Reunião de execução planejada de tarefas", nextDay9Am(), nextDay11Am()));  
        eventModel.addEvent(new DefaultScheduleEvent("Visita do cliente XYZ", theDayAfter3Pm(), fourDaysLater3pm()));  
          
        lazyEventModel = new LazyScheduleModel() {  
              
            public String fetchEvents(Date start, Date end) {  
                clear();  
                  
                Date random = new Date();  
                addEvent(new DefaultScheduleEvent("Reunião 1", random, random));  
                  
                random = new Date();  
                addEvent(new DefaultScheduleEvent("Café da manhã 1", random, random));
                return "sucesso";
            }     
        };  
    }  
      
    public String addEvent(ActionEvent actionEvent) {  
        if(event.getId() == null)  
            eventModel.addEvent(event);  
        else  
            eventModel.updateEvent(event);  
          
        event = new DefaultScheduleEvent();  
        return "sucesso";
    }  
      
    public String onEventSelect(ScheduleEntrySelectEvent selectEvent) {  
        event = selectEvent.getScheduleEvent();  
        return "sucesso";
    }  
      
    public String onDateSelect(DateSelectEvent selectEvent) {  
        event = new DefaultScheduleEvent("Novo Evento", selectEvent.getDate(), selectEvent.getDate());  
        return "sucesso";
    }  
      
    public String onEventMove(ScheduleEntryMoveEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento movido ", "Dia:" + event.getDayDelta() + ", Minuto:" + event.getMinuteDelta());  
          
        addMessage(message);  
        return "sucesso";
    }  
      
    public String onEventResize(ScheduleEntryResizeEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento modificado", "Dia:" + event.getDayDelta() + ", Minuto:" + event.getMinuteDelta());  
          
        addMessage(message);  
        return "sucesso";
    }  
      
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }
	public ScheduleModel getEventModel() {
		return eventModel;
	}
	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}
	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}
	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
	}
	public ScheduleEvent getEvent() {
		return event;
	}
	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
}
