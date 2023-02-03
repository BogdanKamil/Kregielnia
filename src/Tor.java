import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Tor {
    public static int numerToru;
    private String stan;

    public  Tor(int numerToru) {
        this.numerToru = numerToru;
        this.stan = stan;
    }

    public Tor(int numerToru, int numerKlienta, int numerPracownika) {
    }


    public int getNumerToru() {
        return numerToru;
    }

    public void setNumerToru(int numerToru) {
        this.numerToru = numerToru;
    }

   public String getStan() {
        return stan;
    }

    public void setStan(String stan) {this.stan = stan;}
    public static void tory() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from tory");
            System.out.println("Lista torów: ");
            while (resultSet.next()) {
                System.out.println("Numer Toru: " + resultSet.getInt(1) + "  Stan: " + resultSet.getString(2));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void dodajTor(){
        //Wyświetlenie wszystkich torów i ich stanu
        Tor.tory();
        //Dodanie toru
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj numer toru do dodania: ");
        int numertoru = scan.nextInt();
        if (numertoru < 0) {
            System.out.println("Numer toru nie może być mniejszy od 0");
        } else {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM tory WHERE numerToru = " + numertoru + ";");

                if (result.next()) {
                    System.out.println("\nTor o numerze " + numertoru + " już istnieje\n");
                } else {
                    System.out.print("Podaj stan(Sprawny,Niesprawny): ");
                    String stan = scan.next();
                    statement.executeUpdate("INSERT INTO tory VALUES(" + numertoru + ",'" + stan + "')");
                    Tor.tory();
                    System.out.println("Dodano tor o numerze: " + numertoru);
                }
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
              }
    }
    public static void usunTor(){
        Tor.tory();
        //Usuwanie torów
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj numer toru do usuniecia:");
        int numertoru = scan.nextInt();
        if (numertoru < 0) {
            System.out.println("Numer toru nie może być mniejszy od 0");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Tory WHERE numerToru=" + numertoru);
                if(resultSet.next()){
                    statement.executeUpdate("DELETE FROM Tory WHERE numerToru=" + numertoru);
                    System.out.println("\nUsunięto tor o numerze: " + numertoru);
                }else{
                    System.out.println("\nNie ma toru o numerze: " + numertoru + " w bazie danych.\n");
                }
                System.out.println();
                Tor.tory();
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
