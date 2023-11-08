// Gage Notargiacomo, Fall 2023
// This assignment prominently features the usage of abstraction, accessing 
// final variables, getters, and setters.
// 
// 
// -----------------------------------------------------------------------------
// This is the logic used to access the final "list" var:
// 
//          BookList library = new BookList();
//          LibraryBook lb = new LibraryBook();
//          lb.setBookInfo("ann", "anything", "123456", 23, "ann", "6");
//          library.getList()[0] = lb;
//          library.getList()[0].printBook();  
// 
// -----------------------------------------------------------------------------

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class LibraryKeeper {

      public static void main(String args[]) {      

            System.out.println("Welcome to the book program!");
            Scanner reader = new Scanner(System.in);
            BookList library = new BookList();
            String input = "";
            String end = "";
            int book_count = 0;
            int lb_count = 0;
            int bb_count = 0;
            boolean flag = true;

            // Ensure the user enters a response correctly
            System.out.print("Would you like to create a book object? (yes/no): ");
            while (flag)
            {
                  input = reader.next();
                  if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no"))
                  {
                        end = input;
                        flag = false;
                  }
                  else
                        System.out.print("I'm sorry but " + input + " isn't a valid answer. Please enter either yes or no: ");
            }

            while (end.equalsIgnoreCase("yes"))
            {
                  System.out.print("Please enter the author, title and the isbn of the book separated by /: ");
                  reader.nextLine();
                  input = reader.nextLine();
                  String [] base_book_info = input.split("/");

                  // Ensure the user enters a response correctly
                  System.out.print("Got it!\nNow, tell me if it is a bookstore book or a library book " + 
                                    "(enter BB for bookstore book or LB for library book): ");
                  flag = true;
                  while (flag)
                  {
                        input = reader.next();
                        if (input.equalsIgnoreCase("BB") || input.equalsIgnoreCase("LB"))
                              flag = false;
                        else
                              System.out.print("Oops! That's not a valid entry. Please try again: ");
                  }

                  System.out.println("Got it!");

                  if (input.equalsIgnoreCase("BB"))
                  {
                        // Create a BB object and add in its base information
                        BookstoreBook bb = new BookstoreBook();
                        bb.setBookInfo(base_book_info[0].toUpperCase(), base_book_info[1].toUpperCase(), 
                                          base_book_info[2].toUpperCase(), "bb");

                        // Stores the original price
                        System.out.print("Please enter the list price of " + bb.getTitle().toUpperCase() + " by " + 
                                          bb.getAuthor().toUpperCase() + ": ");
                        float price = Float.parseFloat(reader.next());
                        bb.setBookInfo(price);

                        // Ensure the user enters a response correctly
                        flag = true;
                        while (flag)
                        {
                              System.out.print("Is it on sale? (y/n): ");
                              input = reader.next();

                              // Will add a deduction to the original list price
                              if (input.equalsIgnoreCase("y"))
                              {
                                    bb.setBookInfo("y");
                                    System.out.print("Deduction percentage: ");
                                    String percentage = reader.next();
                                    String [] array = percentage.split("%");
                                    bb.setBookInfo(Integer.parseInt(array[0]));
                                    flag = false;
                              }

                              else if (input.equalsIgnoreCase("n"))
                                    flag = false;

                              else
                                    System.out.print("Oops! That's not a valid entry. ");
                        }

                        // Stores the instantiated BB into the book index
                        System.out.println("Got it!\nHere is your bookstore book information");
                        library.getList()[book_count] = bb;
                        library.getList()[book_count].printBook();
                        book_count++;
                        bb_count++;
                  }

                  else 
                  {     
                        // Stores all LB information since all is needed is the base info
                        LibraryBook lb = new LibraryBook();
                        lb.setBookInfo(base_book_info[0].toUpperCase(), base_book_info[1].toUpperCase(), 
                                          base_book_info[2].toUpperCase(), "lb");
                        lb.setBookInfo(ThreadLocalRandom.current().nextInt(1, 100), lb.getAuthor().substring(0,3), 
                                          lb.getIsbn().substring(lb.getIsbn().length() - 1));

                        // Stores the instantiated LB into the book index
                        System.out.println("Here is your library book information");
                        library.getList()[book_count] = lb;
                        library.getList()[book_count].printBook();

                        book_count++;
                        lb_count++;
                  }

                  // Ensure the user enters a response correctly
                  System.out.print("Would you like to create a book object? (yes/no): ");
                  flag = true;
                  while (flag)
                  {
                        input = reader.next();
                        if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no"))
                        {
                              end = input;
                              flag = false;
                        }
                        else
                              System.out.print("I'm sorry but " + input + " isn't a valid answer. Please enter either yes or no: ");
                  }
            }

            // The instances of BB and LB are now accessed and presented to the user
            reader.close(); /* Always close scanners */
            System.out.println("Sure!\nHere are all your books...");
            System.out.format("Library Books (%d)\n", lb_count);
            for (int i=0; i<book_count; i++)
            {
                  if (library.getList()[i].getType() == "lb")
                        library.getList()[i].printBook();
            }
            System.out.println("----");
            System.out.format("Bookstore Books (%d)\n", bb_count);
            for (int i=0; i<book_count; i++)
            {
                  if (library.getList()[i].getType() == "bb")
                        library.getList()[i].printBook();
            }
            System.out.println("----");
            System.out.println("Take care now!");
      }

}

