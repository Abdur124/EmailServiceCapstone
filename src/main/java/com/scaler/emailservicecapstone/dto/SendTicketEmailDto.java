package com.scaler.emailservicecapstone.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SendTicketEmailDto {

    private String username;
    private String email;
    private String movieName;
    private String theatreName;
    private String audiName;
    private int totalTickets;
    private String ticketStatus;
    private LocalDateTime showTime;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getAudiName() {
        return audiName;
    }

    public void setAudiName(String audiName) {
        this.audiName = audiName;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public LocalDateTime getShowTime() {
        return showTime;
    }

    public void setShowTime(LocalDateTime showTime) {
        this.showTime = showTime;
    }

    @Override
    public String toString() {
        return "Ticketbooking Status: {" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", movieName='" + movieName + '\'' +
                ", theatreName='" + theatreName + '\'' +
                ", audiName='" + audiName + '\'' +
                ", totalTickets=" + totalTickets +
                ", ticketStatus='" + ticketStatus + '\'' +
                ", showTime='" + showTime + '\'' +
                '}';
    }
}
