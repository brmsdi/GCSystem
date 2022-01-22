package system.gc.dtos;

import system.gc.entities.Role;
public class RoleDTO implements ConvertEntityAndDTO<RoleDTO, Role> {
    private Integer id;
    private String name;

    public RoleDTO() {}

    public RoleDTO(String name) {
        this.name = name;
    }
    public RoleDTO(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