//___________________________

abstract class Book {

      private String author;
      private String title;
      private String isbn;
      private String type;
      
      // This method will be overloaded later on but is used to store the base information
      public void setBookInfo(String author, String title, String isbn, String type) {
            this.author = author;
            this.title = title;
            this.isbn = isbn;
            this.type = type;
      }

      public String getAuthor() {
            return author;
      }
      public String getTitle() {
            return title;
      }
      public String getIsbn() {
            return isbn;
      }
      public String getType() {
            return type;
      }

      // Every book will need the ability to print its contents
      abstract void printBook();
      
}


//___________________________

class BookstoreBook extends Book {

      float price;
      String sale;
      int deduction;

      // Here, the abstract method is enforced for BBs
      public void printBook()
      {
            float sale_price = this.price - (this.price * this.deduction /  100);
            System.out.format("[%s-%s by %s, $%.2f listed for $%.2f]\n", this.getIsbn(), this.getTitle(), 
                              this.getAuthor(), this.price, sale_price);
      }

      // Below are setters and getters for BB
      public void setBookInfo(float price) {
            this.price = price;
      }

      public void setBookInfo(String sale) {
            this.sale = sale;
      }

      public void setBookInfo(int deduction) {
            this.deduction = deduction;
      }

      public float getPrice() {
            return price;
      }
      public String getSale() {
            return sale;
      }
      public int getDeduction() {
            return deduction;
      }
}

//___________________________
class LibraryBook extends Book {

      int xx;
      String yyy;
      String c;

      // Here, the abstract method is enforced for LBs
      public void printBook()
      {
            System.out.format("[%s-%s by %s-%02d.%s.%s]\n", this.getIsbn(), this.getTitle(),
                              this.getAuthor(), this.xx, this.yyy, this.c);
      }

      // Below are setters and getters for LB
      public void setBookInfo(int xx, String yyy, String c) {
            this.xx = xx;
            this.yyy = yyy;
            this.c = c;
      }

      public int getXx() {
            return xx;
      }
      public String getYyy() {
            return yyy;
      }
      public String getC() {
            return c;
      } 
      
}

//___________________________

class BookList {

      // Making this private means it cannot be accessed directly as a field
      // So, setters and getters must be enforced as seen below
      private Book[] list;

      public BookList() {

           list = new Book[100];
      }

      public Book[] getList() {
            return list;
      }

      public void setList(Book[] list) {
            this.list = list;
      }

}