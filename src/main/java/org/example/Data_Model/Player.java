package org.example.Data_Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Players")
public class Player {
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String position;
    private double rating;
    public Player(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Double.compare(rating, player.rating) == 0 && Objects.equals(id, player.id) && Objects.equals(name, player.name) && Objects.equals(position, player.position);
    }
}
