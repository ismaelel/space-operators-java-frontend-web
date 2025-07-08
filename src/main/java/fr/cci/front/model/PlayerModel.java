package fr.cci.front.model;

/**
 * Modèle représentant un joueur (Player).
 * Sert à la fois pour l’authentification, l’administration et le jeu lui-même.
 */
public class PlayerModel {

    /**
     * Identifiant unique du joueur (UUID ou autre).
     */
    private String playerId;

    /**
     * Nom ou pseudo du joueur.
     */
    private String playerName;

    /**
     * État de préparation du joueur (utile en phase de matchmaking ou de lobby).
     */
    private Boolean isReady;

    /**
     * Nombre total de parties jouées par le joueur.
     */
    private Long gamesPlayed;

    /**
     * Rôle du joueur (ex: "ROLE_USER", "ROLE_ADMIN").
     */
    private String role;

    /**
     * Adresse email du joueur (utilisée pour l’authentification).
     */
    private String email;

    /**
     * Mot de passe du joueur (non stocké en base ici, utilisé uniquement côté client).
     */
    private String password;

    /**
     * Constructeur vide par défaut.
     */
    public PlayerModel() {}

    /**
     * Constructeur principal.
     *
     * @param playerId     identifiant du joueur
     * @param isReady      état de préparation
     * @param playerName   nom/pseudo du joueur
     * @param gamesPlayed  nombre de parties jouées
     * @param role         rôle (ADMIN/USER)
     * @param email        adresse email
     * @param password     mot de passe
     */
    public PlayerModel(String playerId, Boolean isReady, String playerName, Long gamesPlayed, String role, String email, String password) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.isReady = isReady;
        this.gamesPlayed = gamesPlayed;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructeur simplifié.
     *
     * @param playerId   identifiant
     * @param playerName nom
     * @param isReady    état prêt ou non
     */
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

    /**
     * Représentation textuelle de l’objet, sans mot de passe.
     *
     * @return chaîne lisible pour debug ou logs
     */
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
}
