public class Liha {
    private String lihaTüüp;
    private double kaal;
    private String küpsetusTase;
    private int temperatuur;
    private boolean külmutatud;

    public Liha(String lihaTüüp, double kaal, String küpsetusTase, int temperatuur, boolean külmutatud) {
        this.lihaTüüp = lihaTüüp;
        this.kaal = kaal;
        this.küpsetusTase = küpsetusTase;
        this.temperatuur = temperatuur;
        this.külmutatud = külmutatud;
    }

    public String getLihaTüüp() {
        return lihaTüüp;
    }

    public double küpsetusAeg() {
        double küpsetusAeg = 0.0;

        switch (lihaTüüp.toLowerCase()) {
            case "veiseliha":
                küpsetusAeg = 30;
                break;
            case "sealiha":
                küpsetusAeg = 35;
                break;
            case "kanaliha":
                küpsetusAeg = 40;
                break;
            case "kalkuniliha":
                küpsetusAeg = 45;
                break;
            default:
                System.out.println("Kahjuks sellist lihatüüpi me ei tea.");
        }
        if (kaal > 500) {
            küpsetusAeg = küpsetusAeg * (1 + kaal / 1000);
        }

        if (küpsetusTase.equalsIgnoreCase("rare")) {
            küpsetusAeg -= 10;
        } else if (küpsetusTase.equalsIgnoreCase("medium-rare")) {
            küpsetusAeg -= 5;
        } else if (küpsetusTase.equalsIgnoreCase("medium-well")) {
            küpsetusAeg += 5;
        } else if (küpsetusTase.equalsIgnoreCase("well-done")) {
            küpsetusAeg += 10;
        }

        if (külmutatud) {
            küpsetusAeg += 5;
        }

        if (temperatuur < 185) {
            küpsetusAeg += 8;
        } else if (temperatuur > 215) {
            küpsetusAeg -= 8;
        }

        return küpsetusAeg;
    }

    public double ahjuSoojenemine() {
        double ahiSoojeneb = 0.0;
        if (temperatuur < 185) {
            ahiSoojeneb = 8.0;
            return ahiSoojeneb;
        } else {
            return ahiSoojeneb;
        }
    }

    @Override
    public String toString() {
        if (ahjuSoojenemine() != 0.0) {
            return "Soovite küpsetada " + lihaTüüp +
                    " kaaluga " + kaal + " grammi." +
                    " Selleks, et saavutada " + '\'' + küpsetusTase + '\'' +
                    " küpsusaste, tuleks liha küpsetada umbes " + küpsetusAeg() +
                    " minutit. Lase enne ahjul soojeneda " + ahjuSoojenemine() + " minutit!";
        } else {
            return "Soovite küpsetada " + lihaTüüp +
                    " kaaluga " + kaal + " grammi." +
                    " Selleks, et saavutada " + '\'' + küpsetusTase + '\'' +
                    " küpsusaste, tuleks liha küpsetada umbes " + küpsetusAeg() +
                    " minutit. Ahi on juba soe ning võite liha kohe küpsema panna!";
        }
    }
}
