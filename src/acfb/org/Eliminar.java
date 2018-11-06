package acfb.org;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import acfb.org.json.DBConnect;

/**
 * Servlet implementation class Eliminar
 */
@WebServlet("/Eliminar")
public class Eliminar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Eliminar() {
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
		JSONObject json = new JSONObject();
		String id = request.getParameter("Id");
		String options = request.getParameter("options").trim();
		DBConnect conn = null;
		try{
			conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
			if(options.equals("1")){ //1 para mangas
				conn.Query("SELECT * FROM mangas WHERE manga_id= " + id);
				if(conn.getNumRows() == 1){
					conn.Action("UPDATE manga SET manga_status= false WHERE manga_id=" + id);
					conn.Action("UPDATE capitulo SET status= false WHERE manga_id=" + id);
					json.put("status", "200");
				}else{
					json.put("status", "403");
				}
			}else if(options.equals("2")){ //2 para capitulos
				System.out.println("ID DEL CAPITULO: " + id);
				conn.Query("SELECT * FROM capitulo WHERE capitulo_id= " + id);
				if(conn.getNumRows() == 1){
					conn.Action("UPDATE capitulo SET status= false WHERE capitulo_id=" + id);
					json.put("status", "200");
				}else{
					json.put("status", "403");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			json.put("status", "403");
		}
		out.print(json);
	}

}
