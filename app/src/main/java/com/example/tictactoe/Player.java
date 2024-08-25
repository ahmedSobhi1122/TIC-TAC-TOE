package com.example.tictactoe;

public class Player extends Game {
    private String name;
    private int score;
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void incrementScore() {
        score++;
    }
    public void resetScore() {
        score = 0;
    }
}
