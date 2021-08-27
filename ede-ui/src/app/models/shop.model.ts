import { User } from "./user.model";

export interface Shop {
  id?: string;
  name: string;
  user: User;
  image: string;
  create_date: string;
  description: string;
  address: string;
}
