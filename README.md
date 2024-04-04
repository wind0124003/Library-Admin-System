# Library admin system
## Description
- This system allows library admin staff to  manage the book. They can add, edit, delete, and search books in the system. 
- Also, they can display all book records when clicking the "display all", "display all by ISBN", and "display all by title" buttons. 
The book will be displayed in ascending order or descending order.
- Besides, the staff can help students to borrow, return and reserve the books through this system.

## How to use
### Main page
![image](https://github.com/wind0124003/Library-Admin-System/assets/59653164/aa93fb23-17b0-441d-a431-742da70cda4f)

#### Add book
Input ISBN, title and click the "Add" button. A book will be added and displayed on the list. If the text fields are empty, an error message will be prompted.
#### Delete book
Input ISBN, and click the "Delete" button. If the book exists in the list, the book will be deleted.
#### Edit book
Staff can edit ISBN and title of a book after he inputs the ISBN. If he click the "Save" button, the book will be replaced with new information.

#### Search book
Input ISBN or title and click the "Search" button. The system will search the books that match the criteria.
#### Display book
- "Display all": display all books by order of adding into the list.
- "Display all by ISBN": display all books in ascending order according to ISBN. When clicking the button again, it will be in descending order.
- "Display all by Title": display all books in ascending order according to title. When clicking the button again, it will be in descending order.

### Book page
This page is to help students borrowing, reserving, returning books. Users can click the button to finish this operation. After clicking the button, the latest information will be displayed in the text areas.
![image](https://github.com/wind0124003/Library-Admin-System/assets/59653164/37a75c1f-fc2e-4808-8c93-3417dfe7baf9)
### Reserve book
When clicking this button, a message box is prompted and asks for inputting the name of student who has reserved the book.
![image](https://github.com/wind0124003/Library-Admin-System/assets/59653164/14449cf7-1eb0-46b9-9d12-502b512bc140)

### Return book
The system will check if any students reserve this book. If other students reserve the book, the available state of the books will remain false. 
The student, who reserved the book first, will borrow this book.
