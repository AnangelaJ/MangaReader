package acfb.org;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import acfb.org.json.DBConnect;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		User u = new User();
		JSONObject jsonResponse = new JSONObject();
		
		u.setUser_email(request.getParameter("correo"));
		u.setUser_name(request.getParameter("nombre"));
		u.setUser_password(request.getParameter("contrasena"));
		u.setUser_role(2);
		u.setUser_username(request.getParameter("usuario"));
		u.setUser_time(request.getParameter("fecha"));
		DBConnect conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
		try {
			conn.Action("INSERT INTO usuario (tipo_id, user_contrasena, user_username, user_nombre, user_creation_time, user_email) VALUES("+u.getUser_role()+",'"+u.getUser_password()+"','"+u.getUser_username()+"','"+u.getUser_name()+"','"+u.getUser_time()+"','"+u.getUser_email()+"');");
			jsonResponse.put("status", "200");
			conn.Query("SELECT * FROM usuario WHERE user_contrasena='"+u.getUser_password()+"' AND user_username='"+u.getUser_username()+"';");
			JSONObject resp = new JSONObject();
			resp = conn.getJsonResult().getJSONObject(0);
			Object uid = resp.get("usuario_id");
			String ruta = "C:/develop/tomcat/webapps/MangaReaderJ/WebContent/MangaImages/u"+uid;
			File folder = new File(ruta);
			if(!folder.exists()) {
				folder.mkdir();
			}
		}catch(Exception e) {
			jsonResponse.put("status", "404");
			e.printStackTrace();
		}
		out.print(jsonResponse);
	}

}