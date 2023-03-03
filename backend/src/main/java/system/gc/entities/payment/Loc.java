package system.gc.entities.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Loc {
    private String location;
    private Integer id;
    private String tipoCob;

    public Loc(){}

}