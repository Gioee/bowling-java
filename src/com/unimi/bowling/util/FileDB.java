package com.unimi.bowling.util;

import java.io.*;
import java.util.List;

public class FileDB {
    Strumenti s = new Strumenti();
    String file;

    public FileDB(String nomefile){
        try{
            FileWriter fw = new FileWriter(nomefile, true);
            fw.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        this.file=nomefile;
    }

    public void nuovoUtente(int id, String nome){
        PrintWriter pw = s.scrittoreFile(file, true);
        pw.println(id + ";" + nome + ";");
        pw.close();
    }

    public int numGiocatori() {

        BufferedReader br = s.lettoreFile(file);
        int n = 0;

        try{
            while(br.readLine()!=null){
                n++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return n;
    }

    public String idToGiocatore(int idGiocatore){

        List<String> fileLista = s.getFile(file);

        return fileLista.get(idGiocatore-1).toString().split(";")[1].toUpperCase();
    }

    public String[] punteggioTurnoGiocatore(int turno, int idGiocatore){
        String[] punteggio = new String[2];
        int n = 1;
        String riga = null;

        BufferedReader br = s.lettoreFile(file);

        try{
            while((riga=br.readLine())!=null&&n!=idGiocatore){
                n++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        n = riga.split(";").length;

        if(n>(turno-1)*2+2){
            punteggio[0] = riga.split(";")[(turno-1)*2+2];
        } else {
            punteggio[0]=" ";
        }

        if(n>(turno-1)*2+2+1){
            punteggio[1] = riga.split(";")[(turno-1)*2+2+1];
        } else {
            punteggio[1]=" ";
        }

        return punteggio;
    }

    public void setPunteggio(int idGiocatore, int turno, int lancio, boolean spare, boolean strike){

        List<String> fileLista = s.getFile(file);

        fileLista.set(idGiocatore-1, fileLista.get(idGiocatore-1).toString() + (strike?"X":spare?"/":lancio) + ";");

        PrintWriter pw = s.scrittoreFile(file, false);

        for (int i = 0; i < fileLista.size(); i++) {
            pw.println(fileLista.get(i));
        }

        pw.close();
    }

}
