package gcom.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * StringUtil 은 String 관련 Utility 클래스이다.
 *
 * @author Jeon gil
 * @since 1.0
 */
public class StringUtil {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String KOR_CHARSET = "EUC-KR";
    private static final String ENG_CHARSET = "ISO-8859-1";

    /**
     *
     * Ajax 엔코딩
     *
     * @param data String value
     * @return String
     */
    public static String ajaxEncoding(String data) {
        String korean = null;

        if (data == null ) {
            return null; 
        }

        try {
            korean = new String(data.getBytes(ENG_CHARSET), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            korean = new String(data);
        }

        return korean;
    }

    /**
     *
     * eng -> kor
     *
     * @param english 영어
     * @return String
     */
    public static String eng2Kor(String english) {
        String korean = null;

        if (english == null ) {
            return null;
        }

        try {
            korean = new String(english.getBytes(ENG_CHARSET), KOR_CHARSET);
        } catch (UnsupportedEncodingException e) {
            korean = new String(english);
        }

        return korean;
    }

    /**
     *
     * kor -> eng
     *
     * @param korean 한글
     * @return String
     */
    public static String kor2Eng(String korean) {
        String english = null;

        if (korean == null ) {
            return null;
        }

        try {
            english = new String(korean.getBytes(KOR_CHARSET), ENG_CHARSET);
        } catch (UnsupportedEncodingException e) {
            english = new String(korean);
        }

        return english;
    }

    /**
     *
     * 대문자로 변환
     *
     * @param str String value
     * @param from 시작 index
     * @param to 끝 index
     * @return String
     */
    public static String toUpperCase(String str, int from, int to) {
        String prefix = str.substring(0, from);
        String suffix = str.substring(to+1);
        String temp = str.substring(from, to+1);
        return prefix + temp.toUpperCase() + suffix;
    }

    /**
     *
     * merge
     *
     * @param strArr String 배열
     * @return String
     */
    public static final String merge(String[] strArr) {
        if (strArr == null || strArr.length == 0)
            return "";

        StringBuffer result = new StringBuffer("");
        result.append("'").append(strArr[0]).append("'");
        for (int i = 1; i < strArr.length; i++) {
            result.append(", '");
            result.append(strArr[i]);
            result.append("'");
        }

        return result.toString();
    }

    /**
     *
     * makeCode
     *
     * @param maxCode
     * @param defaultCode
     * @return String
     * @throws Exception
     */
    public static String makeCode(String maxCode, String defaultCode) throws Exception {
        if (maxCode == null) {
            return defaultCode;
        }else if (maxCode.length() < 14) {
            throw new Exception("Invalid code string, Code must be length 14 : " + maxCode);
        }

        String prefix = maxCode.substring(0, 2);
        String serial = maxCode.substring(2);

        return (prefix + String.valueOf( (Long.parseLong(serial) + 1) ) );
    }

    /**
     *
     * String -> int
     *
     * @param numStr String 숫자
     * @param defaultValue 디폴트 값
     * @return String
     */
    public static int parseInt(String numStr, int defaultValue) {
        return (isNull(numStr)) ? defaultValue : Integer.parseInt(numStr);
    }
    public static int parseInt(Object numStr, int defaultValue) {
        return (isNull(numStr)) ? defaultValue : Integer.parseInt( String.valueOf(numStr) );
    }
    /**
     *
     * 0 추가
     *
     * @param number 숫자
     * @param order 순서
     * @return String
     */
    public static String addZero(int number, int order) {
        return addZero(Integer.toString(number), order);
    }

    /**
     *
     * 0 추가
     *
     * @param xxx 숫자
     * @param x 순서
     * @return String
     */
    public static String addZero(String xxx, int x) {
        StringBuffer yyy = new StringBuffer("");
        if (!(xxx == null || xxx.trim().equals(""))) {
            if(xxx.length() < x){
                for(int a = 0; a < x - xxx.length() ; a++){
                    yyy.append("0");
                }
                yyy.append(xxx);
            }
            else if(xxx.length() > x){
                yyy.append(xxx.substring(x-xxx.length(),x));
            }
            else {
                yyy.append(xxx);
            }
        }

        return yyy.toString();
    }

    /**
     *
     * 문자열 특정기호로 나누기 (빈값은 삭제)
     * 3/3///3 ==> String[3]
     * @param str String value
     * @param delim 구분자
     * @return String[]
     */
    public static String[] split(String str, String delim) {
    	if(str == null){
    		return new String[0];
    	}
    	
        StringTokenizer token = new StringTokenizer(str, delim);
        String[] result = new String[token.countTokens()];

        for (int i = 0; token.hasMoreTokens(); i++) {
            result[i] = token.nextToken();
        }

        return result;
    }

    /**
     *
     * 문자열 특정기호로 나누기 (생략 없이 빈값도 다 배열)
     * 3/3///3 ==> String[5]
     * @param str String value
     * @param delim 구분자
     * @return String[]
     */
    public static String[] splitAll(String str, String delim) {
    	Map<Integer, String> map = new HashMap<Integer, String>();
    	int i,idxEnd = 0;
    	String value = "";

    	for(i=0; str.indexOf(delim)>-1; i++){
    		idxEnd = str.indexOf(delim);

    		value  = str.substring(0, idxEnd);
    		map.put(i, value);
    		str = str.substring(idxEnd+delim.length());
    	}
    	map.put(i, str);

    	String[] result = new String[map.size()];
    	for(i=0; i<map.size(); i++){
    		result[i] = map.get(i).trim();
    	}

        return result;
    }

    /**
     *
     * 문자열 특정기호로 나누기
     *
     * @param str String value
     * @param delim 구분자
     * @return List<String>
     */
    public static List<String> split2Iterate(String str, String delim) {
        List<String> result = new ArrayList<String>();

        if(isNull(str))
        	return result;

        StringTokenizer token = new StringTokenizer(str, delim);
        for (; token.hasMoreTokens();) {
            result.add( token.nextToken() );
        }

        return result;
    }

    /**
     *
     * 전화번호에  '-' 추가하기
     *
     * @param str 전화번호
     * @return String
     */
    public static String addHyphenToTelephone(String str) {
        StringBuffer buffer = new StringBuffer();

        if ( str == null ) return "-";

        if (str.substring(0,2).equals("02")) {
            if ( str.length() == 9 ) {
                buffer.append("02-");
                buffer.append( str.substring(2,5) );
                buffer.append("-");
                buffer.append( str.substring(5,9) );
            } else if ( str.length() == 10 ) {
                buffer.append("02-");
                buffer.append( str.substring(2,6) );
                buffer.append("-");
                buffer.append( str.substring(6,10) );
            }
        } else {
            if ( str.length() == 10 ) {
                buffer.append( str.substring(0,3) );
                buffer.append("-");
                buffer.append( str.substring(3,6) );
                buffer.append("-");
                buffer.append( str.substring(6,10) );
            }else if ( str.length() == 11) {
                buffer.append( str.substring(0,3) );
                buffer.append("-");
                buffer.append( str.substring(3,7) );
                buffer.append("-");
                buffer.append( str.substring(7,11) );
            }
        }

        return buffer.toString();
    }

    /**
     *
     * 문자열에 구분자 숫자
     *
     * @param str 문자열
     * @param delim 구분자
     * @return int
     */
    public static int tokenStringNum(String str, String delim) {
        StringTokenizer token = new StringTokenizer(str, delim);
        int count = token.countTokens();
        return count;
    }

    /**
     *
     * 문자열이 null 이면 대치문자열로 대치
     *
     * @param str 문자열
     * @param replaceStr 대치 문자열
     * @return String
     */
    public static String nullToString(String str) {
    	return nullToString(str, "");
    }
	public static String nullToString(String str, String replaceStr) {
		return (str == null || "".equals(str))? replaceStr:str;
	}

	/**
	 *
	 * null 여부 체크
	 *
	 * @param str 문자열
	 * @return boolean
	 */
	public static boolean isNull(String str) {
		return (str == null || "".equals(str))? true:false;
	}

	/**
	 *
	 * null 여부 체크
	 *
	 * @param str Object 객체
	 * @return boolean
	 */
	public static boolean isNull(Object str) {
		return (str == null || "".equals(str))? true:false;
	}

	/**
	 *
	 * 문자열 자르기
	 *
	 * @param s 문자열
	 * @param max 길이
	 * @param tail 꼬리 태그
	 * @return String
	 */
	public static String shortString(String s, int max, String tail){

		String result = "";
		int count = 0;

		if(s == null)
		    return result;


		if(s.getBytes().length > max) {

			max -= 2;
			char buf[] = s.toCharArray();

			for(int i=0; i <= max && i < s.length(); i++) {

				if(buf[i] >= 0xa100 && buf[i] <= 0xfe00) {
					count += 2;
				} else {
					count ++;
				}

				if(count > max) {
					result += tail;
					break;

				} else {
					result += (new String(buf, i, 1));
				}

			}
		} else {
			result = s;
		}

		return result;
	}

	/**
	 * 문자 숨기기 .. 뒤에서 2자  **로 치환
	 * @param str
	 * @param re
	 * @return
	 */
	public static String hideString(String str, String re){
		String result = "";
		if(isNull(str))
			return str;

		if(str.length() == 1)
			return re;

		if(str.length() == 2)
			return str.substring(0, 1)+re;


		result = str.substring(0, str.length()-2)+re+re;
		return result;
	}


	/**
	 * 메일 숩기기 .. 뒤에서 2자  **로 치환
	 * @param str
	 * @param re
	 * @return
	 */
	public static String hideEmail(String str, String re){

		String result = "";
		if(isNull(str))
			return str;

		String[] arr = split(str, "@");
		if(isNull(arr) || arr.length < 2)
			return str;

		str = arr[0];
		if(str.length() == 1){
			result =  re;

		}else if(str.length() == 2){
			result =  str.substring(0, 1)+re;

		}else{
			result = str.substring(0, str.length()-2)+re+re;
		}

		return result+"@"+arr[1];
	}

	/**
	 *
	 * 왼쪽에 특정문자 붙이기
	 *
	 * @param src 문자열
	 * @param ch 특정문자
	 * @param num 총 길이
	 * @return String
	 */
	public static String leftPadding(String src, String ch, int num) {
	    String result = "";

	    if(src == null || src.length() >= num) {
	        return src;
	    }

	    int cnt = num - src.length();

	    for(int i=0; i < cnt; i++) {
	        result += ch;
	    }

	    return result+src;
	}

	/**
	 *
	 * html 태그 삭제
	 *
	 * @param str html
	 * @return String
	 */
	public static String stripHtml(String str) {
		str = replace(str, "<","&lt;");
		str = replace(str, ">","&gt;");

		return str;
	}

	/**
	 *
	 * 에디터 인젝션
	 *
	 * @param str 문자열
	 * @return String
	 */
	public static String editorInjection(String str) {

		if( isNull(str) ) str = "";

		str = replacePattern(str, "<[S|s][C|c][R|r][I|i][P|p][T|t]", "&lt;SCRIPT");
		str = replacePattern(str, "[J|j][A|a][V|v][A|a][S|s][C|c][R|r][I|i][P|p][T|t]\\:","#NO script:");
		str = replace(str, "#NO script:downFile(","javaScript:downFile(");
		return str;
	}

	/**
	 * 아이핀 인증요 변환
	 *
	 * @param paramValue
	 * @param gubun
	 * @return
	 */
	public static String requestIPinReplace (String paramValue, String gubun) {
        String result = "";

        if (paramValue != null) {

        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");

        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
        	}

        	result = paramValue;

        }
        return result;
	}
	/**
	 *
	 * 문자열 교체 패턴
	 *
	 * @param str 문자열
	 * @param pattern 패턴
	 * @param replace 교체문자
	 * @return String
	 */
	public static String replacePattern(String str, String pattern, String replace ){

		int c=0;
		boolean isFind = true;
		Matcher mc = null;
		Pattern pt = Pattern.compile(pattern);

		while(isFind){
			mc = pt.matcher(str);

			if(mc.find()){
				str = replace(str, mc.group(),replace);
			}else{
				isFind = false;
			}

			if(c++>100000){
				// injection 공격 받았을때  10만 건만 대응.. 이상은 포기..
				// 처리 시간동안 서버 느려질 수 있어 서버 성능 문제로 MAX 10만건  처리시간 약 2분
				break;
			}
		}

		return str;
	}

