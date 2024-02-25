package org.example.Data_Model;

import java.util.ArrayList;
import java.util.Objects;

public class Team {
    private ArrayList<Player> players;

    public Team(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public int getGKCount(){
        int counter=0;
        for (Player p : players)
            if (p.getPosition().contains("GK"))
                counter++;
        return counter;
    }
    public int getDEFCount(){
        int counter=0;
        for (Player p : players)
            if (p.getPosition().contains("DEF"))
                counter++;
        return counter;
    }
    public int getMIDCount(){
        int counter=0;
        for (Player p : players)
            if (p.getPosition().contains("MID"))
                counter++;
        return counter;
    }
    public int getATKCount(){
        int counter=0;
        for (Player p : players)
            if (p.getPosition().contains("ATK"))
                counter++;
        return counter;
    }
    public double getGKRating(){
        double sum = 0;
        for (Player p : players)
            if (p.getPosition().contains("GK"))
                sum+=p.getRating();
        return sum;
    }
    public double getDEFRating(){
        double sum = 0;
        for (Player p : players)
            if (p.getPosition().contains("DEF"))
                sum+=p.getRating();
        return sum;
    }
    public double getMIDRating(){
        double sum = 0;
        for (Player p : players)
            if (p.getPosition().contains("MID"))
                sum+=p.getRating();
        return sum;
    }
    public double getATKRating(){
        double sum = 0;
        for (Player p : players)
            if (p.getPosition().contains("ATK"))
                sum+=p.getRating();
        return sum;
    }
    public double getTotalRating() {
        double sum = 0;
        for (Player p: players)
            sum+=p.getRating();
        return sum;
    }
    @Override
    public int hashCode() {
        return players.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return players.equals(team.players);
    }
}
