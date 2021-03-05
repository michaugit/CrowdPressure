package Crowd_Pressure_AGH;

import java.io.*;

/** The class for messages printing in terminal */
public final class TerminalPrinter {
    static boolean isFirst=true;

    public static void print(String message){
        System.out.println(message);
        //exportLogsToTxt(message);
    }

    private static void exportLogsToTxt(String message){
        try {
            File file= new File ("CrowdPressureAGH_LOG.txt");
            FileWriter fileWriter;
            if (file.exists()) {
             fileWriter = new FileWriter(file, !isFirst);
            } else {
               fileWriter = new FileWriter(file);
            }
            isFirst=false;
            PrintWriter writer = new PrintWriter(fileWriter);
            writer.println(message);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
