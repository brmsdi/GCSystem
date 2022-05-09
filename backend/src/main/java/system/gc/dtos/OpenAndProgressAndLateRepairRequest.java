package system.gc.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class OpenAndProgressAndLateRepairRequest {
    private Integer openRepairRequest;
    private Integer progressRepairRequest;
    private Integer lateRepairRequest;

    @JsonIgnore
    private Map<String, Integer> values;

    public OpenAndProgressAndLateRepairRequest() {}

    public OpenAndProgressAndLateRepairRequest(Integer openRepairRequest, Integer progressRepairRequest, Integer lateRepairRequest, Map<String, Integer> values) {
        setOpenRepairRequest(openRepairRequest);
        setProgressRepairRequest(progressRepairRequest);
        setLateRepairRequest(lateRepairRequest);
        setValues(values);
    }

    public void generate(String open, String progress, String late) {
        if (!values.isEmpty()) {
            openRepairRequest = values.get(open);
            progressRepairRequest = values.get(progress);
            lateRepairRequest = values.get(late);
        }
    }


}
