package com.kotlinmediconnect.login

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet("/LogoutServlet")
class LogoutServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.session.invalidate()
        request.getRequestDispatcher("/WEB-INF/jsp/login/logout.jsp").forward(request, response)
    }
}