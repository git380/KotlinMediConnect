package com.kotlinmediconnect.patient

import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/PatientUpdateServlet")
class PatientUpdateServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        request.setAttribute("patId", request.getParameter("patId"))
        request.setAttribute("patFname", request.getParameter("patFname"))
        request.setAttribute("patLname", request.getParameter("patLname"))
        request.setAttribute("hokenmei", request.getParameter("hokenmei"))
        request.setAttribute("hokenexp", request.getParameter("hokenexp"))
        request.getRequestDispatcher("/WEB-INF/jsp/patient/P102/patientUpdate.jsp").forward(request, response)
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        request.characterEncoding = "UTF-8"
        val hokenexp = request.getParameter("hokenexp")
        if (hokenexp == null || hokenexp == "") {
            response.contentType = "text/html; charset=UTF-8"
            response.writer.println("日付を入力してください。")
        } else {
            val expirationDate = LocalDate.parse(hokenexp, formatter)
            val oldExpirationDate = LocalDate.parse(request.getParameter("oldhokenexp"), formatter)
            if (expirationDate.isAfter(oldExpirationDate)) {
                try {
                    DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                        val preparedStatement = connection.prepareStatement("UPDATE patient SET hokenmei = ?, hokenexp = ? WHERE patid = ?")
                        preparedStatement.setString(1, request.getParameter("hokenmei"))
                        preparedStatement.setString(2, hokenexp)
                        preparedStatement.setString(3, request.getParameter("patId"))
                        preparedStatement.executeUpdate()
                    }
                } catch (e: SQLException) {
                    e.printStackTrace()
                }
                request.getRequestDispatcher("/WEB-INF/jsp/patient/P102/changeOK.jsp").forward(request, response)
            } else {
                response.contentType = "text/html; charset=UTF-8"
                response.writer.println("新しい日付を入力してください。")
            }
        }
    }
}