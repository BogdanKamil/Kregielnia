import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Statement;


public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Zarzadzanie torami");
        System.out.println("   1. Dodaj tor");
        System.out.println("   2. Usuń tor");
        System.out.println("   3. Wyświetl listę torów");
        System.out.println("2. Zarzadzanie rezerwacjami");
        System.out.println("    1. Dodaj rezerwacje");
        System.out.println("    2. Usuń rezerwacje");
        System.out.println("    3. Wyświetl listę rezerwacji");
        System.out.println("3. Zarzadzanie pracownikami");
        System.out.println("    1. Dodaj pracownika");
        System.out.println("    2. Usuń pracownika");
        System.out.println("    3. Wyświetl listę pracownikow");
        System.out.println("4. Zarzadzanie Klientami");
        System.out.println("    1. Dodaj klienta");
        System.out.println("    2. Usuń klienta");
        System.out.println("    3. Wyświetl listę klientow");
        System.out.println("5. Zarzadzanie cenami");
        System.out.println("    1. Dodaj cene");
        System.out.println("    2. Usuń cene");
        System.out.println("    3. Wyświetl listę cen za dany tor");
        System.out.println("6. Kalkulator ceny za rezerwację");
        System.out.println("7. Wyjście");

        int option = scanner.nextInt();
        switch (option) {
            case 1:
                System.out.println("1. Zarzadzanie torami");
                System.out.println("   1. Dodaj tor");
                System.out.println("   2. Usuń tor");
                System.out.println("   3. Wyświetl listę torów");
                int option1 = scanner.nextInt();
                switch (option1) {
                    //dodawanie toru
                    case 1:
                        Tor.dodajTor();
                        break;
                    case 2://Usuwanie toru
                        Tor.usunTor();
                        break;
                    case 3://Wyświetlanie całej tablicy tory
                        Tor.tory();
                        break;
                    default:
                        System.out.println("Niepoprawna opcja! Spróbuj ponownie.");
                }
                break;
            case 2:
                System.out.println("2. Zarzadzanie rezerwacjami");
                System.out.println("    1. Dodaj rezerwacje");
                System.out.println("    2. Usuń rezerwacje");
                System.out.println("    3. Wyświetl listę rezerwacji");
                int option3 = scanner.nextInt();
                switch (option3) {
                    case 1:
                        //Wyświetlenie wszystkich rezerwacji
                        Rezerwacje.rezerwacje();
                        System.out.println();
                        //Wyświetlenie torów i ich stanów
                        Tor.tory();
                        System.out.println();
                        //Wyświetlenie ceny za tor
                        Zarzadzanie_Cenami.ceny();
                        System.out.println();
                        //Dodanie rezerwacji
                        Scanner scan = new Scanner(System.in);
                        System.out.print("Podaj numer rezerwacji: ");
                        int numerRezerwacji = scan.nextInt();
                        System.out.print("Podaj numer toru: ");
                        int numerToru = scan.nextInt();
                        System.out.print("Podaj numer pracownika: ");
                        int numerPracownika = scan.nextInt();
                        System.out.print("Podaj numer klienta: ");
                        int numerKlienta = scan.nextInt();
                        System.out.print("Podaj czas gry: ");
                        int czas = scan.nextInt();
                        System.out.println();
                        try {
                            Class.forName("com.mysql.cj.jdbc.Driver");
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/kregielnia", "root", "");
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT stan FROM tory WHERE numerToru = " + numerToru + ";");

                            if (resultSet.next() && resultSet.getString("stan").equals("Niesprawny")) {
                                System.out.println("\nTor nie sprawny, nie można zarezerwować!!! \n");
                            } else {
                                resultSet = statement.executeQuery("SELECT numerRezerwacji FROM rezerwacje WHERE numerRezerwacji = " + numerRezerwacji + ";");
                                if (resultSet.next()) {
                                    System.out.println("\nRezerwacja z numerem " + numerRezerwacji + " już istnieje, nie można dodać!!! \n");
                                } else {
                                    statement.executeUpdate("INSERT INTO rezerwacje VALUES(" + numerToru + "," + numerRezerwacji + "," + numerPracownika + "," + numerKlienta + "," + czas + ");");
                                    System.out.println();
                                    Rezerwacje.rezerwacje();
                                }
                            }
                            connection.close();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        break;

                    case 2://Usuwanie rezerwacji
                        Rezerwacje.usunRezerwacje();
                        break;

                    case 3://Wyswietlanie wszystkich rezerwacji i przypisanych do nich encji
                        Rezerwacje.rezerwacje();
                        break;
                    default:
                        System.out.println("Niepoprawna opcja! Spróbuj ponownie.");
                }
                break;
            case 3:
                System.out.println("Wybrales Zarzadzanie pracownikami");
                System.out.println("    1. Dodaj pracownika");
                System.out.println("    2. Usuń pracownika");
                System.out.println("    3. Wyświetl listę pracownikow");
                int option4 = scanner.nextInt();
                switch (option4) {
                    case 1:
                        Pracownicy.dodajPracownika();
                        break;
                    case 2:
                        Pracownicy.usunPracownika();
                        break;

                    case 3:
                        Pracownicy.pracownicy();
                        break;
                    default:
                        System.out.println("Niepoprawna opcja! Spróbuj ponownie.");
                }
                break;

            case 4:
                System.out.println("Wybrałeś Zarzadzanie Klientami");
                System.out.println("    1. Dodaj klienta");
                System.out.println("    2. Usuń klienta");
                System.out.println("    3. Wyświetl listę klientow");
                int option5 = scanner.nextInt();
                switch (option5) {
                    case 1:
                        Klienci.dodajKlienta();
                        break;
                    case 2:
                        Klienci.usunKlienta();
                        break;
                    case 3:
                        Klienci.klienci();
                        break;
                    default:
                        System.out.println("Niepoprawna opcja! Spróbuj ponownie.");
                }
                break;
            case 5:
                System.out.println("Wybrales Zarzadzanie cenami :");
                System.out.println("    1. Dodaj cene");
                System.out.println("    2. Usuń cene");
                System.out.println("    3. Wyświetl listę cen");
                int option6 = scanner.nextInt();
                switch (option6) {
                    case 1:
                        Zarzadzanie_Cenami.dodajCene();
                        break;
                    case 2:
                        Zarzadzanie_Cenami.usunCene();
                        break;
                    case 3:
                        Zarzadzanie_Cenami.ceny();
                        break;
                    default:
                        System.out.println("Niepoprawna opcja! Spróbuj ponownie.");
                }
                break;

            case 6:
                Zarzadzanie_Cenami.ceny();
                System.out.println();
                Rezerwacje.rezerwacje();
                System.out.print("\nPodaj numer rezerwacji aby obliczyc kwote za grę: \n");
                Rezerwacje.obliczKoszt();
                break;
            case 7:
                System.out.println("Dziekuje za skorzystanie z aplikacji :)");
                break;
            default:
                System.out.println("Niepoprawna opcja! Spróbuj ponownie.");
        }
    }


}