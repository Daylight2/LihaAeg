public class Tellimus implements Comparable<Tellimus> {
    private int lauaNr;
    private Liha liha; //tellimuse korral paljud muutujad kattuvad lihaga, seega loome liha isendi
    private Lisand lisand;

    public Tellimus(int lauaNr, String lihatüüp, String küpsusAste, double lihaKaal, boolean onLisandiga) {
        this.lauaNr = lauaNr;
        this.liha = new Liha (lihatüüp, lihaKaal, küpsusAste);
        if (onLisandiga) {
            this.lisand = new Lisand(liha);
        } else {
            this.lisand = null;
        }
    }

    public int getLauaNr() {
        return lauaNr;
    }

    public Liha getLiha() {
        return liha;
    }


    public void setLiha(Liha liha) {
        this.liha = liha;
    }

    @Override
    public String toString() {
        if (lisand != null) {
            return "tellimus lauast numbriga " +
                    lauaNr + ", küpsetusajaga " + liha.getKüpsetusaeg() +
                    ": " + liha.getLihaTüüp() +
                    ", " + liha.getKüpsetusTase() +
                    ", " + liha.getKaal() +
                    " grammi, lisandiga " + lisand;
        } else {
            return "tellimus lauast numbriga " +
                    lauaNr + ", küpsetusajaga " +
                    liha.getKüpsetusaeg() + ": " +
                    liha.getLihaTüüp() +
                    ", " + liha.getKüpsetusTase() +
                    ", " + liha.getKaal() +
                    " grammi. ";
        }
    }

    @Override
    public int compareTo(Tellimus o) {
        if (this.liha.getKüpsetusaeg() < o.getLiha().getKüpsetusaeg()) {
            return -1;
        }
        if (this.liha.getKüpsetusaeg() > o.getLiha().getKüpsetusaeg()) {
            return 1;
        }
        return 0;
    }
}
