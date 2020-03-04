import { ITransactionDocument } from 'app/shared/model/transaction-document.model';
import { IFormalDocument } from 'app/shared/model/formal-document.model';

export interface IUserProfile {
  id?: number;
  staffNumber?: string;
  userLogin?: string;
  userId?: number;
  departmentDepartmentNumber?: string;
  departmentId?: number;
  transactionDocuments?: ITransactionDocument[];
  formalDocuments?: IFormalDocument[];
}

export class UserProfile implements IUserProfile {
  constructor(
    public id?: number,
    public staffNumber?: string,
    public userLogin?: string,
    public userId?: number,
    public departmentDepartmentNumber?: string,
    public departmentId?: number,
    public transactionDocuments?: ITransactionDocument[],
    public formalDocuments?: IFormalDocument[]
  ) {}
}
