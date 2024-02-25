package org.example.Controller;

import org.example.Data_Model.Formation;
import org.example.Data_Model.Player;
import org.example.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
//@RequestMapping("/players")
public class Controller {
    @Autowired
    private PlayerService service;
    @GetMapping(path = "/players")
    public Iterable<Player> test(){
        return service.getAllPlayers();
    }
    @PostMapping(path = "/players")
    public Iterable<Formation> createStudent(@RequestBody Iterable<Player> players) {
        return service.postAllPlayers(players);
    }


}
