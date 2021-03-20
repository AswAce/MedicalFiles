package medical.medical.files.model.enteties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import medical.medical.files.model.enums.RatingEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "feedback")
public class FeedbackEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private RatingEnum rating;
    private String comment;
}
