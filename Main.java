import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Sisesta lihatüüp (veiseliga, sealiha, kanaliha, kanaliha, kalkuniliha): ");
        String lihaTüüp = scanner.nextLine();

        System.out.print("Sisestage liha kaal (grammides): ");
        double weight = scanner.nextDouble();

        System.out.print("Sisestage soovitud küpsusaste (rare, medium-rare, medium, medium-well, well-done): ");
        String küpsetusTase = scanner.next();

        System.out.print("Sisestage ahju temperatuur: ");
        int temperatuur = scanner.nextInt();

        System.out.print("Kas liha on külm? (true/false): ");
        boolean külmutatud = scanner.nextBoolean();

        double küpsetusAeg = 0;

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
                System.out.println("Invalid meat type.");
                return;
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

        if (temperatuur < 185) {
            küpsetusAeg += 8;
        } else if (temperatuur > 215) {
            küpsetusAeg -= 8;
        }

        if (külmutatud) {
            küpsetusAeg += 5;
        }

        if (weight > 500) {
            küpsetusAeg = küpsetusAeg * (1 + weight / 1000);
        }

        System.out.println("Küpsetusaeg: " + küpsetusAeg + " minutit.");
    }
}