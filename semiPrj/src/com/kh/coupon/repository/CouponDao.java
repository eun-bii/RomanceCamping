package com.kh.coupon.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.common.JDBCTemplate;
import com.kh.coupon.vo.CouponVo;

public class CouponDao {

	//유저 쿠폰리스트 조회하기
	public List<CouponVo> selectCouponList(Connection conn, String no) {
		//기본작업
		List<CouponVo> couponList = new ArrayList<CouponVo>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//sql 작성하기
		String sql = "SELECT C.NAME AS NAME , C.DISCOUNT AS COST, C.CODE AS CODE FROM USER_COUPON_LIST U JOIN COUPON_LIST C ON U.C_CODE = C.CODE "
				+ "WHERE U.U_STATUS = 'N' AND U.USER_NO = ?";
		System.out.println("쿠폰조회 sql :::" + sql);
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("NAME");
				String cost = rs.getString("COST");
				String code = rs.getString("CODE");
				CouponVo vo = new CouponVo();
				vo.setCouponName(name);
				vo.setCouponDiscount(cost);
				vo.setCouponCode(code);
				couponList.add(vo);
			}		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		return couponList;
	}
	//유저 쿠폰 사용
	public int useCoupon(Connection conn, String no, String couponCode) {	
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "UPDATE ( "
				+ "SELECT U.U_STATUS FROM COUPON_LIST C JOIN USER_COUPON_LIST U ON C.CODE = U.C_CODE WHERE U.U_STATUS = 'N' AND U.USER_NO = ? AND C. NAME = ? "
				+ ") SET U_STATUS = 'Y'";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, no);
			pstmt.setString(2, couponCode);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
}
