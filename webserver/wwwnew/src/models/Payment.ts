declare interface Payment {
    id: number,
    bookingDate: string,
    amount: string,
    currency: string,
    bookingText: string,
    writeOffUnit: number,
    writeOffNumber: number,
    category: Category
}