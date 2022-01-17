package gcom.common.util;

import gc.db.DbCon;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PwCheck {

    DataSource ds;
    Connection con=null;
    PreparedStatement pstmt=null;
    ResultSet rs = null;

    public PwCheck(){
//        try{
//            Context initCtx = new InitialContext();
//            Context envCtx = (Context)initCtx.lookup("java:comp/env");
//            ds=(DataSource)envCtx.lookup("jdbc/mysql");
//        }catch(Exception ex ){
//            ex.printStackTrace();
//        }
    }

    public Boolean Check(String inputPw, String id) {
        Boolean result = false;

        String sql =
                "SELECT "
                        + "upper_char_enabled, "
                        + "number_enabled, "
                        + "special_char_enabled, "
                        + "equal_char_enabled, "
                        + "consecutive_char_enabled, "
                        + "include_id_char_enabled, "
                        + "min_cnt,"
                        + "max_cnt "
                        + "FROM pw_pattern_info ";

        try {
            con = DbCon.getConnection();
            pstmt=con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                int upper = rs.getInt("upper_char_enabled");
                int num = rs.getInt("number_enabled");
                int special = rs.getInt("special_char_enabled");
                int equal = rs.getInt("equal_char_enabled");
                int consecutive = rs.getInt("consecutive_char_enabled");
                int include = rs.getInt("include_id_char_enabled");
                int minCnt = rs.getInt("min_cnt");
                int maxCnt = rs.getInt("max_cnt");

                Matcher match;

                // 최소 자릿수와 최대 자릿수 사이 값인지 확인
                if (inputPw.length() >= minCnt && inputPw.length() <= maxCnt) {

                    // 3자리 이상 동일한 문자(숫자) 체크
                    if (equal == 1) {
                        Pattern pEqual = Pattern.compile("(\\w)\\1\\1");
                        match = pEqual.matcher(inputPw);
                        if (match.find()) {
                            return false;
                        } else {
                            result = true;
                        }
                    }

                    // 3자리 이상 연속된 문자(숫자) 체크
                    if (consecutive == 1) {
                        int o = 0;
                        int d = 0;
                        int p = 0;
                        int n = 0;
                        int limit = 3;

                        for(int i=0; i<inputPw.length(); i++) {
                            char tempVal = inputPw.charAt(i);
                            if(i > 0 && (p = o - tempVal) > -2 && (n = p == d ? n + 1 :0) > limit -3) {
                                return false;
                            }
                            d = p;
                            o = tempVal;
                        }
                        result = true;
                    }

                    // 아이디와 동일한 문자 4자리 이상 포함 체크
                    if (include == 1) {
                        for(int i=1; i<inputPw.length()-3; i++) {
                            if(id.contains(inputPw.substring(i, i+3))) {
                                return false;
                            }
                        }

                        result = true;
                    }

                    // 대문자가 필수로 들어가야할때
                    if (upper == 1) {
                        Pattern pAlphabetUp = Pattern.compile("[A-Z]");
                        match = pAlphabetUp.matcher(inputPw);
                        if (match.find()){
                            result = true ;
                        } else {
                            return false;
                        }
                    }

                    // 숫자가 필수로 들어가야할때
                    if (num == 1) {
                        Pattern pNumber = Pattern.compile("[0-9]");
                        match = pNumber.matcher(inputPw);
                        if (match.find()){
                            result = true ;
                        } else {
                            return false;
                        }
                    }

                    // 특수문자가 필수로 들어가야할때
                    if (special == 1) {
                        Pattern pSpecialChar = Pattern.compile("\\p{Punct}");
                        match = pSpecialChar.matcher(inputPw);
                        if (match.find()){
                            result = true ;
                        } else {
                            return false;
                        }
                    }

                    // 필수 조건이 없을때
                    if (upper == 0 && num == 0 && special == 0) {
                        result = true;
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } finally {

            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
