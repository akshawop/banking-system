# Banking System Project Documentation

Welcome to the **Banking System** project! This guide will help you set up, understand, and run the project on your local machine using Visual Studio Code or any Java IDE.

---

## 1. Project Structure

Your project is organized as follows:

```
Banking System/
│
├── src/                        # Java source code
│   └── me/akshawop/banking/
│        ├── cli/               # Command Line Interfaces (CLIs)
│        │    ├── AccountCLI.java
│        │    ├── ATM.java
│        │    ├── BankCLI.java
│        │    ├── BranchCLI.java
│        │    ├── CustomerCLI.java
│        │    └── inputmodules/
│        │         ├── NewAddress.java
│        │         ├── UpdateAddress.java
│        │         └── forms/
│        │              ├── CreateBankForm.java
│        │              ├── CreateBranchForm.java
│        │              ├── NewAccountForm.java
│        │              ├── NewCustomerForm.java
│        │              ├── UpdateBankForm.java
│        │              └── UpdateCustomerForm.java
│        ├── customtype/
│        │    └── Address.java
│        ├── sql/
│        │    └── SQLQueries.java
│        ├── sys/
│        │    ├── Account.java
│        │    ├── AccountDAO.java
│        │    ├── AccountStatus.java
│        │    ├── AccountType.java
│        │    ├── Bank.java
│        │    ├── BankDAO.java
│        │    ├── Branch.java
│        │    ├── BranchDAO.java
│        │    ├── Card.java
│        │    ├── CardDAO.java
│        │    ├── CardStatus.java
│        │    ├── CardType.java
│        │    ├── Customer.java
│        │    ├── CustomerDAO.java
│        │    ├── DB.java
│        │    ├── Transaction.java
│        │    ├── TransactionDAO.java
│        │    ├── TransactionMode.java
│        │    └── TransactionType.java
│        └── util/
│             ├── AccountBlockedException.java
│             ├── CardBlockedException.java
│             ├── ClearScreen.java
│             ├── IncorrectPinException.java
│             ├── InputChecker.java
│             ├── InputPIN.java
│             └── NotEnoughBalanceException.java
│
├── lib/                        # External libraries (JARs)
│   ├── jline-3.30.4.jar
│   └── mysql-connector-j-9.3.0.jar
│
├── sql/                        # SQL schema for database setup
│   └── schema.sql
│
├── bin/                        # Compiled Java classes (created after build)
│
├── windows/                    # Windows scripts for compiling and running
│   ├── atm.ps1
│   ├── bank.ps1
│   ├── branch.ps1
│   └── compile.ps1
│
├── linux/                      # Linux / macOS shell scripts for compiling and running
│   ├── atm.sh
│   ├── bank.sh
│   ├── branch.sh
│   └── compile.sh
│
├── .gitignore
├── .gitattributes
└── README.md
```

---

## 2. Prerequisites

