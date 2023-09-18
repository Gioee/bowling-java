package com.unimi.bowling.util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.Thread.sleep;

public class Strumenti {
    private boolean strike, spare, mezzostrike;

    public void aspettaInvio(){
        try{
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pulisciConsole(){
        /*try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Errore nell'esecuzione del processo!");
        }*/

        //SOLO PER INTELLIJ
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_1);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_1);
        } catch (AWTException ex) {
            ex.printStackTrace(System.err);
        }

        try {
            sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //SOLO PER INTELLIJ
    }

    public void stampaErrore(String errore){
        System.out.println(
                "                                  \n" +
                "                                  \n" +
                "___ ___ ___ ___ ___ ___ ___ ___ _ \n" +
                "|                               | \n" +
                "|  ___  __   __   __   __   ___ | \n" +
                "| |__  |__) |__) /  \\ |__) |__  | \n" +
                "| |___ |  \\ |  \\ \\__/ |  \\ |___ | \n" +
                "|                               | \n" +
                "|                               |       \n" +
                "|                               |       \n" +
                "| ___ ___ ___ ___ ____ ____ ___ |       \n" +
                "                                \n" +
                "              | |                      \n" +
                "              | |                      \n" +
                "              | |                      \n" +
                "             _| |_                     \n" +
                "            \\     /                  \n" +
                "             \\   /                   \n" +
                "              \\ /                    \n" +
                "                                      \n" +
                "" + errore + "                 \n " +
                "                                      \n"

        );
    }

    public PrintWriter scrittoreFile(String file, boolean append){
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(file, append));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pw;
    }

    public BufferedReader lettoreFile(String file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return br;
    }

    public void separatoreBasico(){
        System.out.println("\n--------------------------------\n");
    }

    public void separatoreAvanzato(){System.out.println("\n================================\n");}

    public String integerToRomano(int n){

        int[] valori = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        String[] lettereRomane = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};

        StringBuilder romano = new StringBuilder();

        for(int i=0;i<valori.length;i++) {
            while(n >= valori[i]) {
                n -= valori[i];
                romano.append(lettereRomane[i]);
            }
        }

        return romano.toString();
    }

    public int interpreterPunteggio(String p){
        if(resetMezzoStrike())
            return interpreterPunteggio(p)*2;

        if(resetStrike()){
            int loc = interpreterPunteggio(p)*2;
            this.mezzostrike=true;
            return loc;
        }

        if(resetSpare())
            return interpreterPunteggio(p)*2;

        if(isNumeric(p))
            return Integer.parseInt(p);

        if(p.equals("X")){
            this.strike=true;
            return 10;
        }

        if(p.equals("/")){
            this.spare=true;
            return 10;
        }

        return 0;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int n = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public List<String> getFile(String file){
        List<String> backup = null;

        try {
            backup= Files.readAllLines(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return backup;
    }

    public String nToScrito(int n, boolean femminile){
        String[] testo = {"prim","second","terz","quart","quint","sest","settim","ottav","non","decim"};

        return testo[n] + (femminile? "a":"o");
    }

    public void Strike(){
        this.strike=true;
        System.out.println("\nBRAVO! STRIKE!!!");
        System.out.println("\npremere il tasto invio per continuare");
        aspettaInvio();
    }

    public void Spare(){
        this.spare=true;
        System.out.println("\nBRAVO! SPARE!!!");
        System.out.println("\npremere il tasto invio per continuare");
        aspettaInvio();
    }

    public boolean resetStrike(){
        boolean loc = this.strike;
        this.strike=false;
        return loc;
    }

    public boolean resetSpare(){
        boolean loc = this.spare;
        this.spare=false;
        return loc;
    }

    public boolean strikeOrSpare(){
        if(this.strike || this.spare)
            return true;
        return false;
    }

    private boolean resetMezzoStrike(){
        boolean loc = this.mezzostrike;
        this.mezzostrike=false;
        return loc;
    }

    public void stampaVincitore(String vincitore){
        System.out.print(
                "\n\n        .-..-\"\"``\"\"-..-.\n" +
                "        |(`\\`'----'`/`)|\n" +
                "         \\\\ ;:.    ; //\n" +
                "          \\\\|%.    |//\n" +
                "           )|%:    |(\n" +
                "         ((,|%.    |,))\n" +
                "          '-\\::.   /-'\n" +
                "             '::..'\n" +
                "               }{\n" +
                "              {__}\n" +
                "             /    \\\n" +
                "            |`----'|\n" +
                "            | [#1] |\n" +
                "            '.____.'\n\n" +
                "        IL VINCITORE È " + vincitore


        );
    }
}
