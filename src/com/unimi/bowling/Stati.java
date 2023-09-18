package com.unimi.bowling;

import com.unimi.bowling.util.Punteggio;
import com.unimi.bowling.util.Strumenti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;

public class Stati {
    Strumenti s = new Strumenti();

    public void Inizio(){
        System.out.println(
                "                                                                                                               \n" +
                "                                             CANTONI GIOELE PRESENTA                                           \n" +
                "                                                                                                               \n" +
                "██████╗  ██████╗ ██╗    ██╗██╗     ██╗███╗   ██╗ ██████╗     ██╗███╗   ██╗         ██╗ █████╗ ██╗   ██╗ █████╗ \n" +
                "██╔══██╗██╔═══██╗██║    ██║██║     ██║████╗  ██║██╔════╝     ██║████╗  ██║         ██║██╔══██╗██║   ██║██╔══██╗\n" +
                "██████╔╝██║   ██║██║ █╗ ██║██║     ██║██╔██╗ ██║██║  ███╗    ██║██╔██╗ ██║         ██║███████║██║   ██║███████║\n" +
                "██╔══██╗██║   ██║██║███╗██║██║     ██║██║╚██╗██║██║   ██║    ██║██║╚██╗██║    ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║\n" +
                "██████╔╝╚██████╔╝╚███╔███╔╝███████╗██║██║ ╚████║╚██████╔╝    ██║██║ ╚████║    ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║\n" +
                "╚═════╝  ╚═════╝  ╚══╝╚══╝ ╚══════╝╚═╝╚═╝  ╚═══╝ ╚═════╝     ╚═╝╚═╝  ╚═══╝     ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝\n" +
                "                                                                                                               \n" +
                "                                    PROGETTO PROGRAMMAZIONE OOP - UNIMI SSRI                                   \n" +
                "                                                                                                               \n" +
                "                                                                                                               \n" +
                "                                     premere il tasto invio per continuare                                     \n" +
                "                                                                                                               \n"
        );

        s.aspettaInvio();

        s.pulisciConsole();

    }

    public void Menu(){

        int scelta = 0;

        Scanner in = new Scanner(System.in);

        do {

            s.pulisciConsole();

            if(scelta!=1&&scelta!=2&&scelta!=0&&scelta!=3)
                s.stampaErrore("\t\tSCELTA INESISTENTE");

            s.separatoreAvanzato();

            System.out.print(
                    "\t[1]\tNUOVA PARTITA    \n" +
                    "\t[2]\tSTORICO PARTITE  \n" +
                    "\t[3]\tREGOLAMENTO  \n" +
                    "\t[0]\tESCI             \n"
            );

            s.separatoreAvanzato();

            System.out.print("Scelta: ");

            scelta = in.nextInt();

        } while(scelta!=1 && scelta!=2 && scelta!=0 && scelta!=3);
        switch (scelta){
            case 1:
                nuovaPartita();
                break;
            case 2:
                storicoPartite();
                break;
            case 3:
                regolamento();
                break;
            default:
                break;
        }

    }

    private void nuovaPartita(){
        s.pulisciConsole();
        int pista=0;
        Scanner in = new Scanner(System.in);
        do{
            System.out.print("\nNumero della pista (1-8)? ");
            pista = in.nextInt();
        }while(pista<1||pista>8);


        Punteggio p = new Punteggio(new String(java.time.LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + ".txt").replace(":", "."), pista);

        p.setGiocatori();
        s.pulisciConsole();

        int lancio = 0;

        for (int j = 1; j < 11; j++) {
            for (int i = 1; i <= p.getNumGiocatori(); i++) {
                s.pulisciConsole();
                p.stampaPunteggio(j, i, false);
                System.out.println();
                s.separatoreBasico();
                for (int k = 0; k < 2; k++) {
                    //if(!(lancio.equals("X")||lancio.equals("/")))
                        do{
                            if(lancio<0||lancio>10)
                                s.stampaErrore("NUMERO BIRILLI INESISTENTE");
                            if(lancio<10){
                                int lancioprec=0;
                                do{
                                    if(lancioprec+lancio>10){
                                        s.stampaErrore("NUMERO BIRILLI INESISTENTE");
                                    } else{
                                        lancioprec=lancio;
                                    }
                                    System.out.print("Birilli affondati con il " + s.nToScrito(k, false) + " lancio: ");
                                    lancio = in.nextInt();
                                }while(lancioprec+lancio>10);
                                if(lancioprec+lancio==10 && lancio!=10 && lancioprec!=10)
                                    s.Spare();
                            }else{
                                lancio = 0;
                                s.Strike();
                            }
                        }while(lancio<0||lancio>10);


                    p.setPunteggio(i, j, lancio, s.resetSpare(), s.resetStrike());
                }
                s.separatoreBasico();
                lancio = 0;
            }
        }

        s.pulisciConsole();

        s.stampaVincitore(p.stampaPunteggio(0, 0, true));

        System.out.println("\npremere il tasto invio per continuare");

        s.aspettaInvio();

        Menu();
    }

    private void storicoPartite(){
        int scelta = 0;

        s.pulisciConsole();

        File dir = new File(System.getProperty("user.dir"));

        String[] nomifiles = Arrays.toString(dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".txt");
            }
        })).replace("]","").split(",");

        for (int i = 0; i < nomifiles.length; i++) {
            nomifiles[i] = nomifiles[i].split("\\\\")[nomifiles[i].split("\\\\").length-1];
        }

        Scanner in = new Scanner(System.in);

        do{

            if(scelta<0||scelta> nomifiles.length)
                s.stampaErrore("\t\tSCELTA INESISTENTE");

            s.separatoreAvanzato();

            for (int i = 0; i < nomifiles.length; i++) {
                System.out.println("\t[" +(i+1)+"] "+ nomifiles[i].replace(".txt",""));
            }

            System.out.println("\t[0] TORNA AL MENU");

            s.separatoreAvanzato();

            System.out.print("Scelta: ");

            scelta = in.nextInt();
        }while(scelta<0||scelta> nomifiles.length);

        if(scelta==0)
            Menu();

        s.pulisciConsole();

        Punteggio p = new Punteggio(nomifiles[scelta-1], 1);

        p.stampaPunteggio(0,0, false);

        s.separatoreBasico();

        System.out.println("\npremere il tasto invio per continuare");

        s.aspettaInvio();

        Menu();

    }

    private void regolamento(){
        s.pulisciConsole();
        BufferedReader br = s.lettoreFile("resources/regolamento.txt");
        String riga;

        s.separatoreBasico();

        try{
            while((riga=br.readLine())!=null){
                System.out.println(riga);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();

        s.separatoreBasico();

        System.out.println("\npremere il tasto invio per continuare");

        s.aspettaInvio();

        Menu();

    }
}
