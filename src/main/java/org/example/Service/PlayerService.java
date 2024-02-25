package org.example.Service;

import org.example.Data_Model.Formation;
import org.example.Data_Model.Player;
import org.example.Data_Model.Team;
import org.example.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@org.springframework.stereotype.Service
public class PlayerService {
    @Autowired
    private PlayerRepository repo;
    public Iterable<Player> getAllPlayers() {
        return repo.findAll();
    }

    public Iterable<Formation> postAllPlayers(Iterable<Player> players) {
        for (Player p : players) {
            Player existingPlayer = repo.findByName(p.getName());
            if (existingPlayer != null) {
                double newRating = Math.round((p.getRating()+existingPlayer.getRating())/2 * 10.0) / 10.0;
                p.setRating(newRating);
                p.setId(existingPlayer.getId());
            }
                repo.save(p);
        }
        ArrayList<Formation> formations = (ArrayList<Formation>) generateFormations((List<Player>) players);
        Set<Formation> uniqueFormationsSet = new HashSet<>(formations);
        ArrayList<Formation> uniqueFormations = new ArrayList<>(uniqueFormationsSet);
        ArrayList<Formation> competitiveFormations = new ArrayList<>();
        int countDifference = 2;
        int ratingDifference = 3;
        for (Formation f : uniqueFormations){
                if (f.validGKCountDifference() && f.validDEFCountDifference(countDifference) && f.validMIDCountDifference(countDifference) && f.validATKCountDifference(countDifference))
                    if (f.validDEFRatingDifference(ratingDifference) && f.validMIDRatingDifference(ratingDifference) && f.validATKRatingDifference(ratingDifference))
                        competitiveFormations.add(f);
        }
        System.out.println(competitiveFormations.size());
        Collections.sort(competitiveFormations);
        return competitiveFormations;
    }
    public static List<Formation> generateFormations(List<Player> players) {
        List<Formation> formations = new ArrayList<>();
        int n = players.size();
        int k = 7;

        int[] indices = new int[k];
        for (int i = 0; (indices[i] = i) < k - 1; i++) ;
        formations.add(getFormation(players, indices));

        while (true) {
            int i;
            for (i = k - 1; i >= 0 && indices[i] == n - k + i; i--) ;
            if (i < 0) {
                break;
            }
            indices[i]++;
            for (++i; i < k; i++) {
                indices[i] = indices[i - 1] + 1;
            }
            formations.add(getFormation(players, indices));
        }

        return formations;
    }

    private static Formation getFormation(List<Player> players, int[] indices) {
        List<Player> team1Players = new ArrayList<>();
        List<Player> team2Players = new ArrayList<>();
        for (int index : indices) {
            team1Players.add(players.get(index));
        }
        for (int i = 0; i < players.size(); i++) {
            if (!containsIndex(indices, i)) {
                team2Players.add(players.get(i));
            }
        }
        return new Formation(team1Players, team2Players);
    }

    private static boolean containsIndex(int[] indices, int index) {
        for (int i : indices) {
            if (i == index) {
                return true;
            }
        }
        return false;
    }
}
