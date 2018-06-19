package com.example.mongoapp.jdbc;


import java.sql.*;

public class OracleJdbc {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        DriverManager.registerDriver( oracle.jdbc.driver.OracleDriver);
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:my","SCOTT","scott");
        System.out.println(con);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("select * from emp where rownum<=1 for update");
        while(rs.next()){
            System.out.println(rs.getLong(1));
        }
    }
}
