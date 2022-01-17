package gcom.controller.front.admin.ax.list;

import com.google.gson.Gson;
import gcom.controller.action.admin.getAdminAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/ax/system/pwpatternlist")
public class axPwPatternListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public axPwPatternListController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        getAdminAction action = new getAdminAction();

        HashMap<String, Object> data = action.getPwPatternList(map);

        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(new Gson().toJson(data));

    }
}
