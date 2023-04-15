import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gracz krupier = new Gracz(0,1000);
        System.out.println("Karty krupiera: ");
        Karta karta = new Karta("sample", 0);
        ArrayList<Karta> kartyKrupiera = new ArrayList<>();
        Gracz gracz = new Gracz(0,1000);
        ArrayList<Karta> kartyGracza = new ArrayList<>();
        boolean gameEnd = true;
        boolean roundEnd = true;
        System.out.println("rozpoczynam grę!");
        game:
        while (gameEnd) {
            System.out.println("Pieniądze gracza: " + gracz.getMoney() + "PLN");
            System.out.println("Pieniądze krupiera: " + krupier.getMoney() + "PLN");
            round:
            while (roundEnd) {
                System.out.println("Ile chcesz postawić w tej rundzie?");
                Scanner scannerMoney = new Scanner(System.in);
                int betMoney = scannerMoney.nextInt();
                System.out.println("Postawiłeś " + betMoney + " PLN");
                //losowanie pierwszych dwóch kart dla krupiera
                for (int i = 0; i < 2; i++) {
                    kartyKrupiera.add(new Karta(karta.losowaniekart(krupier.getWynik()), karta.getRealValue()));
                    krupier.setWynik(krupier.getWynik() + karta.getRealValue());
                }
                for (Karta kartalist : kartyKrupiera) {
                    System.out.println(kartalist);
                }
                System.out.println("wynik krupiera: " + krupier.getWynik());


                //losowanie pierwszych dwóch kart dla gracza
                for (int i = 0; i < 2; i++) {
                    kartyGracza.add(new Karta(karta.losowaniekart(gracz.getWynik()), karta.getRealValue()));
                    gracz.setWynik(gracz.getWynik() + karta.getRealValue());
                }
                for (Karta kartalist : kartyGracza) {
                    System.out.println(kartalist);
                }
                System.out.println("Twój wynik: " + gracz.getWynik());


                //zmienna w celu wyjścia z pętli while
                int accept = 0;


                //dobieranie karty przez użytkownika
                while (accept == 0) {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("co chcesz zrobić? dobierz kartę = 1, zakończ = 2");
                    int decyzja = scanner.nextInt();
                    if (decyzja == 1) {
                        kartyGracza.add(new Karta(karta.losowaniekart(gracz.getWynik()), karta.getRealValue()));
                        gracz.setWynik(gracz.getWynik() + karta.getRealValue());
                        if (gracz.getWynik() > 21) {
                            System.out.println("przegrałeś! Twój wynik to: " + gracz.getWynik());
                            gracz.setMoney(gracz.getMoney()-betMoney);
                            krupier.setMoney(krupier.getMoney()+betMoney);
                            break round;
                        }
                        for (Karta kartalist : kartyGracza) {
                            System.out.println(kartalist);
                        }
                        System.out.println("Twój wynik wynosi: " + gracz.getWynik());

                    } else {
                        accept++;
                    }
                }

                //po turze użytkownika krupier losuje (lub nie) swoje karty
                System.out.println("teraz kolej krupiera!");
                while (!krupier.isended(krupier.getWynik())) {
                    kartyKrupiera.add(new Karta(karta.losowaniekart(krupier.getWynik()), karta.getRealValue()));
                    krupier.setWynik(krupier.getWynik() + karta.getRealValue());
                }
                System.out.println("krupier dobrał " + (kartyKrupiera.size() - 2));
                for (Karta kartalist : kartyKrupiera) {
                    System.out.println(kartalist);
                }
                System.out.println("wynik krupiera: " + krupier.getWynik());
                if (krupier.getWynik() > 21) {
                    System.out.println("Krupier wylosował za wysoką wartość. Wygrałeś!");
                    gracz.setMoney(gracz.getMoney()+betMoney);
                    krupier.setMoney(krupier.getMoney()-betMoney);
                    break round;
                }


                //ostateczny wynik
                int wynik = krupier.getWynik() - gracz.getWynik();
                if (wynik > 0) {
                    System.out.println("krupier wygrał o " + wynik + " punkty!");
                    gracz.setMoney(gracz.getMoney()-betMoney);
                    krupier.setMoney(krupier.getMoney()+betMoney);
                } else if (wynik == 0) {
                    System.out.println("remis!");
                } else {
                    System.out.println("wygrałeś o " + (wynik * (-1)) + " punkty!");
                    gracz.setMoney(gracz.getMoney()+betMoney);
                    krupier.setMoney(krupier.getMoney()-betMoney);
                }
                System.out.println("Koniec rundy.");
                System.out.println("Twój kapitał: " + gracz.getMoney() + "PLN");
                System.out.println("Kapitał krupiera: " + krupier.getMoney() + "PLN");

                roundEnd = false;
            }
                System.out.println("Czy chcesz kontynuować? 1 - tak, 2 - nie");
                Scanner scanner1 = new Scanner(System.in);
                int choice = scanner1.nextInt();
                if (choice == 1) {
                    gracz.setWynik(0);
                    krupier.setWynik(0);
                    kartyKrupiera.clear();
                    kartyGracza.clear();
                    roundEnd = true;
                } else if (choice == 2) {
                    break game;
                } else {
                    System.out.println("niepoprawny wybór. Zamykam grę.");
                    break game;
                }

        }
        System.out.println("koniec gry.");
    }
}
class Karta{
    private String value;

    public String getValue() {
        return value;
    }

    private int realValue;
    //value - zmienna z wartością na karcie, realValue zmienna z wartością punktową karty w int.
    public Karta(String value, int realValue) {
        this.value = value;
        this.realValue = realValue;
    }

    @Override
    public String toString() {
        return"///////\n/     /\n/  "+value+"  /\n/     /\n///////";
    }


    public int getRealValue(){
        return realValue;
    }

    public void setRealValue(int realValue) {
        this.realValue = realValue;
    }
    public String losowaniekart(int wynik){
        String card = "";
        int randomNumber = 2 + (int)(Math.random() * ((14 - 2) + 1));
        if (randomNumber >=2 && randomNumber<=10){
            setRealValue(randomNumber);
            card = Integer.toString(randomNumber);
        } else if (randomNumber == 11) {
            card = "A";
            if(wynik <=11){
                setRealValue(10);
            } else {
                setRealValue(1);
            }
        } else if (randomNumber == 12){
            setRealValue(10);
            card = "D";
        } else if (randomNumber == 13){
            setRealValue(10);
            card = "K";
        } else if(randomNumber == 14){
            setRealValue(10);
            card = "W";
        }
        return card;

    }
}
class Gracz{
    private int wynik;
    private int money;

    public Gracz(int wynik, int money) {
        this.wynik = wynik;
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getWynik() {
        return wynik;
    }

    public void setWynik(int wynik) {
        this.wynik = wynik;
    }
    public boolean isended(int wynikmain){
        boolean toEnd;
        if (wynikmain <16){
            toEnd = false;
        }
        else{
            toEnd = true;
        }
        return toEnd;
    }

}
