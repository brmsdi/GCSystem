export const statusColor = (value: string) => {
    value = value.toUpperCase();
    if (value === "ABERTO") {
      return "modal-status-open"
    } else if (value === "EM ANDAMENTO") {
      return "modal-status-progress"
    } else if (value === "ATRASADO") {
      return "modal-status-late"
    } else {
      return "modal-status-concluded"
    }
  }