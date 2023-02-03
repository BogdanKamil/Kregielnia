import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Zarzadzanie_Cenami{
    int cena;

    public int getCena() {return cena;}

    public void setCena(int cena) {this.cena = cena;}
    public static void ceny() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from ceny");
            System.out.println("Lista torów i ich cen: ");
            while (resultSet.next()) {
                System.out.println("Numer Toru: " + resultSet.getInt(1) + "  Cena: " + resultSet.getString(2));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void dodajCene() {
        Scanner scan = new Scanner(System.in);
        Zarzadzanie_Cenami.ceny();
        System.out.print("Podaj numer toru :");
        int numertoru = scan.nextInt();
        if (numertoru < 0) {
            System.out.println("Numer toru nie może być mniejszy od 0");
        } else {
            System.out.print("Podaj cene za tor: ");
            int cena = scan.nextInt();
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM ceny WHERE numerToru=" + numertoru);
                if(result.next()) {
                    System.out.println("\nCena dla toru o numerze " + numertoru + " już istnieje\n");
                } else {
                    statement.executeUpdate("INSERT INTO ceny VALUES(" + numertoru + ",'" + cena + "')");
                    Zarzadzanie_Cenami.ceny();
                }
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void usunCene(){
        Zarzadzanie_Cenami.ceny();
        Scanner scan = new Scanner(System.in);
        System.out.print("Podaj numer do usuniecia ceny:");
        int numertoru = scan.nextInt();
        if (numertoru < 0) {
            System.out.println("Numer toru nie może być mniejszy od 0");
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM ceny WHERE numerToru=" + numertoru);
                if (!resultSet.next()) {
                    System.out.println("\nNie znaleziono ceny dla wybranego numeru toru.\n");
                    connection.close();
                    return;
                }
                statement.executeUpdate("DELETE FROM ceny WHERE numerToru=" + numertoru);
                Zarzadzanie_Cenami.ceny();
                connection.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    }






