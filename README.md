# 🏛️ Banking System Project Documentation

Welcome to the **Banking System** project! This guide will help you set up, understand, and run the project on your local machine using Visual Studio Code or any Java IDE. 🚀

---

## 1. 🗂 Project Structure

```
Banking System/
│
├── src/                        # ☕ Java source code
│   └── me/akshawop/banking/
│        ├── cli/               # 🖥 Command Line Interfaces (CLIs)
│        │    ├── AccountCLI.java
│        │    ├── ATM.java
│        │    ├── BankCLI.java
│        │    ├── BranchCLI.java
│        │    ├── CustomerCLI.java
│        │    └── inputmodules/        # 📝 Forms and Inputs for User interaction
│        │         ├── NewAddress.java
│        │         ├── UpdateAddress.java
│        │         └── forms/
│        │              ├── CreateBankForm.java
│        │              ├── CreateBranchForm.java
│        │              ├── NewAccountForm.java
│        │              ├── NewCustomerForm.java
│        │              ├── UpdateBankForm.java
│        │              └── UpdateCustomerForm.java
│        ├── customtype/               # 📦 Custom data types
│        │    └── Address.java
│        ├── sql/                       # 🗃️ SQL query helpers
│        │    └── SQLQueries.java
│        ├── sys/                       # ⚙️ Core system classes & DAOs
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
│        │    ├── DB.java               # 🔌 Database connection config
│        │    ├── Transaction.java
│        │    ├── TransactionDAO.java
│        │    ├── TransactionMode.java
│        │    └── TransactionType.java
│        └── util/                      # 🛠️ Utility classes & exceptions
│             ├── AccountBlockedException.java
│             ├── CardBlockedException.java
│             ├── ClearScreen.java
│             ├── IncorrectPinException.java
│             ├── InputChecker.java
│             ├── InputPIN.java
│             └── NotEnoughBalanceException.java
│
├── lib/                        # 📦 External libraries (JARs)
│   ├── jline-3.30.4.jar
│   └── mysql-connector-j-9.3.0.jar
│
├── sql/                        # 🗄 SQL schema for database setup
│   └── schema.sql
│
├── bin/                        # 🏗️ Compiled Java classes (after build)
│
├── windows/                    # 💻 Windows scripts for compiling and running
│   ├── atm.ps1
│   ├── bank.ps1
│   ├── branch.ps1
│   └── compile.ps1
│
├── linux/                      # 🐧 Linux / macOS shell scripts for compiling and running
│   ├── atm.sh
│   ├── bank.sh
│   ├── branch.sh
│   └── compile.sh
│
├── .gitignore 🚫
├── .gitattributes ⚙️
└── README.md 📘
```

---

## 2. ⚙️ Prerequisites

