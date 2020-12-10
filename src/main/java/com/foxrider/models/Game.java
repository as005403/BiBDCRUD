package com.foxrider.models;


import com.foxrider.tools.Validator;

public class Game{
    private int id;
    private int stadiumId;
    private String teamName;
    private String date;
    private String time;

    public Game(int stadiumId, String teamName, String date, String time) {
        this.stadiumId = stadiumId;
        this.teamName = teamName;
        setDate(date);
        setTime(time);
    }

    public Game(){}

    public int getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(int stadiumId) {
        this.stadiumId = stadiumId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        Validator validator = new Validator();
        if (validator.validateDate(date))
            this.date = date;
        else throw new IllegalArgumentException("Validator date exeption");

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        Validator validator = new Validator();
        if (validator.validateTime(time))
            this.time = time;
        else throw new IllegalArgumentException("Validator time exeption");

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Game{" +
                "stadiumId='" + stadiumId + '\'' +
                ", teamName='" + teamName + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
