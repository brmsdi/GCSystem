import { ActivityType } from "./activity-type";
import { Debt } from "./debt";
import { Employee } from "./employee";

export type Movement = {
    id?: number;
    moveDateAndTime: string;
    dueDate: string;
    previousValue: number;
    debt: Debt;
    activityType: ActivityType;
    employee: Employee;
}