package com.example.ainunrentcar.Model;

import java.io.Serializable;

public class PlayerItem implements Serializable {
    String no, name, Position, birth_date, Poster;

    public PlayerItem(String no, String name, String Position, String birth_date, String Poster) {
        this.no = no;
        this.name = name;
        this.Position = Position;
        this.birth_date = birth_date;
        this.Poster = Poster;
    }


    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return Position;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getPoster() {
        return Poster;
    }
}
