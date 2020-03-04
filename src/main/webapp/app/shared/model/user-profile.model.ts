export interface IUserProfile {
  id?: number;
  staffNumber?: string;
  userLogin?: string;
  userId?: number;
  departmentDepartmentNumber?: string;
  departmentId?: number;
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public staffNumber?: string,
    public userLogin?: string,
    public userId?: number,
    public departmentDepartmentNumber?: string,
    public departmentId?: number
  ) {}
}
