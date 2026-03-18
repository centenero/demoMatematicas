package es.game.demo.service;

import es.game.demo.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketNotificationService {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    public void notifyPersonaAdded(Persona persona) {
        PersonaNotification notification = new PersonaNotification("added", persona, null);
        messagingTemplate.convertAndSend("/topic/personas", notification);
    }
    
    public void notifyPersonaUpdated(Persona persona) {
        PersonaNotification notification = new PersonaNotification("updated", persona, null);
        messagingTemplate.convertAndSend("/topic/personas", notification);
    }
    
    public void notifyPersonaDeleted(Long id) {
        PersonaNotification notification = new PersonaNotification("deleted", null, id);
        messagingTemplate.convertAndSend("/topic/personas", notification);
    }
    
    // Clase interna para la notificación
    public static class PersonaNotification {
        public String action;
        public Persona persona;
        public Long id;
        
        public PersonaNotification(String action, Persona persona, Long id) {
            this.action = action;
            this.persona = persona;
            this.id = id;
        }
        
        // Getters para serialización JSON
        public String getAction() { return action; }
        public Persona getPersona() { return persona; }
        public Long getId() { return id; }
    }
}
