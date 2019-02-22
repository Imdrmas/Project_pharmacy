package com.issamdrmas.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.imdrmas.entities.Produits;
import org.imdrmas.entities.Users;

public class GetConnecation extends GetContextSql {

	public static Connection getMyConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String URL = "jdbc:mysql://localhost:3306/springbootdb" + "?useUnicode=true&characterEncoding=UTF-8";
			con = DriverManager.getConnection(URL, "", "");
			System.out.println("Connected");

		} catch (Exception e) {
			System.out.println("Unconnected" + e);
		}
		return con;
	}

	public static void closeDb() {
		try {
			if (resultSet != null) {
				resultSet.close();
				resultSet = null;
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (preparedStatement != null) {
				preparedStatement.close();
				preparedStatement = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
      }
	
	public static List<Produits> getAllProducts() {
		List<Produits> list = new ArrayList<Produits>();
		try {
			String strSelect = "SELECT * FROM produits";
			con = GetConnecation.getMyConnection();
			statement = con.createStatement();
			resultSet = statement.executeQuery(strSelect);
			while (resultSet.next()) {
				Produits pro = new Produits();
				pro.setDesignation(resultSet.getString("designation"));
				pro.setPrix(resultSet.getDouble("prix"));
				list.add(pro);
			}
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}
	

	
	public static List<Users> getUserList() throws Exception {
		List<Users> list = new ArrayList<Users>();

		con = getMyConnection();
		String strSelect = "SELECT * FROM user";
		statement = con.createStatement();
		resultSet = statement.executeQuery(strSelect);

		while (resultSet.next()) {
			Users u = new Users();
			u.setId(resultSet.getInt("id"));
			u.setName(resultSet.getString("name"));
			u.setEmail(resultSet.getString("email"));
			u.setPassword(resultSet.getString("password"));
			list.add(u);
		    }
           return list;
       }
	
	
}
