import java.util.Scanner;
import java.io.FileWriter;  //write to files
import java.io.IOException; //deal with exceptions
import java.util.ArrayList; // import the ArrayList class
import java.io.File; //read files

class BankAccount {
    private String accountnum;
    private String name;
    private String acc_type;
    private long balance;
    Scanner sc = new Scanner(System.in);
    //Constructor
    public BankAccount(String name, String accountnum, String acc_type, long balance){
        this.accountnum = accountnum;
        this.name = name;
        this.acc_type = acc_type;
        this.balance = balance;
    }
    //method to open new account
    public BankAccount() {
        String tempAccountnum = "";
        String tempAccType = "";
        String tempName = "";
        long tempBalance = 0;
        boolean flag = false;
        while(!flag){
            try{
                System.out.print("Enter Account No: ");
                tempAccountnum = sc.next();
                System.out.print("Enter Account type: ");
                tempAccType = sc.next();
                System.out.print("Enter Name: ");
                tempName = sc.next();
                System.out.print("Enter Balance: ");
                tempBalance = sc.nextLong();
                flag = true;
            } catch(Exception e){
                System.out.println("**************************************");
                System.out.println("Error: " + e);
                System.out.println("Please Re-enter your info correctly.");
                System.out.println("**************************************");
                sc.nextLine();
            }
        }
        accountnum = tempAccountnum;
        acc_type = tempAccType;
        name = tempName;
        balance = tempBalance;
    }
    //method to display account details
    public void showAccount() {
        System.out.println("Name of account holder: " + name);
        System.out.println("Account no.: " + accountnum);
        System.out.println("Account type: " + acc_type);
        System.out.println("Balance: " + balance);
    }
    //method to deposit money
    public void deposit() {
        long amt;
        System.out.println("Enter the amount you want to deposit: ");
        amt = sc.nextLong();
        balance = balance + amt;
    }
    //method to withdraw money
    public void withdrawal() {
        long amt;
        System.out.println("Enter the amount you want to withdraw: ");
        amt = sc.nextLong();
        if (balance >= amt) {
            balance = balance - amt;
            System.out.println("Balance after withdrawal: " + balance);
        } else {
            System.out.println("Your balance is less than " + amt + "\tTransaction failed...!!" );
        }
    }
    //method to search an account number
    public boolean search(String ac_no) {
        if (accountnum.equals(ac_no)) {
            showAccount();
            return (true);
        }
        return (false);
    }

    //method to save account
    public void saveAccount(String fileContent){
        try{
            FileWriter fWriter = new FileWriter("Accounts.txt");
            fWriter.write(fileContent);
            fWriter.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public String getName(){return name;};
    public String getAccNum(){return accountnum;};
    public String getAccType(){return acc_type;};
    public Long getBalance(){return balance;};
}
public class BankingAccount {
    public static void main(String arg[]) {
        String fileContent = "";
        Scanner sc = new Scanner(System.in);
        int sizeOfTempArrList = 0;
        //load previous data
        ArrayList<BankAccount> tempList = new ArrayList<BankAccount>();
        try{
            File file = new File("Accounts.txt");
            Scanner myObj = new Scanner(file);

        while(myObj.hasNext()){
            String accName = myObj.next();
            String accNum = myObj.next();
            String accType = myObj.next();
            long accBal = Long.parseLong(myObj.next());
            BankAccount tempAccount = new BankAccount(accName, accNum, accType, accBal);
            tempList.add(tempAccount);
        }

        } catch(Exception e){
            System.out.println("Previous saved data not found.");
        }

        //create initial accounts
        System.out.print("Enter number of accounts you want? ");
        int n = sc.nextInt();
        sizeOfTempArrList = tempList.size();
        BankAccount C[] = new BankAccount[sizeOfTempArrList + n];
        for (int i = 0; i < sizeOfTempArrList; i++){
            C[i] = tempList.get(i);
        }

        for (int j = sizeOfTempArrList; j < (sizeOfTempArrList - C.length) ; j++) {
            C[j] = new BankAccount();
            // C[i].openAccount();
        }

        // loop runs until number 6 is not pressed to exit
        int ch;
        do {
            System.out.println("\n ***Banking System Application***");
            System.out.println(
                "\n 1. Display all account details " +
                "\n 2. Search by account number " +
                "\n 3. Deposit the amount " +
                "\n 4. Withdraw the amount" +
                "\n 5. Save accounts" +
                "\n 6. Exit");
            System.out.println("Enter your choice: ");
            ch = sc.nextInt();
            switch (ch) {
                case 1:
                    for (int i = 0; i < C.length; i++) {
                        C[i].showAccount();
                    }
                    break;
                case 2:
                    System.out.print("Enter Account no. : ");
                    String ac_no = sc.next();
                    boolean found = false;
                    for (int i = 0; i < C.length; i++) {
                        found = C[i].search(ac_no);
                        if (found) {
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 3:
                    System.out.print("Enter Account no. : ");
                    ac_no = sc.next();
                    found = false;
                    for (int i = 0; i < C.length; i++) {
                        found = C[i].search(ac_no);
                        if (found) {
                            C[i].deposit();
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 4:
                    System.out.print("Enter Account No : ");
                    ac_no = sc.next();
                    found = false;
                    for (int i = 0; i < C.length; i++) {
                        found = C[i].search(ac_no);
                        if (found) {
                            C[i].withdrawal();
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Search failed! Account doesn't exist..!!");
                    }
                    break;
                case 5:
                    fileContent = "";
                    for (int i = 0; i < C.length; i++) {
                        fileContent += C[i].getName() + " " + C[i].getAccNum() + " " + C[i].getAccType() + " " + C[i].getBalance() + "\n";
                        
                    }
                    C[0].saveAccount(fileContent);
                    System.out.println("All accounts have been saved.");
                    break; 

                case 6:
                    System.out.println("See you soon...");
                    break;
                }
        }
        while (ch != 6);
    }
}