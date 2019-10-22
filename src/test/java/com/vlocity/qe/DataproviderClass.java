package com.vlocity.qe;

import org.testng.annotations.DataProvider;

public class DataproviderClass {

    /* Reg 1 : Use TestNg @DataProvider(s) for every @Test method you write
 This is dataprovider.
 If languages dont work, can use UFT-8.
 Works for me from terminal though*/
    @DataProvider(name = "data-provider")
    public static Object[][] dataProviderMethod() {
        return new Object[][]{
                {"English"},
                {"日本語"},
                {"Русский"},
                {"Italiano"},
                {"Português"},
                {"Polski"},
                {"中文"},
                {"Français"},
                {"Deutsch"},
                {"Español"}};
    }
}
