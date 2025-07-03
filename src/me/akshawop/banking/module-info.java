module me.akshawop.banking {
    exports me.akshawop.banking.sys;
    exports me.akshawop.banking.cli;
    exports me.akshawop.banking.customtype;
    exports me.akshawop.banking.inputmodules.forms;

    requires transitive java.sql;
}