	/**
	 *
	 * html 태그 삭제
	 *
	 * @param s html
	 * @return String
	 */
	public static String shortNonHtmlString(String s){
	    StringBuffer strip_html = new StringBuffer();
	    char[] buf = s.toCharArray();
	    int j=0;

	    for (; j<buf.length; j++) {
	        if(buf[j]=='<') {
	            for(; j<buf.length; j++) {
	                if(buf[j]=='>') { break; }
	            }
	        } else { strip_html.append(buf[j]); }
	    }

	    return strip_html.toString();
	}

	/**
	 *
	 * html 태그 삭제후 글길이 짜르기
	 *
	 * @param s html
	 * @param max 크기
	 * @return String
	 */
	public static String shortNonHtmlString(String s, int max) {
	    StringBuffer strip_html = new StringBuffer();
	    char[] buf = s.toCharArray();
	    int j=0;

	    for (; j<buf.length; j++) {
	        if(buf[j]=='<') {
	            for(; j<buf.length; j++) {
	                if(buf[j]=='>') { break; }
	            }
	        } else { strip_html.append(buf[j]); }
	    }

	    return shortString( strip_html.toString(), max, "..." );
	}

	/**
	 *
	 * 랜덤 문자열 생성
	 *
	 * @param length 길이
	 * @return String
	 */
	public static String makeRandomString(int length) {
		StringBuffer result = new StringBuffer();

		char character[] = {'0','1','2','3','4','5','6','7','8','9'};

		for(int i=0; i < length; i++) {
			result.append(character[(int)(Math.random()*character.length)]);
		}

		return result.toString();
	}

