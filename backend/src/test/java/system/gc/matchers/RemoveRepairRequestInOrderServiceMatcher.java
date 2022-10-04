package system.gc.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import system.gc.dtos.RepairRequestDTO;
import system.gc.dtos.StatusDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RemoveRepairRequestInOrderServiceMatcher<DTO extends RepairRequestDTO, T extends Set<DTO>> extends TypeSafeMatcher<T> {

    private T repairRequests;

    private StatusDTO finallyStatus;

    public RemoveRepairRequestInOrderServiceMatcher(T repairRequests, StatusDTO finallyStatus)
    {
        this.repairRequests = repairRequests;
        this.finallyStatus = finallyStatus;
    }

    @Override
    protected boolean matchesSafely(T param)
    {
        if (!Objects.equals(repairRequests.size(), param.size())) return false;
        boolean contains = false;
        List<DTO> updatedRepairRequestDTOList = new ArrayList<>(param.stream().toList());
        for (RepairRequestDTO repairRequestDTOItem : repairRequests)
        {
            contains = false;
            for (int index = 0; index < updatedRepairRequestDTOList.size(); index++ )
            {
                if (repairRequestDTOItem.getId().equals(updatedRepairRequestDTOList.get(index).getId())
                        && updatedRepairRequestDTOList.get(index).getStatus().getId().equals(finallyStatus.getId()))
                {
                    updatedRepairRequestDTOList.remove(index);
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                break;
            }
        }
        return contains;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(repairRequests.toString());
    }
}
