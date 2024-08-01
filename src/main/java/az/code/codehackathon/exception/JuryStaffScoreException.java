package az.code.codehackathon.exception;

import lombok.Getter;

@Getter
public class JuryStaffScoreException extends RuntimeException {

    public final ErrorCodes errorCode;
    public final transient Object[] arguments;


    public JuryStaffScoreException(ErrorCodes errorCode, Object... args) {
        this.errorCode = errorCode;
        this.arguments = args == null ? new Object[0] : args;
    }
}
