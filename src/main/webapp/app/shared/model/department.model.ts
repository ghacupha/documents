export interface IDepartment {
  id?: number;
  departmentName?: string;
  departmentNumber?: string;
}

export class Department implements IDepartment {
  constructor(public id?: number, public departmentName?: string, public departmentNumber?: string) {}
}
