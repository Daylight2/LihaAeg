public class Lisand {
    private static String[] veiseLisandid = {"ahjukartulid", "kartulilootsikud", "rohelised oad", "sparglid", "kartulipuder"};
    private static String[] sealihaLisandid = {"ahjukartulid", "kartulilootsikud", "ahjuporgandid", "marineeritud peedid", "keedukartulid", "tatar"};
    private static String[] linnuLisandid = {"ahjukartulid", "kartulilootsikud", "rohelised oad", "riis", "kuskus", "grillitud paprika"};
    private String valitudLisand;
    public Lisand(Liha liha) {
        if (liha.getLihaTüüp().equalsIgnoreCase("veiseliha")) {
            int juhuslikArv = (int) (Math.random() * (veiseLisandid.length));
            String juhuslikultGenereeritudLisand = veiseLisandid[juhuslikArv];
            this.valitudLisand = juhuslikultGenereeritudLisand;
        }
        else if (liha.getLihaTüüp().equalsIgnoreCase("sealiha")) {
            int juhuslikArv = (int) (Math.random() * (sealihaLisandid.length));
            String juhuslikultGenereeritudLisand = sealihaLisandid[juhuslikArv];
            this.valitudLisand = juhuslikultGenereeritudLisand;
        }
        else if (liha.getLihaTüüp().equalsIgnoreCase("kanaliha") | (liha.getLihaTüüp().equalsIgnoreCase("kalkuniliha"))) {
            int juhuslikArv = (int) (Math.random() * (linnuLisandid.length));
            String juhuslikultGenereeritudLisand = linnuLisandid[juhuslikArv];
            this.valitudLisand = juhuslikultGenereeritudLisand;
        }
    }

    @Override
    public String toString() {
        return valitudLisand;
    }
}
