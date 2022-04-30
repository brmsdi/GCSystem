import { TypeProblem } from "types/type-problem"
import http from "utils/http"
import { REQUEST_TYPE_PROBLEM } from "utils/requests"

export const getAllTypeProblem = () => {
    return http.get<TypeProblem[]>(`${REQUEST_TYPE_PROBLEM}`)
}