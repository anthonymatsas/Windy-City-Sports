package com.example.anthonymatsas.windycitysports;

import java.util.UUID;

/**
 * Created by anthonymatsas on 4/12/16.
 */
public class Team {
    //Class level variables
    private UUID id;
    private String name;
    private String image;
    private String twitterName;
    private String youtubeUsrName;
    private String history;

    public Team() {
        //Set id
        id = UUID.randomUUID();
    }

    public Team(String uuid, String name, String image, String twitterName, String youtubeUsrName, String history) {
        //Team ctor
        id = UUID.fromString(uuid);
        this.name = name;
        this.image = image;
        this.history = history;
        this.twitterName = twitterName;
        this.youtubeUsrName = youtubeUsrName;
    }

    //Gets and sets
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getTwitterName() {
        return twitterName;
    }

    public void setTwitterName(String twitterName) {
        this.twitterName = twitterName;
    }

    public String getYoutubeUsrName() {
        return youtubeUsrName;
    }

    public void setYoutubeUsrName(String youtubeUsrName) {
        this.youtubeUsrName = youtubeUsrName;
    }
}
