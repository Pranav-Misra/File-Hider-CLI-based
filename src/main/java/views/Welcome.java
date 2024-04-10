package views;

import dao.*;
import model.*;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the app");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signup");
        System.out.println("Press 0 to exit");
        int choice = 0;
        try{
            choice = Integer.parseInt(br.readLine());
        } catch (IOException ex){
            ex.printStackTrace();

        }
        switch(choice) {
            case 1 -> logIn();
            case 2 -> signUp();
            case 0 ->  System.exit(0);
        }
    }

    protected void logIn() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter email");
        String email = sc.nextLine();
        try{
            if(UserDAO.isExists(email)) {
                String genOtp = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email , genOtp);
                System.out.println("Enter the OTP");
                String otp = sc.nextLine();
                if(otp.equals(genOtp)) {
                    new UserView(email).home();

                } else {
                    System.out.println("Wrong otp");
                }
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name ");
        String name = sc.nextLine();
        System.out.println("Enter email");
        String email = sc.nextLine();
        String genOtp = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email , genOtp);
        System.out.println("Enter the OTP");
        String otp = sc.nextLine();
        if(otp.equals(genOtp)) {

            User user = new User(name,email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0 -> System.out.println("User already exists");
                case 1 -> System.out.println("User saved");
            }

        } else {
            System.out.println("Wrong OTP");
        }


    }
}
