package me.dcal.gamemanager.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import me.dcal.gamemanager.service.GameService;

import java.io.IOException;
import java.text.ParseException;


@Controller
public class Accueil {
	
	@Autowired 
	GameService gameService;

    @GetMapping("/")
    public String accueil(Model model) throws ParseException, IOException {
        return "dorian-home";
    }
    
    @GetMapping("/form")
    public String form(Model model) throws ParseException, IOException {
        return "form";
    }
    
    @GetMapping("/gamelist")
    public String gamelist(Model model
    		, @RequestParam(required = false) String name
    		, @RequestParam(required = false) String genre
    		, @RequestParam(required = false) String platform
    		, @RequestParam(required = false) String publisher) 
    {
    	model.addAttribute("games", gameService.getListGame(
    			name, genre, platform,publisher
    			).getGameList());
        return "list-game";
    }
    
    @GetMapping("/about")
    public String about(Model model) throws ParseException, IOException {
        return "about";
    }
    
    @GetMapping("/gameview")
    public String gameveiw(Model model, @RequestParam String url) {
    	
    	model.addAttribute("game", gameService.getGame(url));
        return "idGame";
    }
    
   
   
}