package escritor.Control;

public class GameMetadata {
    private String event;
    private String site;
    private String date;
    private String round;
    private String whitePlayer;
    private String blackPlayer;
    private String result;

    public GameMetadata(String text, String siteFieldText, String dateFieldText, String roundFieldText, String whitePlayerFieldText, String blackPlayerFieldText, String resultFieldText) {
        this.event = text;
        this.site = siteFieldText;
        this.date = dateFieldText;
        this.round = roundFieldText;
        this.whitePlayer = whitePlayerFieldText;
        this.blackPlayer = blackPlayerFieldText;
        this.result = resultFieldText; // Default result for ongoing games
    }

    // Getters and Setters
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(String whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "[Event \"" + event + "\"]\n" +
                "[Site \"" + site + "\"]\n" +
                "[Date \"" + date + "\"]\n" +
                "[Round \"" + round + "\"]\n" +
                "[White \"" + whitePlayer + "\"]\n" +
                "[Black \"" + blackPlayer + "\"]\n" +
                "[Result \"" + result + "\"]";
    }
}
