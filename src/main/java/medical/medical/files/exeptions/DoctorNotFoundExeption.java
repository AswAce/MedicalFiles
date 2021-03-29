package medical.medical.files.exeptions;

public class DoctorNotFoundExeption extends RuntimeException {
    public DoctorNotFoundExeption(String s) {
        super(s);
    }
}
