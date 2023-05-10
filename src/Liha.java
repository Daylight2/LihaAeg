public class Liha {
    private String lihaTüüp;
    private double kaal;
    private String küpsetusTase;
    private double küpsetusaeg;

    public Liha(String lihaTüüp, double kaal, String küpsetusTase) {
        this.lihaTüüp = lihaTüüp;
        this.kaal = kaal;
        this.küpsetusTase = küpsetusTase;
        this.küpsetusaeg = baasKüpsetusAeg();
    }

    public void setKüpsetusaeg(double küpsetusaeg) {
        this.küpsetusaeg = küpsetusaeg;
    }

    public String getLihaTüüp() {
        return lihaTüüp;
    }

    public double getKaal() {
        return kaal;
    }

    public String getKüpsetusTase() {
        return küpsetusTase;
    }

    public double getKüpsetusaeg() {
        return küpsetusaeg;
    }

    public double baasKüpsetusAeg() {
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

        return küpsetusAeg;
    }

    //järgnevad kaks funktsiooni tõstetud eraldi tulenevalt nende parameetrite pärinemisest mitte failist vaid kasutajalt
    public void külmutatudLiha(boolean külmutatud) {
        if (külmutatud) {
            this.küpsetusaeg += 5;
        }
    }

    public void ahjuTemperatuur(double temperatuur) {
        if (temperatuur < 185) {
            this.küpsetusaeg += 8;
        } else if (temperatuur > 215) {
            this.küpsetusaeg -= 8;
        }
    }

    @Override
    public String toString() {
        return "Liha " +
                lihaTüüp +
                ", kaal " + kaal +
                ", küpsetustase " + küpsetusTase;
    }
}
