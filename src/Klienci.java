import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Klienci {
    private  int numerKlienta;
    private String imie;
    private String nazwisko;



    public int getNrKlienta() {return numerKlienta;}

    public void setNrKlienta(int nrKlienta) {this.numerKlienta = nrKlienta;}

    public String getImie() {return imie;}

    public void setImie(String imie) {this.imie = imie;}

    public String getNazwisko() {return nazwisko;}

    public void setNazwisko(String nazwisko) {this.nazwisko = nazwisko;}
    public static void klienci() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from klienci");
            System.out.println("Lista klientow: ");
            while (resultSet.next()) {
                System.out.println("Numer klienta: " + resultSet.getInt(1) + ", Imie: " + resultSet.getString(2) + ", Nazwisko: " + resultSet.getString(3));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void dodajKlienta(){
        //Wyświetlenie numeru klienta, imie i nazwisko
        Klienci.klienci();
        //Dodanie klienta
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj numer klienta do dodania: ");
        int numerklienta = scan.nextInt();
        if (numerklienta < 0) {
            System.out.println("Numer klienta nie może być mniejszy od 0");
        } else {
            System.out.print("Podaj imie: ");
            String imie = scan.next();
            System.out.print("Podaj nazwisko: ");
            String nazwisko = scan.next();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM klienci WHERE numerKlienta = " + numerklienta);
                if (resultSet.next()) {
                    System.out.println("\nKlient o numerze " + numerklienta + " już istnieje\n");
                    connection.close();
                    return;
                }
                statement.executeUpdate("INSERT INTO klienci VALUES(" + numerklienta + ",'" + imie + "','" + nazwisko + "');");
                Klienci.klienci();
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Dodano klienta o numerze: " + numerklienta);
        }
    }
    public static void usunKlienta(){
        //Wyświetlenie numeru pracownika, imie i nazwisko
        Klienci.klienci();
        //usuwanie pracownika
        Scanner scan=new Scanner(System.in);
        System.out.print("Podaj numer klienta do usuniecia: ");
        int numerklienta = scan.nextInt();
        if (numerklienta < 0) {
            System.out.println("Numer klienta nie może być mniejszy od 0");
        } else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM Klienci WHERE numerKlienta=" + numerklienta);
                    if(resultSet.next()){
                        statement.executeUpdate("DELETE FROM klienci WHERE numerKlienta=" + numerklienta);
                        System.out.println("Usunięto klienta o numerze: " + numerklienta);
                    }else{
                        System.out.println("\nNie ma klienta o numerze: " + numerklienta + " w bazie danych.\n");
                    }
                    Klienci.klienci();
                    connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }

    }
}}
