package org.example.Repository;

import org.example.Data_Model.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {
    Player findByName(String name);
}
