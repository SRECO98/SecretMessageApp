package com.example.secretmessage;

import android.util.Log;

import java.util.Random;

public class TranslatorClass {

    private int brojacZaMillis = 0, duzinaMillisa = 0;
    Random rand = new Random();

    private String hideYourMessage(String message, long millis){

        brojacZaMillis = 0; //vraca na nulu u slucaju da prevodi vise tekstova odjednom, a nije napustio aplikaciju
        String millis1 = String.valueOf(millis); // pretvaranje u string
        duzinaMillisa = millis1.length(); //duzina Millisa
        char[] nizOdMillis = millis1.toCharArray();    //pravljenje niza od karaktera u trenutnom vremenu
        char[] nizSlovaIzPoruke = message.toCharArray(); //pravljenje niza od karaktera u poruci

        char[] kopijaNizaOdMillis = new char[nizOdMillis.length];
        //pravimo kopiju prije mjenjanja
        System.arraycopy(nizOdMillis, 0, kopijaNizaOdMillis, 0, nizOdMillis.length);

        for(int i = 0; i < nizOdMillis.length; i++){  //smanjivanje vrijednosti karaktera iz trenutnog vremena da bi se mogli procitati svi karakteri koje dobijemo u skrivenom kodu
            nizOdMillis[i] -= 48;
        }

        for(int i = 0; i < nizSlovaIzPoruke.length; i++){
            if (brojacZaMillis > nizOdMillis.length - 1) {   //resetuje se na nulu ako u poruci ima vise karaktera nego u tre. vremenu.
                brojacZaMillis = 0;
            }
            nizSlovaIzPoruke[i] = (char) (nizSlovaIzPoruke[i] + nizOdMillis[brojacZaMillis++]);  //kreiranje tajne poruke
        }
        //pravljenje stringa od slova u poruci.
        String poruka = String.valueOf(nizSlovaIzPoruke);
        //dodavanje naseg trenutnog vremena u skrivenu poruku da se moze dekriptovati.
        int pozicija = rand.nextInt(3) + 2;

        poruka = castingDecimalToHex(duzinaMillisa) + poruka; //dodavanje duzine trenutnog vremena na drugo mjesto
        poruka = pozicija + poruka;// dodavanje i random broja pomocu kojeg radimo siftanje brojeva na prvo mjesto

        for(char brojIzMillis : kopijaNizaOdMillis){
                if(pozicija < poruka.length()) {
                    poruka = addChar(poruka, brojIzMillis, pozicija);
                    pozicija ++;
                }
            }
        return poruka;
    }

    //ubacivanje vremenskih cifri u poruku.
    private String addChar(String str, char ch, int position){
        StringBuilder sb = new StringBuilder(str);
        sb.insert(position, ch);
        return new String(sb);
    }

    //Metoda za pokretanje pravljenja tajne poruke, jer je ona private.
    public final String startCreatingSecretMessage(String message, long millis){
        return hideYourMessage(message, millis);
    }

    private String readHiddenMessage(String message){
        brojacZaMillis = 0;
        Log.d("TAG", message);
        int razmakUKodu = Character.getNumericValue(message.charAt(0));  //pretvara iz poruke char karakter u int, jer inace se pretvara po ASCII tabeli
        duzinaMillisa = castFromHexaToDecimal(message.charAt(1)); //pretvara iz heca u decimal karakter iz poruke
        char[] nizSlovaIzPoruke;
        char[]nizTrenutnogVremena = new char[duzinaMillisa];
        for(int u = 0; u < duzinaMillisa; u++){
            if(razmakUKodu < message.length()){
                nizTrenutnogVremena[u] = message.charAt(razmakUKodu); //upisivanje brojeva u nas char niz
                message = removeChar(message, razmakUKodu); // brisanje brojeva iz poruke;
            }
        }
        Log.d("TAG", String.valueOf(nizTrenutnogVremena));
        Log.d("TAG", message);
        message = removeChar(message, 0);
        message = removeChar(message, 0);
        Log.d("TAG", message);
        for (int i = 0; i < nizTrenutnogVremena.length; i++) { //ovo mora jer smo tako raditi tajni kod
            nizTrenutnogVremena[i] -= 48;
        }
        Log.d("TAG7", String.valueOf(nizTrenutnogVremena));
        nizSlovaIzPoruke = message.toCharArray(); // pretvaranje poruke u char niz
        Log.d("TAG9", String.valueOf(nizSlovaIzPoruke));
        for(int i = 0; i < nizSlovaIzPoruke.length; i++){
            if (brojacZaMillis > nizTrenutnogVremena.length - 1) {   //resetuje se na nulu ako u poruci ima vise karaktera nego u tre. vremenu.
                brojacZaMillis = 0;
            }
            nizSlovaIzPoruke[i] = (char) (nizSlovaIzPoruke[i] - nizTrenutnogVremena[brojacZaMillis++]);  //dekriptovanje tajne poruke
        }
        return String.valueOf(nizSlovaIzPoruke);
    }
//brisanje trenutnog vremena iz poruke.
    private String removeChar(String message, int position){
        StringBuilder sb = new StringBuilder(message);
        sb.deleteCharAt(position);
        return sb.toString();
    }
//pocetak citanje secret koda jer je on private
    public final String startReadingSecretMessage(String message){
        return readHiddenMessage(message);
    }

    private String castingDecimalToHex(int decimal){
        int rem;
        StringBuilder hex= new StringBuilder();
        char[] hexchars ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(decimal>0)
        {
            rem=decimal%16;
            hex.insert(0, hexchars[rem]);
            decimal=decimal/16;
        }
        return hex.toString();
    }

    private int castFromHexaToDecimal(char slovo){
        if(slovo == 'D')
            return 13;
        else if(slovo == 'E')
            return 14;
        else
            return 15;
    }
}