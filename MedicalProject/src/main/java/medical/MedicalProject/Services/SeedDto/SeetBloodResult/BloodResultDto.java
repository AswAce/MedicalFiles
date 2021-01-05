package medical.MedicalProject.Services.SeedDto.SeetBloodResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodResultDto {
    @NotNull
    private String email;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private String result;
    private static final DateTimeFormatter FORMATER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//"2016-03-04 example

    public BloodResultDto(@NotNull String email, @NotNull String date, @NotNull String result) {
        this.email = email;
        this.date = LocalDateTime.parse(date, FORMATER);
        this.result = result;
    }
}
