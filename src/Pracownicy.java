import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Pracownicy {
    private int numerPracownika;
    private String imie;
    private String nazwisko;


    public int getNumerPracownika() {return numerPracownika;}
    public void setNumerPracownika(int numerPracownika) {this.numerPracownika = numerPracownika;}
    public String getImie() {return imie;}
    public void setImie(String imie) {this.imie = imie;}
    public String getNazwisko() {return nazwisko;}
    public void setNazwisko(String nazwisko) {this.nazwisko = nazwisko;}
    public static void pracownicy() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from pracownicy");
            System.out.println("Lista pracownikow: ");
            while (resultSet.next()) {
                System.out.println("Numer Pracownika: " + resultSet.getInt(1) + ", Imie: " + resultSet.getString(2) + ", Nazwisko: " + resultSet.getString(3));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void dodajPracownika() {
        Pracownicy.pracownicy();
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj numer pracownika do dodania: ");
        int numerpracownika = scan.nextInt();
        if (numerpracownika < 0) {
            System.out.println("Numer pracownika nie może być mniejszy od 0");
        } else {
            System.out.print("Podaj imie: ");
            String imie = scan.next();
            System.out.print("Podaj nazwisko: ");
            String nazwisko = scan.next();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM pracownicy WHERE numerPracownika = " + numerpracownika + ";");
                if (result.next()) {
                    System.out.println("\nPracownik o numerze " + numerpracownika + " już istnieje.\n");
                } else {
                    statement.executeUpdate("INSERT INTO pracownicy VALUES(" + numerpracownika + ",'" + imie + "','" + nazwisko + "');");
                    Pracownicy.pracownicy();
                    System.out.println("Dodano pracownika o numerze: " + numerpracownika);
                }
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }



    public static void usunPracownika(){
//Wyświetlenie numeru pracownika, imie i nazwisko
        Pracownicy.pracownicy();
//usuwanie pracownika
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj numer pracownika do usuniecia: ");
        int numerpracownika = scan.nextInt();
        if (numerpracownika < 0) {
            System.out.println("Numer pracownika nie może być mniejszy od 0");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM pracownicy WHERE numerPracownika=" + numerpracownika);
                if(resultSet.next()){
                    statement.executeUpdate("DELETE FROM pracownicy WHERE numerPracownika=" + numerpracownika);
                    System.out.println("Usunięto pracownika o numerze: " + numerpracownika);
                }else{
                    System.out.println("\nNie ma pracownika o numerze: " + numerpracownika + " w bazie danych.\n");
                }
                Pracownicy.pracownicy();
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
