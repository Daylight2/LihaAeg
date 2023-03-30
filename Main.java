import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Soovime olla abiks teie poolt valitud lihale õige küpsetusaja määramisel. Lisaks pakume ka ühe võimaliku lisandi, mis antud lihaga sobida võiks.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Sisesta lihatüüp (veiseliha, sealiha, kanaliha, kalkuniliha): ");
        String lihaTüüp = scanner.nextLine();

        System.out.print("Sisestage liha kaal (grammides): ");
        double kaal = scanner.nextDouble();

        System.out.print("Sisestage soovitud küpsusaste (rare, medium-rare, medium, medium-well, well-done): ");
        String küpsetusTase = scanner.next();

        System.out.print("Sisestage ahju temperatuur: ");
        int temperatuur = scanner.nextInt();

        System.out.print("Kas liha on külm? (true/false): ");
        boolean külmutatud = scanner.nextBoolean();

        if (lihaTüüp.equalsIgnoreCase("veiseliha")
                | lihaTüüp.equalsIgnoreCase("sealiha")
                | lihaTüüp.equalsIgnoreCase("kanaliha")
                | lihaTüüp.equalsIgnoreCase("kalkuniliha")) {
            Liha liha = new Liha(lihaTüüp, kaal, küpsetusTase, temperatuur, külmutatud);
            Lisand lisand = new Lisand(liha);

            System.out.println(liha);
            System.out.println(lisand);;
        } else {
            System.out.println("Sinu sisestatud lihaga ei oska kahjuks aidata.");
        }
    }
}