package medical.medical.files.exeptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;



@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Examination not found!")
public class ExaminationNotFoundException extends Throwable   {
    private int statusCode;

    public ExaminationNotFoundException() {
        this.statusCode = 404;
    }

    public ExaminationNotFoundException(String message) {
        super(message);
        this.statusCode = 404;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
