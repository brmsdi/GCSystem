package system.gc.dtos;

import lombok.Getter;
import lombok.Setter;
import system.gc.entities.Role;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoleDTO implements ConvertEntityAndDTO<RoleDTO, Role> {
    private Integer id;

    @NotNull(message = "{required.validation}")
    @NotBlank(message = "{required.validation}")
    private String name;

    public RoleDTO() {}

    public RoleDTO(Role role) {
        setId(role.getId());
        setName(role.getName());
    }

    @Override
    public RoleDTO toDTO(Role role) {
        return new RoleDTO(role);
    }

    @Override
    public Role toEntity(RoleDTO roleDTO) {
        Role role = new Role(roleDTO.getName());
        if(roleDTO.getId() != null) {
            role.setId(roleDTO.getId());
        }
        return role;
    }
}
