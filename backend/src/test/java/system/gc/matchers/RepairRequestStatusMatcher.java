package system.gc.matchers;

import lombok.Getter;
import lombok.Setter;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import system.gc.dtos.RepairRequestDTO;
import system.gc.dtos.StatusDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class RepairRequestStatusMatcher extends TypeSafeMatcher<Set<StatusDTO>> {

    private StatusDTO safeStatusDTO;

    public RepairRequestStatusMatcher(StatusDTO statusDTO)
    {
        this.safeStatusDTO = statusDTO;
    }

    @Override
    protected boolean matchesSafely(Set<StatusDTO> statusDTOS) {
        Set<StatusDTO> setFiltered = statusDTOS
                .parallelStream()
                .filter(statusDTO -> Objects.equals(statusDTO.getId(), safeStatusDTO.getId()))
                .collect(Collectors.toSet());
        return Objects.equals(statusDTOS.size(), setFiltered.size());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(String.format("%s (ID=%d - NAME=%s)", safeStatusDTO.getClass(), safeStatusDTO.getId(), safeStatusDTO.getName()));
    }

    @Override
    protected void describeMismatchSafely(Set<StatusDTO> item, Description mismatchDescription) {
        List<StatusDTO> statusDTOList = item.parallelStream().toList();
        mismatchDescription.appendText("[");
        for (int index = 0; index < statusDTOList.size(); index++)
        {
            StatusDTO itemIndex = statusDTOList.get(index);
            mismatchDescription.appendText(String.format("%s (ID=%d - NAME=%s)", itemIndex.getClass(), itemIndex.getId(), itemIndex.getName()));
            int next = index + 1;
            if (next < statusDTOList.size()) mismatchDescription.appendText(", ");
        }
       mismatchDescription.appendText("] ");
    }
}
