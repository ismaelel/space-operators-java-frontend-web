package fr.cci.front.model;

public class PlayerModel {
    private String playerId;
    private String playerName;
    private Boolean isReady;
    //private String name;
    private Long gamesPlayed;
    private String role;
    private String email;
    private String password;

    public PlayerModel() {
    }

    public PlayerModel(String playerId, Boolean isReady, String name, Long gamesPlayed, String role, String email, String password) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.isReady = isReady;
        // this.name = name;
        this.gamesPlayed = gamesPlayed;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    // Constructeurs, getters et setters
    public PlayerModel(String playerId, String playerName, Boolean isReady) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.isReady = isReady;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Boolean getReady() {
        return isReady;
    }

    public void setReady(Boolean ready) {
        isReady = ready;
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "playerId='" + playerId + '\'' +
                ", isReady=" + isReady +
                ", playerName='" + playerName + '\'' +
                ", gamesPlayed=" + gamesPlayed +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public Long getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Long gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
