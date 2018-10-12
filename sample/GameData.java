package sample;

import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;

import java.io.*;
import java.lang.IllegalArgumentException;
import java.util.Base64;
import java.util.Optional;
import javax.swing.JOptionPane;

public class GameData {

    final static String encryptionKey = "sUp3rS3cUr3kEy";

    public static String getGameDataContent() throws IOException{

        String fileName = "gamedata";
        File f = new File(fileName);

        if (f.exists() && !f.isDirectory()) {

            String data;
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            data = bufferedReader.readLine();

            // Always close files.
            bufferedReader.close();

            return data;

        }
        else {
            throw new IOException();
        }
    }

    public static String decodeGameDataContent() throws IOException, IllegalArgumentException{

        String encodedData = getGameDataContent();
        try {
            byte[] decodedData = Base64.getDecoder().decode(encodedData);
            String data = new String(decodedData);
            // <--- AES decryption ---> //
            data = AES.decrypt(data, encryptionKey);
            // <----------------------> //
            return data;
        }
        catch (IllegalArgumentException exception) {
            throw exception;
        }
    }

    public static String[][] parseGameData() throws Exception{

        String decodedString = decodeGameDataContent();
        try {
            String[] data = decodedString.split("\\n");
            String[][] parsedData = new String[10][2];
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) parsedData[i] = data[i].split("\\:");
            }
            return parsedData;
        }
        catch (Exception exception){
            throw exception;
        }
    }

    public static int getScoreboardLength(){
        try {
            String[][] data = parseGameData();
            int i = 0;
            while (i < data.length && data[i][0] != null && data[i][1] != null) i++;
            return i;
        }
        catch (Exception exception){
        }
        return 0;
    }

    public static int getHighestScore(){

        try {
            String[][] data = parseGameData();
            int heighestScore = 0;
            for (int i = 0; i < data.length; i++) {
                if (data[i][0] != null &&
                    data[i][1] != null &&
                    Integer.parseInt(data[i][1]) > heighestScore) heighestScore = Integer.parseInt(data[i][1]);
            }
            return heighestScore;
        }
        catch (Exception exception){
            try {
                PrintWriter writer = new PrintWriter("gamedata", "UTF-8");
                writer.close();
            }
            catch(Exception e){
            }
        }
        return 0;
    }

    public static boolean avaiablePlace(){

        try {
            String[][] data = parseGameData();
            int i = data.length - 1;
            int score = InGameWindow.widgets.getScore();
            return ((data[i][0]==null && data[i][1]==null) || score > Integer.parseInt(data[i][1]));
        }
        catch (Exception exception){
            try {
                PrintWriter writer = new PrintWriter("gamedata", "UTF-8");
                writer.close();
            }
            catch(Exception e){
            }
        }
        return true;
    }

    public static void updateGameDataContent(String nickname, int score){

        nickname = nickname.replace(":", "");

        try {
            String[] temp = new String[2];
            String[][] data = parseGameData();

            int i = data.length-1;
            if ((data[i][1]==null&&data[i][0]==null) || score > Integer.parseInt(data[i][1])) {
                data[i][0] = nickname;
                data[i][1] = Integer.toString(score);
                while (i > 0 && ((data[i - 1][0] == null && data[i - 1][1] == null) || score > Integer.parseInt(data[i - 1][1]))) {
                    temp[0] = data[i - 1][0];
                    temp[1] = data[i - 1][1];
                    data[i - 1][0] = data[i][0];
                    data[i - 1][1] = data[i][1];
                    data[i][0] = temp[0];
                    data[i][1] = temp[1];
                    i--;
                }
            }

            String parsedData = "";
            for (i = 0; i < data.length; i++){
                if (data[i][0] != null && data[i][1] != null){
                    parsedData += data[i][0] + ":" + data[i][1] + "\n";
                }
            }

            // <--- AES encryption ---> //
            parsedData = AES.encrypt(parsedData, encryptionKey);
            // <----------------------> //

            byte[] encodedData = Base64.getEncoder().encode(parsedData.getBytes());
            parsedData = new String(encodedData);
            try {
                PrintWriter writer = new PrintWriter("gamedata", "UTF-8");
                writer.print(parsedData);
                writer.close();
            }
            catch (Exception e){
            }
        }
        catch (Exception e){
            String parsedData = nickname + ":" + Integer.toString(score);
            parsedData = AES.encrypt(parsedData, encryptionKey);
            byte[] encodedData = Base64.getEncoder().encode(parsedData.getBytes());
            parsedData = new String(encodedData);
            try {
                PrintWriter writer = new PrintWriter("gamedata", "UTF-8");
                writer.print(parsedData);
                writer.close();
            }
            catch (Exception ex){
            }
        }
    }

}