package heig.model.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String content;
}