	/**
	 *
	 * 문자열을 대문자로 변환한다.
	 *
	 * @param str 문자열
	 * @return String
	 */
	public static String toUpperCase(String str) {
		return (str == null)? "":str.toUpperCase();
	}

	/**
	 *
	 * 문자열을 한국어(EUC-KR)로 인코딩한다.
	 * 참고) form또는 querystring에서 넘어온 한글은 KSC5601 또는 EUC-KR로 인코딩 해야 한글로 인식함
	 * 용례) StringUtil.toKR(request.getParameter("content"))
	 *
	 * @param str 한글로 인코딩할 문자열
	 * @return String
	 */
	public static String toKR(String str) {
		if (str == null || str.trim().equals("")) return str;
		try {
			return new String(str.getBytes(ENG_CHARSET), KOR_CHARSET);
		} catch (UnsupportedEncodingException ex) {
			return null;
		}
	}


	/**
	 *
	 * html 형태의 것을 Text형태의 스트링 형태로 변환한다.
	 * 용례) throw new EmpsException(StringUtil.getFileName(requst.getRequestURI()), 500);
	 *
	 * @param str 원래형태의 String 값
	 * @return String
	 */
	public static String htmlToText(String str) {
		int i = 0,j = 0;
		StringBuffer string = new StringBuffer(65536);
		while ( (j = str.indexOf("<",i)) != -1 ) {
			string.append(str.substring(i,j));
			if (str.regionMatches(true,j,"<br>",0,4))
				string.append("<");
			else
				string.append("&lt;");
			i = j + 1;
		}
		return (string.append(str.substring(i,str.length())).toString());
	}

