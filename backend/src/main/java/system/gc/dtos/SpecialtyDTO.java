package system.gc.dtos;
import system.gc.entities.Specialty;

public class SpecialtyDTO implements ConvertEntityAndDTO<SpecialtyDTO, Specialty> {

    private Integer id;
    private String name;

    public SpecialtyDTO() {}

    public SpecialtyDTO(Specialty specialty) {
        this.id = specialty.getId();
        this.name = specialty.getName();
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
    public SpecialtyDTO toDTO(Specialty specialty) {
        return new SpecialtyDTO(specialty);
    }

    @Override
    public Specialty toEntity(SpecialtyDTO specialtyDTO) {
        Specialty specialty = new Specialty(specialtyDTO.getName());
        if(specialtyDTO.getId() != null) {
            specialty.setId(specialtyDTO.getId());
        }
        return specialty;
    }
}
