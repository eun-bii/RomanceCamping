package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.service.MemberService;
import com.kh.member.vo.MemberVo;

@WebServlet(urlPatterns = "/member/changePwd")
public class ChangePwdController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		String originPwd = req.getParameter("pwd");
		String newPwd = req.getParameter("newPwd");
		String newPwdCheck = req.getParameter("newPwdCheck");
		String id = ((MemberVo)req.getSession().getAttribute("loginMember")).getId();
		
//		int result = new MemberService().changePwd(memberId, memberPwd, memberPwdNew, memberPwdNew2);
//		
//		if(result == 1) {
//			req.getSession().setAttribute("alertMsg", "비밀번호 변경 성공!");
//			resp.sendRedirect("/semi/member/myPage");
//		} else {
//			req.setAttribute("errorMsg", "비밀번호 변경 실패..");
//			req.getRequestDispatcher("/views/error/errorPage.jsp").forward(req, resp);
//		}
		
	}
	
}