-   **Java JDK 17 or higher** installed ([Download here](https://adoptium.net/)).
-   **MySQL Server** running locally (or update connection settings in `DB.java`).
-   **Visual Studio Code** (recommended) or any Java IDE.
-   **VS Code Extensions:**
    -   Extension Pack for Java
    -   Java Dependency Viewer
    -   Code Runner (optional)

---

## 3. Setting Up the Database

1. **Start your MySQL server.**
2. **Open `sql/schema.sql`** and run its contents in your MySQL client to create the required tables and schema.

---

## 4. Configuring the Project

-   **Dependencies:**  
    The project uses MySQL Connector/J and JLine for database and console input.  
    These JARs are in the `lib/` folder.

-   **Database Connection:**  
    Edit `src/me/akshawop/banking/sys/DB.java` to match your MySQL username, password, and database name.

---

## 5. Building and Running

### **A. Compile the Project**

You can use the provided scripts or compile manually.

#### **Windows (PowerShell)**

```powershell
./windows/compile.ps1
```

#### **Linux / macOS (Shell)**

```sh
bash linux/compile.sh
```

#### **Manual Compilation**

Open a terminal in the project root and run:

```sh
javac -cp "lib/*" -d bin src/me/akshawop/banking/**/*.java
```

### **B. Run CLI Applications**

Each CLI file represents a different role in the banking system.  
**Run them individually as needed:**

#### 1. **BankCLI** (Bank Official)

-   **Purpose:** Manage bank and branches, perform high-level operations.
-   **Main File:** `src/me/akshawop/banking/cli/BankCLI.java`
-   **Run:**
    -   **Windows:**
        ```powershell
         ./windows/bank.ps1
        ```
    -   **Linux / macOS:**
        ```sh
        bash linux/bank.sh
        ```
    -   **Manual:**
        ```powershell
        # windows
        java -cp "bin;lib/*" me.akshawop.banking.cli.BankCLI
        ```
        ```sh
        # linux / macOS
        java -cp "bin:lib/*" me.akshawop.banking.cli.BankCLI
        ```
-   **What it does:**
    -   Allows creation and management of banks and branches.
    -   Uses `BankDAO` for database operations.
    -   Helper forms in `inputmodules/forms/` for user input.

#### 2. **BranchCLI** (Branch Official)

-   **Purpose:** Manage customers, accounts, and branch-level operations.
-   **Main File:** `src/me/akshawop/banking/cli/BranchCLI.java`
-   **Run:**
    -   **Windows:**
        ```powershell
        ./windows/branch.ps1
        ```
    -   **Linux / macOS:**
        ```sh
        bash linux/branch.sh
        ```
    -   **Manual:**
        ```powershell
        # windows
        java -cp "bin;lib/*" me.akshawop.banking.cli.BranchCLI
        ```
        ```sh
        # linux / macOS
        java -cp "bin:lib/*" me.akshawop.banking.cli.BranchCLI
        ```
-   **What it does:**
    -   Allows branch officials to add/update customers, manage accounts, issue cards, and handle transactions.
    -   Uses `BranchDAO` for branch-specific database operations.
    -   Helper forms and utility classes for input validation and screen clearing.

#### 3. **ATM** (Customer)

-   **Purpose:** Simulate ATM operations for customers.
-   **Main File:** `src/me/akshawop/banking/cli/ATM.java`
-   **Run:**
    -   **Windows:**
        ```powershell
        ./windows/atm.ps1
        ```
    -   **Linux / macOS:**
        ```sh
        bash linux/atm.sh
        ```
    -   **Manual:**
        ```powershell
        # windows
        java -cp "bin;lib/*" me.akshawop.banking.cli.ATM
        ```
        ```sh
        # linux / macOS
        java -cp "bin:lib/*" me.akshawop.banking.cli.ATM
        ```
-   **What it does:**
    -   Allows customers to log in, check balances, withdraw/deposit money, and view transactions.
    -   Uses `AccountDAO`, `TransactionDAO`, and related classes for account and transaction management.

---

## 6. How the System Works

### **CLI Classes (User Interfaces)**

-   **BankCLI:**

    -   Role: Bank Official
    -   Manages banks and branches.
    -   Interacts with `BankDAO` for all bank-related database operations.
    -   Uses helper forms for structured input.

-   **BranchCLI:**

    -   Role: Branch Official
    -   Manages customers and accounts within a branch.
    -   Interacts with `BranchDAO` for branch-specific operations.
    -   Uses forms and utility classes for input and validation.

-   **ATM:**
    -   Role: Customer
    -   Allows customers to access their accounts, perform transactions and view a mini statement.
    -   Interacts with `AccountDAO`, `TransactionDAO`, etc.

### **DAO Classes (Data Access Objects)**

-   **BankDAO:**  
    Handles all database operations related to bank (create, update, fetch, etc.).
-   **BranchDAO:**  
    Handles branch-specific operations (customer/account management, transactions).
-   **AccountDAO, CustomerDAO, TransactionDAO, CardDAO:**  
    Handle respective entity operations.

### **Helper Classes**

-   **Forms (`inputmodules/forms/`):**  
    Collect and validate user input for creating/updating entities.
-   **Utils (`util/`):**
    -   `ClearScreen`: Clears the console for better UX.
    -   `InputChecker`: Validates user input.
    -   Custom exceptions for error handling.

---

## 7. Roleplay Usage

-   **Bank Official:**  
    Run `BankCLI` to manage bank and branches.
-   **Branch Official:**  
    Run `BranchCLI` to manage customers, accounts, and branch operations.
-   **Customer:**  
    Run `ATM` to access personal banking features.

Each CLI is independent and should be run in its own terminal window for roleplay simulation.

---

## 8. Troubleshooting

-   **Database errors:**  
    Check your MySQL connection settings and ensure the schema is set up.
-   **Class not found:**  
    Make sure you compile with the correct classpath (`lib/*` for JARs).
-   **Input issues:**  
    Ensure your terminal is focused when running CLI programs.

---

## 9. Customization

-   **Add more roles:**  
    Create new CLI classes in `cli/` and corresponding DAOs in `sys/`.
-   **Change database:**  
    Update `DB.java` for different database engines or credentials.
-   **Add features:**  
    Extend forms, DAOs, and CLI classes as needed.

---

## 10. Additional Resources

-   [VS Code Java Documentation](https://code.visualstudio.com/docs/java/java-tutorial)
-   [MySQL Connector/J Documentation](https://dev.mysql.com/doc/connector-j/en/)
-   [JLine Documentation](https://github.com/jline/jline3)

---

**Enjoy exploring and roleplaying with your Banking System project!**  
If you have any questions or need help, check the comments in the code or the error messages.
