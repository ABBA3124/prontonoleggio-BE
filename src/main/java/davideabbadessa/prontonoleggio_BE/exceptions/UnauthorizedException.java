package davideabbadessa.prontonoleggio_BE.exceptions;


public class UnauthorizedException extends RuntimeException {

    // <-------- UNAUTHORIZED 401 -------->
    public UnauthorizedException(String message) {
        super(message);
    }
}