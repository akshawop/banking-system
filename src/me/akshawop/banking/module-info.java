module me.akshawop.banking {
    exports me.akshawop.banking.sys;
    exports me.akshawop.banking.cli;
    exports me.akshawop.banking.util;
    exports me.akshawop.banking.inputmodules.forms;

    requires transitive java.sql;
}