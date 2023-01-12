export interface Analytic {
  id?: number;
  billingDate: Date;
  customerID: number;
}
export interface FullPageAnalytic {
  content: Array<Analytic>;
  size: number;
  totalPages: number;
  totalElements: number;
  number: number;
}

export interface PageAnalytic {
  _embedded: {
    bills: Array<Analytic>;
  };
  page: {
    size: number;
    totalPages: number;
    totalElements: number;
    number: number;
  };
}
