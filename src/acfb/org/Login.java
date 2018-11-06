package acfb.org;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import acfb.org.json.DBConnect;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		JSONObject json = new JSONObject();	
		System.out.println("Hay Sesion: " + session.isNew());
		if(session.isNew()) {
			if(session.getAttribute("usuario") == null){
				System.out.println("Atributo: " + session.getAttribute("usuario"));
				System.out.println("sesion no iniciada");
				json.put("status", "403").put("res", "session not started").put("value", "");
				session.invalidate();
			}
		}
		else if(session.getAttribute("usuario") != null) {
			System.out.println("Atributo: " + session.getAttribute("usuario"));
			System.out.println("Sesion iniciada");
			System.out.println("usuario: "+session.getAttribute("usuario"));
			json.put("status", "200").put("rol", session.getAttribute("tipo")).put("usuario", session.getAttribute("usuario")).put("idUser", session.getAttribute("userId"));
		}
		out.print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Entro en login");
		HttpSession sesion = request.getSession();
		JSONObject jsonResponse = new JSONObject();
        String user, pass;
        PrintWriter out = response.getWriter();
        DBConnect conn = null;
        int userId;
        
        user = request.getParameter("usuario");
        pass = request.getParameter("contrasena");
        
        //Abro la base de datos y chequeo el username y la contraseña
        try {
	        conn = Connection.getConnection("5432", "postgres", "anangela", "MangaReader");
	        conn.Query("SELECT * FROM usuario WHERE user_contrasena= '"+pass+"' AND user_username= '"+user+"'");
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        //Compruebo que se encontró alguna coincidencia
        
        if(conn.getNumRows() == 1){
        	JSONObject result = new JSONObject();
        	result = conn.getJsonResult().getJSONObject(0);
        	System.out.println("usuario_id: "+result.get("usuario_id"));
            jsonResponse.put("status", 200).put("res", "session stored");
            sesion.setAttribute("usuario", result.get("user_nombre"));
            sesion.setAttribute("tipo", result.get("tipo_id"));
            sesion.setAttribute("userId", result.get("usuario_id"));
        }else{
        	jsonResponse.put("status", 404).put("res", "session stored");
        	sesion.invalidate();
        } 
        out.println(jsonResponse);
	}
}