-   **Java JDK 17 or higher** installed ([Download here](https://adoptium.net/))☕
-   **MySQL Server** 🗄 running locally (or update connection settings in `DB.java`)
-   **Visual Studio Code** 💻 (recommended) or any Java IDE
-   **VS Code Extensions:**

    -   Extension Pack for Java 📦
    -   Java Dependency Viewer 🔍
    -   Code Runner (optional) ✅

---

## 3. 🏗 Setting Up the Database

1. **Start your MySQL server.** 🔌
2. **Open `sql/schema.sql`** and run its contents in your MySQL client 📄✅

---

## 4. 🛠 Configuring the Project

-   **Dependencies:** MySQL Connector/J 🗄 and JLine ✍️ (in `lib/`)
-   **Database Connection 🔑:** Edit `src/me/akshawop/banking/sys/DB.java` to match your MySQL username, password, and database name.

---

## 5. 🏃 Building and Running

### **A. Compile the Project**

#### **Windows (PowerShell)** 💻

```sh
./windows/compile.ps1
```

#### **Linux / macOS (Shell)** 🐧

```sh
./linux/compile.sh
```

#### **Manual Compilation** 🛠️

```sh
javac -cp "lib/*" -d bin src/me/akshawop/banking/**/*.java
```

---

### **B. Run CLI Applications** 🖥️

Each CLI file represents a different role in the banking system.

#### 1. **BankCLI** 🏦👔

-   **Purpose:** Manage banks and branches
-   **Main File:** `src/me/akshawop/banking/cli/BankCLI.java`
-   **Run:**

    -   Windows 💻:
        ```sh
        ./windows/bank.ps1
        ```
    -   Linux/macOS 🐧:
        ```sh
        ./linux/bank.sh
        ```
    -   Manual:
        ```sh
        # windows
        java -cp "bin;lib/*" me.akshawop.banking.cli.BankCLI
        ```
        ```sh
        # linux / macOS
        java -cp "bin:lib/*" me.akshawop.banking.cli.BankCLI
        ```

-   **What it does:**
    -   Allows creation and management of banks and branches 🏢
    -   Uses `BankDAO` for database operations 🗄
    -   Helper forms in `inputmodules/forms/` for user input ✍️

#### 2. **BranchCLI** 🏛️👔

-   **Purpose:** Manage customers, accounts, and branch operations
-   **Main File:** `src/me/akshawop/banking/cli/BranchCLI.java`
-   **Run:**

    -   Windows 💻:
        ```sh
        ./windows/branch.ps1
        ```
    -   Linux/macOS 🐧:
        ```sh
        ./linux/branch.sh
        ```
    -   Manual:
        ```sh
        # windows
        java -cp "bin;lib/*" me.akshawop.banking.cli.BranchCLI
        ```
        ```sh
        # linux / macOS
        java -cp "bin:lib/*" me.akshawop.banking.cli.BranchCLI
        ```

-   **What it does:**
    -   Allows branch officials to add/update customers 👤, manage accounts 💰, issue cards 💳, and handle transactions 💸
    -   Uses `BranchDAO` for branch-specific database operations 🗄
    -   Helper forms ✍️ and utility classes for input validation and screen clearing 🧹✅

#### 3. **ATM** 🏧👤

-   **Purpose:** Simulate ATM operations
-   **Main File:** `src/me/akshawop/banking/cli/ATM.java`
-   **Run:**

    -   Windows 💻:
        ```sh
        ./windows/atm.ps1
        ```
    -   Linux/macOS 🐧:
        ```sh
        ./linux/atm.sh
        ```
    -   Manual:
        ```sh
        # windows
        java -cp "bin;lib/*" me.akshawop.banking.cli.ATM
        ```
        ```sh
        # linux / macOS
        java -cp "bin:lib/*" me.akshawop.banking.cli.ATM
        ```

-   **What it does:**
    -   Allows customers to log in 🔒, check balances 💰, withdraw/deposit money 💸, and view transactions 🧾
    -   Uses `AccountDAO`, `TransactionDAO`, and related classes for account and transaction management 🗄

---

## 6. ⚙ How the System Works

### **CLI Classes (User Interfaces)** 🖥️

-   **BankCLI:** 🏦 manages banks and branches 🏢, uses BankDAO 🗄, forms ✍️
-   **BranchCLI:** 🏛️ manages customers 👤 and accounts 💰, uses BranchDAO 🗄, forms ✍️ and utils 🧹✅
-   **ATM:** 🏧 customer interface, interacts with AccountDAO 💰, TransactionDAO 💸

### **DAO Classes** 🗄

-   BankDAO 🏦, BranchDAO 🏛️, AccountDAO 💰, CustomerDAO 👤, TransactionDAO 💸, CardDAO 💳

### **Helper Classes** 🛠️

-   **Forms ✍️(`inputmodules/forms/`):**  
    Collect and validate user input for creating/updating entities.
-   **Utils 🧹(`util/`):**

    -   `ClearScreen`: Clears the console for better UX.
    -   `InputChecker`: Validates user input.

-   **Custom exceptions for error handling** ⚠️❌

---

## 7. 🎭 Roleplay Usage

-   Bank Official 🏦 → BankCLI
-   Branch Official 🏛️ → BranchCLI
-   Customer 🏧 → ATM

---

## 8. 🛑 Troubleshooting

-   Database errors 🗄
    Check your MySQL connection settings and ensure the schema is set up.
-   Class not found 📦
    Make sure you compile with the correct classpath (`lib/*` for JARs).
-   Input issues ⚠️
    Ensure your terminal is focused when running CLI programs.

---

## 9. 🎨 Customization

-   Add roles ➕
    Create new CLI classes in `cli/` and corresponding DAOs in `sys/`.
-   Change database 🔑
    Update `DB.java` for different database engines or credentials.
-   Add features ✨
    Extend forms, DAOs, and CLI classes as needed.

---

## 10. 📚 Additional Resources

-   [VS Code Java Docs](https://code.visualstudio.com/docs/java/java-tutorial) 💻
-   [MySQL Connector/J Docs](https://dev.mysql.com/doc/connector-j/en/) 🗄
-   [JLine Docs](https://github.com/jline/jline3) ✍️

---

### 🎉**Enjoy exploring and roleplaying with your Banking System project!**

### If you have any questions or need help, check the comments in the code or the error messages.📝