	/**
	 *
     * MD5 변환
     *
     * @return String
     */
	public static String md5(String str){

		String re = null;
		try{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(str.getBytes());

			byte[] md5Bytes = md5.digest();
	        StringBuffer sb = new StringBuffer();
	        for(int i = 0 ; i < md5Bytes.length ; i++){
	                sb.append(Integer.toString((md5Bytes[i] & 0xf0) >> 4, 16));
	                sb.append(Integer.toString(md5Bytes[i] & 0x0f, 16));
	        }
	        re = sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}

		return re;
    }
    
    /**
	 * 
	 * 문자열중에서 A문자열을 B문자열로 모두 대치한다.(대소문자 구별안함)
	 * 용례) StringUtil.replace(ex.getMessage(), "\n", "<br>")
	 * 
	 * @param original 오리지날 문자열
	 * @param find 찾고자 하는 문자열
	 * @param replace 바꾸고자 하는 문자열
	 * @return String
	 */
    public static String replace(String original, String find, String replace) {
		if(original==null || find==null || replace==null || original.length()<1 || find.length()<1) return original; 
		int index=-1, fromIndex=0, tempIndex;
		StringBuffer sb=new StringBuffer();
		while((tempIndex=original.indexOf(find, fromIndex))>=0)	{
			index=tempIndex;
			sb.append(original.substring(fromIndex, index)).append(replace);
			fromIndex=index+find.length();
		}
		if(sb.length()<1) return original;
		
		sb.append(original.substring(index+find.length()));
		return sb.toString();
	}

