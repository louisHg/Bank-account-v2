export class UserDto {
  userId: number | null = null;
  userName: string | null = null;
  userFirstName: string | null = null;
  userEmail: string | null = null;
  userPhoneNumber: string | null = null;
  userAddress: string | null = null;
  userJobTitle: string | null = null;
  userDateOfBirth: string | null = null;
  userAccountBalance: string | null = null;
  userIdentity: string | null = null;

  // Adding an index signature to allow dynamic property access
  [key: string]: string | number | null;

  constructor(init?: Partial<UserDto>) {
    Object.assign(this, init);
  }
}
