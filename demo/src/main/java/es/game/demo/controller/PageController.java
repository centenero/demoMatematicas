package es.game.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping("/personas")
    public String personasPage() {
        return "forward:/personas.html";
    }
}
