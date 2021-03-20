package medical.medical.files.model.serviceModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.RatingEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackServiceModel {

    private long patientId;
    private RatingEnum rating;
    private String comment;

}
