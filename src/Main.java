import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gracz krupier = new Gracz(0);
        System.out.println("Karty krupiera: ");
        Karta karta = new Karta("sample",0);
        ArrayList<Karta> kartyKrupiera = new ArrayList<>();

        //losowanie pierwszych dwóch kart dla krupiera
        for (int i = 0; i < 2; i++) {
            kartyKrupiera.add(new Karta(karta.losowaniekart(krupier.getWynik()), karta.getRealValue()));
            krupier.setWynik(krupier.getWynik()+ karta.getRealValue());
        }
        for (Karta kartalist:kartyKrupiera) {
            System.out.println(kartalist);
        }
        System.out.println("wynik krupiera: " + krupier.getWynik());



        Gracz gracz = new Gracz(0);
        ArrayList<Karta> kartyGracza = new ArrayList<>();


        //losowanie pierwszych dwóch kart dla gracza
        for (int i = 0; i < 2; i++) {
            kartyGracza.add(new Karta(karta.losowaniekart(gracz.getWynik()), karta.getRealValue()));
            gracz.setWynik(gracz.getWynik()+ karta.getRealValue());
        }
        for (Karta kartalist:kartyGracza) {
            System.out.println(kartalist);
        }
        System.out.println("Twój wynik: " + gracz.wynik);


        //zmienna w celu wyjścia z pętli while
        int accept = 0;


        //dobieranie karty przez użytkownika
        while (accept == 0){
            Scanner scanner = new Scanner(System.in);
            System.out.println("co chcesz zrobić? dobierz kartę = 1, zakończ = 2");
            int decyzja = scanner.nextInt();
            if(decyzja == 1){
                kartyGracza.add(new Karta(karta.losowaniekart(gracz.getWynik()), karta.getRealValue()));
                gracz.setWynik(gracz.getWynik() + karta.getRealValue());
                if (gracz.getWynik() > 21){
                    System.out.println("przegrałeś! Twój wynik to: " + gracz.getWynik());
                    System.exit(0);
                }
                for (Karta kartalist:kartyGracza) {
                    System.out.println(kartalist);
                }
                System.out.println("Twój wynik wynosi: " + gracz.getWynik());

            } else {
                accept++;
            }
        }

        //po turze użytkownika krupier losuje (lub nie) swoje karty
        System.out.println("teraz kolej krupiera!");
        while(!krupier.isended(krupier.getWynik())){
            kartyKrupiera.add(new Karta(karta.losowaniekart(krupier.getWynik()),karta.getRealValue()));
            krupier.setWynik(krupier.getWynik()+ karta.getRealValue());
        }
        System.out.println("krupier dobrał " + (kartyKrupiera.size()-2));
        for (Karta kartalist:kartyKrupiera) {
            System.out.println(kartalist);
        }
        System.out.println("wynik krupiera: " + krupier.getWynik());
        if (krupier.getWynik() > 22){
            System.out.println("Krupier wylosował za wysoką wartość. Wygrałeś!");
            System.exit(0);
        }


        //ostateczny wynik
        int wynik = krupier.getWynik() - gracz.getWynik();
        if(wynik > 0){
            System.out.println("krupier wygrał o " + wynik + " punkty!");
        } else if(wynik == 0){
            System.out.println("remis!");
        } else {
            System.out.println("wygrałeś o " + (wynik*(-1)) + " punkty!");
        }
    }
}
class Karta{
    String value;

    public String getValue() {
        return value;
    }

    int realValue;
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
    int wynik;

    public Gracz(int wynik) {
        this.wynik = wynik;
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
