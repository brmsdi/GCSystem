import { Employee } from "types/Employee";

export function getAll1(number1: number, number2: number ): number {
  return number1 + number2;
}

export function getAllEmployeesMock(number: number ): Employee[] {
    let employee: Employee[] = [
    {
      id: 1,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 2,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 3,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 4,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 5,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 6,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 7,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 8,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 9,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    },
    {
      id: 10,
      name: "Wisley Bruno Marques França",
      RG: "1235265",
      CPF: "02356325536",
      email: "srmarquesms@gmail.com",
      role: "Analista de sistemas",
      specialty: ["Desenvolvedor"],
      hiringDate: "01/01/2022",
    }
  ]

  // 1 - 1 = 0 * 5 = 0
  // 2 - 1 = 1 * 5 = 5 
  // 3 - 1 = 2 * 5 = 10

  var result: Employee[] = [];
  var size = number - 1;
  var initCount = size * 5;
  var finalyCount = initCount + 5;


  for (let index = initCount; index < finalyCount; index++) {
    //0
    //1
    //2
    //3
    //4
    result.push(employee[index])   
  }

  return result;
}
