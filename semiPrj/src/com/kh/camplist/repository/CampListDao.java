package com.kh.camplist.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kh.camplist.hashtag.vo.HashTagVo;
import com.kh.camplist.campinfo.vo.CampInfoVo;
import com.kh.camplist.theme.vo.ThemeVo;
import com.kh.common.PageVo;

import static com.kh.common.JDBCTemplate.*;

public class CampListDao {
	
	//캠핑장 정보 리스트(카드) 총 갯수
	public int getCount(Connection conn) {

		String sql = "SELECT COUNT(CAMP_CODE) AS COUNT FROM CAMP_INFO WHERE CAMP_STATUS = 'Y'";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
			close(rs);
		}
		
		return count;
	}

	//현재 페이지에 보여질 캠핑장 리스트 조회
	public List<CampInfoVo> selectList(Connection conn, PageVo pageVo ) {

		String sql = "SELECT * FROM ( SELECT ROWNUM AS RNUM, CI.* FROM (SELECT C.CAMP_CODE, C.CAMP_NAME, C.CAMP_INTRO, C.CAMP_IMGPATH FROM CAMP_INFO C WHERE C.CAMP_STATUS = 'Y') CI ) WHERE RNUM BETWEEN ? AND ?";
		
		PreparedStatement pstmt = null;
		List<CampInfoVo> list = new ArrayList<CampInfoVo>();
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			int start = (pageVo.getCurrentPage()-1) * pageVo.getListLimit() + 1;
			int end = start + pageVo.getListLimit() - 1;
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String campCode = rs.getString("CAMP_CODE");
				String campName = rs.getString("CAMP_NAME");
				String campIntro = rs.getString("CAMP_INTRO");
				String campImgpath = rs.getString("CAMP_IMGPATH");
				
				CampInfoVo vo = new CampInfoVo();
				vo.setCampCode(campCode);
				vo.setCampName(campName);
				vo.setCampIntro(campIntro);
				vo.setCampImgpath(campImgpath);
				
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}

	//테마 리스트 조회
	public List<ThemeVo> selectTheme(Connection conn) {
		
		String sql = "SELECT THEME_CODE, THEME_NAME FROM THEME";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ThemeVo> list = new ArrayList<ThemeVo>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new ThemeVo(
							rs.getString("THEME_CODE"),
							rs.getString("THEME_NAME")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}
	
	//해시태그 리스트 조회
	public List<HashTagVo> selectHashTag(Connection conn) {
		
		String sql = "SELECT HT_CODE, HT_NAME FROM HASHTAG";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<HashTagVo> list = new ArrayList<HashTagVo>();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new HashTagVo(
							rs.getString("HT_CODE"),
							rs.getString("HT_NAME")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return list;
	}
	
	


	

	
	
	
	
	
}