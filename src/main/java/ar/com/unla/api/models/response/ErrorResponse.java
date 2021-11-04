package ar.com.unla.api.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({"details", "origin", "timestamp"})
@Data
@NoArgsConstructor
public class ErrorResponse {

    private LocalDateTime timestamp;

    private String origin;

    @JsonProperty("details")
    private List<ErrorDetail> errorDetails;

    public ErrorResponse(String origin, List<ErrorDetail> errorDetails) {
        this.origin = origin;
        this.errorDetails = errorDetails;
        this.timestamp = LocalDateTime.now();
    }
}
