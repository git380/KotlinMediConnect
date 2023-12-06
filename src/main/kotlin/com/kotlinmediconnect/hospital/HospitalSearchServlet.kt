package com.kotlinmediconnect.hospital

import com.kotlinmediconnect.model.Hospital
import java.io.IOException
import java.sql.DriverManager
import java.sql.SQLException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/HospitalSearchServlet")
class HospitalSearchServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) =
        request.getRequestDispatcher("/WEB-INF/jsp/hospital/H103/hospitalSearch.jsp").forward(request, response)

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        request.characterEncoding = "UTF-8"
        request.setAttribute("hospitals", searchHospitals(request.getParameter("address")))
        request.getRequestDispatcher("/WEB-INF/jsp/hospital/H103/hospitalSearchResult.jsp").forward(request, response)
    }

    private fun searchHospitals(address: String): List<Hospital> {
        val hospitals: MutableList<Hospital> = ArrayList()
        try {
            DriverManager.getConnection("jdbc:mysql://localhost/r4a105", "r4a105", "password").use { connection ->
                val preparedStatement = connection.prepareStatement("SELECT * FROM tabyouin WHERE tabyouinaddres LIKE ?")
                preparedStatement.setString(1, "%$address%")
                val resultSet = preparedStatement.executeQuery()
                while (resultSet.next()) {
                    hospitals.add(Hospital(
                        resultSet.getString("tabyouinid"),
                        resultSet.getString("tabyouinmei"),
                        resultSet.getString("tabyouinaddres"),
                        resultSet.getString("tabyouintel"),
                        resultSet.getInt("tabyouinshihonkin"),
                        resultSet.getInt("kyukyu")
                    ))
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return hospitals
    }
}
