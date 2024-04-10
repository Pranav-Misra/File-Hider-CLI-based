package views;

import dao.*;
import model.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    UserView(String email){
        this.email = email;
    }
    public void home(){
        do{
            System.out.println("Welcome " + this.email);
            System.out.println("Press 1 to show only hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhidden a file");
            System.out.println("Press 0 to exist");
            Scanner sc = new Scanner(System.in);
            int ch = sc.nextInt();
            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFile(this.email);
                        System.out.println("ID - File Name");
                        for (Data file : files){
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                       e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file path -: ");
                    Scanner t = new Scanner(System.in);
                    String path = t.nextLine();

                    File f =new File(path);
                    Data file = new Data(0, f.getName(),path,this.email);
                    try{
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                       e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> {
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFile(this.email);

                    System.out.println("ID - File Name");
                    for (Data file : files) {
                        System.out.println(file.getId() + " - " + file.getFileName());
                    }
                    System.out.println("Enter the id of file to unhide");
                    int id = sc.nextInt();
                    boolean isValidID = false;
                    for (Data file : files) {
                        if (file.getId() == id) {
                            isValidID = true;
                            break;
                        }
                    }
                    if (isValidID) {
                        DataDAO.unhide(id);
                    } else {
                        System.out.println("Wrong ID");
                    }
                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
                }
                case 0 ->{
                    System.exit(0);
                }
            }
        }while (true);
    }
}
