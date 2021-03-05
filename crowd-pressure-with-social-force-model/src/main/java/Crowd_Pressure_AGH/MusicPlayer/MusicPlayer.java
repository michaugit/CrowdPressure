package Crowd_Pressure_AGH.MusicPlayer;

import Crowd_Pressure_AGH.TerminalPrinter;
import javazoom.jl.player.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/** The class to handle background music in application */
public final class MusicPlayer {
    private static boolean isPlayerOn=false;
    private static String filename;
    private static Player player;
    static Thread playMusic;


    public static void setMusic(String fileName) {
        filename = fileName;
    }

    public static void play() {
        if(isPlayerOn) {
            try {
                InputStream in = MusicPlayer.class.getResourceAsStream(filename);
                player = new Player(in);
            } catch (Exception e) {
                System.out.println("Problem playing file " + filename);
                System.out.println(e);
            }
        }
    }

    public static void start() {
        if(isPlayerOn) {
            play();
            playMusic = new Thread(new PlayMusic());
            playMusic.start();
        }
    }

    public static void stop() {
            close();
            playMusic = null;
    }

    public static void close() {
            if (player != null) {
                player.close();
            }
    }

    public static void turnOnPlayer(){
        isPlayerOn=true;
    }

    public static void turnOffPlayer(){
        isPlayerOn=false;
    }

    static class PlayMusic implements Runnable {

        public void run() {
            try {
                player.play();
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}