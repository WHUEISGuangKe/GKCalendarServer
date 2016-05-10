package com.guangke.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.guangke.bean.UserBean;
import com.guangke.dao.UserDao;
import com.guangke.util.JdbcUtil;

public class UserDaoImpl implements UserDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	@Override
	public boolean register(UserBean user) {
		String sql = "INSERT INTO user(username,password) VALUES (?,?)";
		
		QueryRunner runner = JdbcUtil.getRunner();
		try {
			runner.update(sql, user.getUserName(), user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public UserBean findByNameAndPwd(UserBean user) {
		String sql = "SELECT * FROM user where username=? and password=?";
		UserBean userEntity = null;
		try {
			conn = JdbcUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				userEntity = new UserBean();
				userEntity.setUserName(rs.getString("username"));
				userEntity.setPassword(rs.getString("password"));
			}
			return userEntity;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
	}

	@Override
	public boolean userExists(String name) {
		String sql = "select * from user where username=?";
		try {
			conn = JdbcUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String username = rs.getString("username");
				if (username != null && username.length() > 0) {
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
	}
}
