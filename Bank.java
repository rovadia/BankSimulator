import java.util.*;

class Bank {
   static Scanner scan = new Scanner( System.in );
   static BankAccount accounts[]=new BankAccount[1];
   static int numOfAccounts=0;
   
   public static void main (String args[]) {
      String command;
      int currentIndex=-1;
      do {
         menu(currentIndex);
         System.out.print("Enter a command ");
         command=scan.next();
         
         if (command.equalsIgnoreCase("o")) {
            currentIndex++;
            openAcc(currentIndex);
            if (numOfAccounts==accounts.length) {
               accounts=resize();
            }
         }
         if (command.equalsIgnoreCase("d")) {
            if(currentIndex!=-1) {
               accounts[currentIndex].deposit();
            }
            else {
               System.out.println("Please select an account ");
            }
         }
         if (command.equalsIgnoreCase("s")) {
            if(numOfAccounts>0) {
               currentIndex = select(currentIndex);
            }
            else {
               System.out.println("There are no accounts to select ");
            }
         }
         if (command.equalsIgnoreCase("c")) {
            if(currentIndex!=-1) {
               close(currentIndex);
               currentIndex=-1;
            }
            else {
               System.out.println("Please select an account ");
            }
         }
         if (command.equalsIgnoreCase("w")) {
            if(currentIndex!=-1) {
               accounts[currentIndex].withdraw();
            }
            else {
               System.out.println("Please select an account ");
            }
         }
         if (command.equalsIgnoreCase("l")) {
            if (numOfAccounts<1) {
               System.out.println("There are no accounts to list ");
            }
            else {
            listAccounts();
            }
         }
         System.out.println("Current Index: "+currentIndex);
         System.out.println("Number of accounts: "+numOfAccounts);
      } while(!command.equalsIgnoreCase("q"));
   }
   
   /*
   * Display menu
   */
   public static void menu (int currentIndex) {
      System.out.println("O: Open account ");
      System.out.println("D: Deposit ");
      System.out.println("S: Select account ");
      System.out.println("C: Close account ");
      System.out.println("W: Withdraw ");
      System.out.println("L: List all accounts ");
      System.out.println("Q: Quit ");
      if(currentIndex!=-1){
         System.out.println("Your current account selected: "+ accounts[currentIndex].getAcNum());
         System.out.println("Balance: $"+ accounts[currentIndex].getBalance());
      }
   } 

   /*
   * Open account
   */
   static BankAccount openAcc(int currentIndex) {
      boolean duplicate = false;
      int newAcNum;
      do {
         System.out.print("What is account number? ");
         newAcNum=scan.nextInt();
         first:
         for (int i=0; i<numOfAccounts; i++) { //check for duplicates
            if (newAcNum==accounts[i].getAcNum()) {
               System.out.println("Duplicate detected try again ");
               duplicate=true;
               break first;
            }
            else {
               duplicate=false;
            } 
         }
      }while(duplicate);   
      System.out.print("What is balance? ");
      double newBalance=scan.nextDouble();
      accounts[currentIndex] = new BankAccount(newAcNum, newBalance); //create new bank account object
      numOfAccounts++;
      return accounts[currentIndex];
   }
   
   /*
   * Resize accounts array
   */
   static BankAccount[] resize() {
         BankAccount [] temp = new BankAccount [2*numOfAccounts];  
         System.arraycopy(accounts,0,temp,0,numOfAccounts);
         accounts=temp;
         return accounts;
   }
   
   /*
   * List accounts
   */
   static void listAccounts(){
      for (int i=0; i<numOfAccounts;i++) {
         System.out.println((i+1)+")   Account number: " + accounts[i].getAcNum() + "   Balance: $" + accounts[i].getBalance());
      }
   }
   
   /*
   * Select account
   */
   static int select(int currentIndex){
      System.out.print("Enter account number ");
      int userSelect=scan.nextInt();
      boolean foundAccount=false;
      first:
      for (int i=0; i<numOfAccounts; i++) {
         if(accounts[i].getAcNum()==userSelect) {
            currentIndex=i;
            foundAccount=true;
            break first;
         }
      }
      if (!foundAccount) {
         System.out.println("Account number not found ");
      }
      return currentIndex;
   }

   /*
   * Close
   */
   static void close(int currentIndex) {
      accounts[currentIndex]=null;
      if (numOfAccounts>1) {
         accounts[currentIndex]=accounts[numOfAccounts-1];
      }
      numOfAccounts--;
   }
}

class BankAccount {
   private int acNum;
   private double balance;
   static boolean accountSelected;
   
   /*
   * BankAccount constructor (open)
   */
   BankAccount(int acNum, double balance) { 
      this.acNum=acNum;
      this.balance=balance;   
   }
   
   /*
   * account getter
   */
   int getAcNum() {
      return acNum;
   }
   
   /*
   * balance getter
   */
   double getBalance() {
      return balance;
   }
   
   /*
   * Deposit
   */
   void deposit () {
      System.out.print("How much to deposit? ");
      double deposit=Bank.scan.nextDouble();
      balance+=deposit;
   }
   
   /*
   * Withdraw
   */
   void withdraw () {
      boolean askAgain;
         do {
            System.out.print("How much to withdraw? ");
            double withdraw=Bank.scan.nextDouble();
            if((balance-withdraw)<1) {
               System.out.println("Balance less than $1.00, try again");
               askAgain=true;   
            }
            else {
               askAgain=false;
               balance-=withdraw;
            }
         } while(askAgain);
   }
}

