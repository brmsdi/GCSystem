package system.gc.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class OrderService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private Date generationDate;

	@NotNull
	private Date reservedDate;

	private Date completionDate;

	@OneToMany
	private Set<RepairRequest> repairRequests;

	@ManyToMany
	@JoinTable(name = "OrderServiceEmployee",
			joinColumns = @JoinColumn(name = "orderService_id"),
			inverseJoinColumns = @JoinColumn(name = "employee_id"))
	private Set<Employee> employees;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_status_id", referencedColumnName = "id")
	private Status status;
}
