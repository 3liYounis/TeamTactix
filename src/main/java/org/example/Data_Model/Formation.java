package org.example.Data_Model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Formation implements Comparable<Formation> {
    @Autowired
    private Team team1;
    @Autowired
    private Team team2;
    public Formation(List<Player> team1Players, List<Player> team2Players){
        this.team1 = new Team((ArrayList<Player>) team1Players);
        this.team2 = new Team((ArrayList<Player>) team2Players);
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }
    public boolean validGKCountDifference(){
        return team1.getGKCount()-team2.getGKCount() == 0;
    }
    public boolean validDEFCountDifference(int difference){
        return Math.abs(team1.getDEFCount()-team2.getDEFCount()) < difference;
    }
    public boolean validMIDCountDifference(int difference){
        return Math.abs(team1.getMIDCount()-team2.getMIDCount()) < difference;
    }
    public boolean validATKCountDifference(int difference){
        return Math.abs(team1.getATKCount()-team2.getATKCount()) < difference;
    }
    public boolean validDEFRatingDifference(int difference){
        return Math.abs(team1.getDEFRating() -team2.getDEFRating()) < difference;
    }
    public boolean validMIDRatingDifference(int difference){
        return Math.abs(team1.getMIDRating() -team2.getMIDRating()) < difference;
    }
    public boolean validATKRatingDifference(int difference){
        return Math.abs(team1.getATKRating()-team2.getATKRating()) < difference;
    }
    public double getCompetition(){
        return Math.abs(team1.getTotalRating() - team2.getTotalRating());
    }
    @Override
    public int hashCode() {
        return team1.hashCode() + team2.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formation formation = (Formation) o;
        return team1.equals(formation.team2);
    }

    @Override
    public int compareTo(Formation o) {
        return (int)(this.getCompetition() - ((Formation)o).getCompetition());
    }
}
