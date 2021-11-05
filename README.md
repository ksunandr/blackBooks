# blackBooks
Web application REST API: book shop

Entity: Book и Author. 
Book:  
  * name, 
  * year, 
  * Authors, 
  * quantity.

Author: 
  * First name, 
  * Last name,
  * patronymic, 
  * books. 

Author to Book connection is many to many. 
Relational database

Rest api: 
1. Get a list of books by filter with sorting and pagination

    1.1. Filter:
     * name of book, 
     * Year range,
     * Author's last name,
     * status (absent: quantity = 0/ in stock: quantity > =1)
    
   Attributes could be optional 
   
2. Add a book
3. Update a book
4. Sell a book: decrease quantity. Competitiveness
Exception in case of selling book with zero quantity.

Spring. 
ORM 
No authorization
Any bd
No front
