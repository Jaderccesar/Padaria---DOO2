/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exception;

/**
 *
 * @author Usuario
 */
public class AppException extends RuntimeException{
     public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