	/**
	 *
	 * String 을 URLEncode
	 *
	 * @param text 문자열
	 * @return String
	 * @throws java.io.UnsupportedEncodingException
	 *
	 */
	public static String urlEncode(String text) throws UnsupportedEncodingException {
		return URLEncoder.encode(text, KOR_CHARSET);
	}

	/**
	 *
	 * String 을 URLDecode
	 *
	 * @param text 문자열
	 * @return String
	 * @throws java.io.UnsupportedEncodingException
	 */
	public static String urlDecode(String text) throws UnsupportedEncodingException {
		return URLDecoder.decode(text, KOR_CHARSET);
	}

	/**
	 * RequestURI의 sub URI를 가져옴.
	 * ex)	"/uss/empinfo.do"   => return "uss/"
	 * 		"/empinfo.do"    	=> return ""
	 *
	 * @param request HttpServletRequest 객체
	 * @return String
	 */
	public static String getSubURI(HttpServletRequest request) {

		return getSubURI(request.getRequestURI());
    }
	private static String getSubURI(String uri){

		if(isNull(uri)) {
            return "";
        }
		String suf = "";
		
		int point = uri.indexOf("/");

		if(point < -1){
			return "";
		}else if(point == 0){
			uri = uri.substring(1);
			point = uri.indexOf("/");
		}

        String pre = uri.substring(0, point+1);
        suf = uri.substring(point+1, uri.length());
        
        point = suf.indexOf("/");
        if(!isNull(suf) && point > -1)
        	pre += getSubURI(suf);
        
        return pre;
	}

	/**
	 * 파라미터에 String 추가시. getSubURI의 결과값에 연결
	 *
	 * @param request HttpServletRequest 객체
	 * @param suffix 문자
	 * @return String
	 */
	public static String getSubURI(HttpServletRequest request, String suffix) {
		return getSubURI("", request, suffix);
	}

	/**
	 *
	 * 파라미터에 String 추가시. getSubURI의 결과값에 연결
	 *
	 * @param prefix 접두사
	 * @param request HttpServletRequest 객체
	 * @param suffix 접미사
	 * @return String
	 */
	public static String getSubURI(String prefix, HttpServletRequest request, String suffix) {
		StringBuffer sb = new StringBuffer();
		sb.append(prefix);
		sb.append(getSubURI(request));
		sb.append(suffix);
		return sb.toString();
	}

	/**
	 *
	 * List<String> 을  String Array로 변환
	 *
	 * @param list List<String> 객체
	 * @return String[]
	 */
	public static String[] listToArray(List<String> list){
		String[] toarr = new String[list.size()];
		return (String[]) list.toArray(toarr);
	}

	/**
	 *
	 * cover 문자로 str 감싸는 문자 만들기
	 * ex> coverString("[", "원글", "]")
	 *
	 * @param lCover 왼쪽 커버
	 * @param str String Value
	 * @param rCover 오른쪽커버
	 * @return String
	 */
	public static String coverString(String lCover, String str, String rCover) {
		StringBuffer sb = new StringBuffer();
		sb.append(lCover);
		sb.append(str);
		sb.append(rCover);
		return sb.toString();
	}

