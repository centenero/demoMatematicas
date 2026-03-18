package es.game.demo.service;

import es.game.demo.model.Persona;
import es.game.demo.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {
    
    @Autowired
    private PersonaRepository personaRepository;
    
    @Autowired
    private WebSocketNotificationService notificationService;
    
    public List<Persona> getAllPersonas() {
        return personaRepository.findAll();
    }
    
    public Optional<Persona> getPersonaById(Long id) {
        return personaRepository.findById(id);
    }
    
    public Persona savePersona(Persona persona) {
        Persona saved = personaRepository.save(persona);
        
        if (persona.getId() == null) {
            notificationService.notifyPersonaAdded(saved);
        } else {
            notificationService.notifyPersonaUpdated(saved);
        }
        
        return saved;
    }
    
    public void deletePersona(Long id) {
        personaRepository.deleteById(id);
        notificationService.notifyPersonaDeleted(id);
    }
}
