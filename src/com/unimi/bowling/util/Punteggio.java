package com.unimi.bowling.util;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Punteggio {
    FileDB fdb;
    Strumenti s = new Strumenti();
    private int pista = 0;

    public Punteggio(String nomefile, int pista){
        FileDB fdb = new FileDB(nomefile);
        this.fdb = fdb;
        this.pista = pista;
    }

    public void setGiocatori(){
        Scanner in = new Scanner(System.in);
        int n = 0;
        do{
            if(n!=0)
                s.stampaErrore("IL NUMERO DEI GIOCATORI DEVE ESSERE COMPRESO TRA 2 E 4");
            System.out.print("\nIndicare il numero dei giocatori (2-4): ");
            n = in.nextInt();
        }while(n<2||n>4);

        s.separatoreBasico();

        for (int i = 1; i < n+1; i++) {
            String pos = s.nToScrito(i-1, false);
            pos = pos.substring(0,1).toUpperCase()+pos.substring(1);
            System.out.printf(pos + " giocatore: ");
            String nome = in.next();
            fdb.nuovoUtente(i, nome);
        }

        s.separatoreBasico();

    }

    public String stampaPunteggio(int turno, int idGiocatore, boolean vittoria){
        int somma[] = new int[10];
        int tot[] = new int[fdb.numGiocatori()];
        String romano = s.integerToRomano(pista);
        System.out.print(
                "\n  ____________________________________________________________________________________________\n" +
                "|\t PISTA\t\t|     | |\t\tTURNO: "+turno+"\t\t|\t\tORA: "+java.time.LocalTime.now().truncatedTo(ChronoUnit.MINUTES)+"\t\t|     |\t\t\t\t |\n"+
                "|\t NUM "+pista+"\t\t|-----|-'-----------------------'-----------------------'-----|--------------|\n|\t  "+(romano.length()<2?" "+romano:romano)+"\t\t");

        for (int i = 1; i < 11; i++) {
            if(i==turno){
                System.out.print("| [" + i + (i!=10?"] ":"]  |\n"));
            } else{
                System.out.print("|  "+i + (i!=10?"  ":"   |\t   TOTALE    |\n"));
            }
        }

        //System.out.println("\t   TOTALE    |"); fix dopo 10turno

        for (int i = 1; i < fdb.numGiocatori()+1; i++) {
            int[][] sommaparz = new int[fdb.numGiocatori()][10];
            System.out.print(
                    "|---------------|-----|-----|-----|-----|-----|-----|-----|-----|-----|-------|--------------|\n|\t\t\t\t|");

            for (int j = 0; j < 10; j++)
                somma[j]=0;

            for (int j = 0; j < 10; j++) {
                String[] punteggio = fdb.punteggioTurnoGiocatore(j+1, i);
                somma[j]=s.interpreterPunteggio(punteggio[0])+s.interpreterPunteggio(punteggio[1]);

                if(s.strikeOrSpare())
                    somma[j] = 10;

                System.out.print((punteggio[0].length()==2?punteggio[0]:" "+ punteggio[0]) + "|" + (punteggio[1].length()==2?punteggio[1]:" "+punteggio[1]) + (j==9?"  |\t\t\t\t |\n":"|"));
                sommaparz[i-1][j] = Arrays.stream(somma).sum();
            }

            tot[i-1] = Arrays.stream(somma).sum();

            System.out.print("|\t"+(i==idGiocatore?"["+(fdb.idToGiocatore(i).length()>5?fdb.idToGiocatore(i).substring(0, 4) + ".":fdb.idToGiocatore(i))+"]":(fdb.idToGiocatore(i).length()>5?fdb.idToGiocatore(i).substring(0, 5) + ".":fdb.idToGiocatore(i)))+"\t\t|-----|-----|-----|-----|-----|-----|-----|-----|-----|-------|\t\t  " + tot[i-1] + "  \t |\n|\t\t\t\t|");

            for (int j = 0; j < 9; j++)
                System.out.print((Integer.toString(sommaparz[i-1][j]).length()>1?" "+sommaparz[i-1][j]:"  "+sommaparz[i-1][j]) + "  |");

            System.out.print((Integer.toString(sommaparz[i-1][9]).length()>1?"  "+sommaparz[i-1][9]:"   "+sommaparz[i-1][9]) + (Integer.toString(sommaparz[i-1][9]).length()==3?"":" ") + "  |\t\t\t\t |\n");

            System.out.print((i==fdb.numGiocatori()?"'---------------'-----'-----'-----'-----'-----'-----'-----'-----'-----'-------'--------------'\n":""));
        }

        if(vittoria){
            int max=Arrays.stream(tot).max().getAsInt();
            if(IntStream.range(0,tot.length).filter(i -> max == tot[i]).count()>1)
                return "PAREGGIO";
            return fdb.idToGiocatore((IntStream.range(0,tot.length).filter(i -> max == tot[i]).findAny().orElse(-1))+1);
        }
        return null;
    }

    public int getNumGiocatori(){
        return fdb.numGiocatori();
    }

    public void setPunteggio(int idGiocatore, int turno, int lancio, boolean spare, boolean strike){
        fdb.setPunteggio(idGiocatore, turno, lancio, spare, strike);
    }
}
