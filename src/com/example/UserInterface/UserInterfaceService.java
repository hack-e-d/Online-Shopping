package com.example.UserInterface;

import com.example.authentication.AuthenticationOperationsImpl;
import com.example.userRegisteration.RegisterService;
import com.example.userRegisteration.RegistrationImpl;
import com.example.userRegisteration.User;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class UserInterfaceService {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        int option=-1;
        String userId =null;
        int userType = -1;
        boolean loginSignUpFlag = false;
        Scanner scanner=new Scanner(System.in);
        while (option !=3) {
            System.out.println("############## WELCOME TO ONLINE SHOPPING ################");
            System.out.println("\n\n##### Login / Sign up #####\n 1 - Login\n2 - Sign up\n 3 - Exit App\nEnter the Option : ");
            option = scanner.nextInt();
            switch (option){
                case 1 :
                    AuthenticationOperationsImpl authenticationOperations=new AuthenticationOperationsImpl();
                    userId = authenticationOperations.login();
                    if(userId != null){
                        loginSignUpFlag = true;
                    } else {
                        System.out.println("GENESIS : Incorrect password Please Login in again!!!");
                    }
                    break;

                case 2:
                    RegisterService registerService=new RegisterService();
                    Map<String,Object> userInputHashMap = registerService.getUserInput();
                    User testUser =registerService.initialiseUserFromHashMap(userInputHashMap);
                    RegistrationImpl registration=new RegistrationImpl();
                    try {
                        System.out.println( registration.signUp(testUser));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    System.exit(0);
            }
            if (loginSignUpFlag){
                break;
            }
        }
        UserInterfaceOperationsImpl userInterfaceOperations=new UserInterfaceOperationsImpl();
        userInterfaceOperations.performUserOperations(userId);
    }
}
