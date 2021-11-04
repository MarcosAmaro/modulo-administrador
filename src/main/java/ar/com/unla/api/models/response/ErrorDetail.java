package ar.com.unla.api.models.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorDetail {

    private String code;

    private List<String> messages;

    public ErrorDetail(String code, List<String> messages) {
        this.code = code;
        this.messages = messages;
    }
}
