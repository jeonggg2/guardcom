package gcom.controller.front.admin.ax.Do;

import com.google.gson.Gson;
import gcom.Model.ServerAuditModel;
import gcom.common.util.ConfigInfo;
import gcom.controller.action.admin.insertAdminAction;
import gcom.controller.action.admin.updateAdminAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/admin/system/updatepwpattern")
public class axPwPatternDoController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public axPwPatternDoController() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpSession session = httpReq.getSession(false);

        HashMap<String, Object> data = new HashMap<String, Object>();
        HashMap<String, Object> result = null;

        int minCnt = Integer.parseInt(request.getParameter("min_cnt"));
        int maxCnt = Integer.parseInt(request.getParameter("max_cnt"));

        // 최대자릿수가 최소자릿수보다 작을때 체크
        if (minCnt <= maxCnt) {
            data.put("upper_char_enabled", request.getParameter("upper_char_enabled"));
            data.put("number_enabled", request.getParameter("number_enabled"));
            data.put("special_char_enabled", request.getParameter("special_char_enabled"));
            data.put("equal_char_enabled", request.getParameter("equal_char_enabled"));
            data.put("consecutive_char_enabled", request.getParameter("consecutive_char_enabled"));
            data.put("include_id_char_enabled", request.getParameter("include_id_char_enabled"));
            data.put("min_cnt", request.getParameter("min_cnt"));
            data.put("max_cnt", request.getParameter("max_cnt"));
        } else {
            data.put("returnCode", ConfigInfo.PW_COUNT_ERROR);
            data.putAll(data);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(new Gson().toJson(data));
            return;
        }



            updateAdminAction action = new updateAdminAction();

            result = action.updatePwPatternInfo(data);

            ServerAuditModel model = new ServerAuditModel();
            model.setAdminId((String) session.getAttribute("user_id"));
            model.setActionId(2070);
            model.setWorkIp(httpReq.getRemoteAddr());
            model.setDescription("시스템 정책 수정");
            model.setParameter("시스템 번호 : " + request.getParameter("system_no") + ", 데이터 : " + request.getParameter("value"));
            model.setStatus(result.get("returnCode").equals(ConfigInfo.RETURN_CODE_SUCCESS) ? "성공" : "실패");

            insertAdminAction aud = new insertAdminAction();
            aud.insertServeriAudit(model);

            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(new Gson().toJson(result));

    }
}
