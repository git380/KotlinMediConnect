package com.kotlinmediconnect

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/WelcomeServlet")
class WelcomeServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
        request.getRequestDispatcher("/WEB-INF/jsp/welcome.jsp").forward(request, response)

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        when (request.session.getAttribute("emprole") as Int) {
            0 -> request.getRequestDispatcher("/WEB-INF/jsp/administrator.jsp").forward(request, response)
            1 -> request.getRequestDispatcher("/WEB-INF/jsp/reception.jsp").forward(request, response)
            2 -> request.getRequestDispatcher("/WEB-INF/jsp/physician.jsp").forward(request, response)
            else -> {
                response.contentType = "text/html; charset=UTF-8"
                response.writer.println("ロールエラー")
            }
        }
    }
}