	/**
	 *
	 * cover 문자로 str 감싸는 문자 만들기
	 *
	 * @param str String Value
	 * @param cover 커버
	 * @return String
	 */
	public static String coverString(String str, String cover) {
		return coverString(cover, str, cover);
	}

	/**
	 *
	 * request에서 return url 받아옴
	 *
	 * @param request
	 * @return
	 */
	public static String getReturnUrl(HttpServletRequest request){
		StringBuffer sb = new StringBuffer();
		sb.append(request.getRequestURL());
		String queryString = request.getQueryString();
		if(!StringUtil.isNull(queryString)) {
			sb.append("?");
			sb.append(queryString);
		}
		return sb.toString();
	}

	/**
	 *
	 * request에서 파라미터 사용용 return url 받아옴
	 *
	 * @param request
	 * @return
	 */
	public static String getParamReturnUrl(HttpServletRequest request){
		String str = getReturnUrl(request);
		try {
			str = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			str = "/index.do";
		}
		return str;
	}

	/**
	 *
	 *  IN QUERY 를 만들어주기 위한 함수
	 *  ex) GUEST;MASTER;GROUP => 'GUEST','MASTER','GROUP'
	 *
	 * @param str 문자열
	 * @param delim 구분자
	 * @return String
	 */
	public static String makeInQueryForString(String str, String delim){

		String[] strarr = split(str, delim);

		StringBuffer sb = new StringBuffer();
		for(String s : strarr){
			if(sb.length()>0)
				sb.append(",");

			sb.append(coverString(s, "'"));
		}
		return sb.toString();
	}

	/**
	 *
	 * 한글 영어에 상관없이 일정한 길이 만큼 잘라낸다. (UTF-8 환경에서는 한글은 3바이트)
	 *<pre>
     * ex) String line="뷁테스터테스터테";
     *     System.out.println("문자열 길이 : "+line.length());
     *     System.out.println("문자열 byte 길이 : "+line.getBytes().length);
     *     System.out.println("문자열 byte 길이 UTF-8 : "+line.getBytes("UTF-8").length);
     *</pre>
     * @param src 문자열
	 * @param max 길이
	 * @param tail 꼬리태그
	 * @return String
	 */
	public static String shortCutString(String src, int max, String tail) throws UnsupportedEncodingException {

		String result = "";
		int count = 0;
		int korean = 0;
		int english = 0;

		if(src == null)
		    return result;

		if(src.getBytes("UTF-8").length > max) {

            char buf[] = src.toCharArray();

			for(int i=0; i <= max && i < src.length(); i++) {
                // 한글 유니코드 16진수 확인
				if(buf[i] >= 0xa100 && buf[i] <= 0xfe00) {
					count += 3;
					korean++;
				}else if( "(".equals(String.valueOf(buf[i]) ) ){
					count += 0;
				}else {
					count++;
					english++;
				}

				if( english!=0 && english%20==0 ){
					count++;

				}
				if( korean!=0 && korean%10==0 ){
					count--;
				}

				if(count > max) {
					result += tail;
					break;
				} else {
					result += (new String(buf, i, 1));
				}
			}
		} else {
			result = src;
		}

		return result;
	}

    /**
     *
     * XSS 방어 코드
     *
     * @param str 스트링
     * @return 스트링
     */
    public static String removeXSS(String str) {

        str = str.replaceAll("<", "&lt;");
        str = str.replaceAll(">", "&gt;");
        str = str.replaceAll("\\(", "&#40;");
        str = str.replaceAll("\\)", "&#41;");
        str = str.replaceAll("'", "&#39;");
        str = str.replaceAll("eval\\((.*)\\)", "");
        str = str.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        str = str.replaceAll("script", "");

        return str;
    }

    /**
     *
     * 업로드 파일 확장자 검증
     *
     * @param fileExtension 파일 확장자
     * @return True/False
     */
    public static boolean isUseUploadFile(String fileExtension) {

        boolean isUse = true;

        if(".php".equals(fileExtension) || ".js".equals(fileExtension)
        		|| ".pl".equals(fileExtension) || ".sh".equals(fileExtension)) {
            isUse = false;
        }

        return isUse;
    }
    
    public static String capitalize(String s) {
        if (s.length() == 0) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toString();
    }
    
}
