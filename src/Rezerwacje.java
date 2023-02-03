import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Rezerwacje extends Tor {
    private static int numerRezerwacji;
    private String imie;
    private static int czas;


    public Rezerwacje(int numerToru, int numerRezerwacji, int numerPracownika, int numerKlienta, int czas) {
        super(numerToru, numerKlienta, numerPracownika);
    }

    public int getNumerRezerwacji() {
        return numerRezerwacji;
    }

    public void setNumerRezerwacji(int numerRezerwacji) {
        this.numerRezerwacji = numerRezerwacji;
    }

    public int getCzas() {
        return czas;
    }

    public void setCzas(int czas) {
        this.czas = czas;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public static void rezerwacje() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from rezerwacje");
            System.out.println("Lista rezerwacji: ");
            while (resultSet.next()) {
                System.out.println("Numer rezerwacji: " + resultSet.getString(2) + " , Numer toru: " + resultSet.getInt(1) + " , Numer pracownika: " + resultSet.getString(3) + " , Numer klienta: " + resultSet.getString(4) + " , Czas gry: " + resultSet.getString(5));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void usunRezerwacje(){
        Rezerwacje.rezerwacje();
//Usuwanie rezerwacji
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj numer rezerwacji do usuniecia:");
        numerRezerwacji = scan.nextInt();
        if (numerRezerwacji < 0) {
            System.out.println("Numer toru nie może być mniejszy od 0");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM rezerwacje WHERE numerRezerwacji=" + numerRezerwacji);
                if (!result.next()) {
                    System.out.println("\nNie znaleziono rezerwacji o numerze: " + numerRezerwacji+"\n");
                } else {
                    statement.executeUpdate("DELETE FROM rezerwacje WHERE numerRezerwacji=" + numerRezerwacji);
                    Rezerwacje.rezerwacje();
                }
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public static void obliczKoszt() {
        int cena = 0;
        int numerToru = 0;
        int czas = 0;
        try {
            Scanner scan= new Scanner(System.in);
            numerRezerwacji= scan.nextInt();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
            Statement statement1 = connection.createStatement();
            ResultSet result = statement1.executeQuery("SELECT numerToru FROM rezerwacje WHERE numerRezerwacji=" + numerRezerwacji);
            if (result.next()) {
                numerToru = result.getInt(1);
            }
            Statement statement2 = connection.createStatement();
            result = statement2.executeQuery("SELECT cena FROM ceny WHERE numerToru=" + numerToru);
            if (result.next()) {
                cena = result.getInt(1);
            }
            Statement statement3 = connection.createStatement();
            result = statement3.executeQuery("SELECT czas FROM rezerwacje WHERE numerRezerwacji=" + numerRezerwacji);
            if (result.next()) {
                czas = result.getInt(1);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        int koszt = cena * czas;
        if (koszt == 0) {
            System.out.println("Nie znaleziono informacji o rezerwacji o numerze " + numerRezerwacji + " lub nie zdefiniowano jeszcze ceny za wybrany tor.");
        } else {
            System.out.println("Koszt rezerwacji o numerze " + numerRezerwacji + " wynosi: " + koszt + " zł");
        }
    }

}