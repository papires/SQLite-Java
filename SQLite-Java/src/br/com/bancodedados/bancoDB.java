package br.com.bancodedados;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class bancoDB {
	private String localDB = "test.db";
	private Connection c = null;
	Statement stmt = null;
	String url = "jdbc:sqlite:" + localDB;

	public bancoDB() {
		File file = new File(localDB);
		if (file.exists()) {
			System.out.println("Banco de dados já criado.");
		} else {
			createDB();
			createTabelaUser();
		}
	}

	private void createDB() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection(url);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

	private void createTabelaUser() {
		try {
			stmt = c.createStatement();
			String sql = "CREATE TABLE USER (ID INTEGER PRIMARY KEY  AUTOINCREMENT, " 
					+ " NAME TEXT NOT NULL, "
					+ " EMAIL CHAR(50)" + ");";
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Criado a tabela User.");
	}

	private Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public int getSizeTableUser() {
		String sql = "SELECT COUNT(ID) FROM USER";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -2;
		}
		return -1;
	}

	public boolean insertUSer(String nome, String email) {
		String sql = "INSERT INTO USER(NAME,EMAIL) VALUES(?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nome);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

}
