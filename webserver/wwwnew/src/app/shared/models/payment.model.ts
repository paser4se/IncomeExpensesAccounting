import { Category } from './category.model';

export interface Payment {
    id: number;
    bookingDate: string;
    amount: string;
    currency: string;
    bookingText: string;
    category: Category;
}
