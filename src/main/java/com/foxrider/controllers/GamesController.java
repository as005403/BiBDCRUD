package com.foxrider.controllers;

import com.foxrider.config.SpringConfig;
import com.foxrider.dao.GameDAO;
import com.foxrider.models.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/games")
public class GamesController {
    private GameDAO gameDAO;
    private List<Game>games = new ArrayList<>();

    @Autowired
    public GamesController(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @GetMapping()
    public String index(Model model){
        games =gameDAO.findAll();
        model.addAttribute("games",games);

        return showAll();
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        return showAll();
    }
    // easy sorting with help of this
    public String showAll(){
        return "games/index";
    }

    @GetMapping("/new")
    public String newGame(@ModelAttribute("game")Game game){
        return "games/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("game")Game game) throws SQLException {
        gameDAO.create(game);
        return "redirect:/games";

    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")int id,Model model){
        model.addAttribute("game",gameDAO.entityById(id));
        return "games/edit";

    }
    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("game")Game game, @PathVariable("id")int id) throws SQLException {
        gameDAO.update(game);
        return "redirect:/games";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id")int id) throws SQLException {
        gameDAO.delete(id);
        return "redirect:/games";

    }

    @GetMapping("/sort")
    public String sort(@RequestParam(value = "col",defaultValue = "")String order,Model model){
        Comparator<Game> stidComp = (o1,o2) -> Integer.compare(o1.getStadiumId(),o2.getStadiumId());
        Comparator<Game> tnComp = (o1,o2)->o1.getTeamName().compareToIgnoreCase(o2.getTeamName());
        Comparator<Game> dateComp = (o1,o2)->o1.getDate().compareToIgnoreCase(o2.getDate());
        Comparator<Game> timeComp = (o1,o2)->o1.getTime().compareToIgnoreCase(o2.getTime());

        switch (order){
            case "stadium":
                games.sort(stidComp);
                break;
            case "tn":
                games.sort(tnComp);
                break;
            case "date":
                games.sort(dateComp);
                break;
            case "time":
                games.sort(timeComp);
                break;
        }
        model.addAttribute("games",games);
        return showAll();
    }
}
