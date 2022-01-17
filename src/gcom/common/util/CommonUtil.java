package gcom.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.*;

import gcom.user.model.MemberPolicyModel;

public class CommonUtil {
	
	final static int CHAR_ACCESS_ARRAY_SIZE = 10;

	public static String getStrPolicyChangeOperation(String value, String strPolicy){
		String result  = "";
		String blockYn = "";
		String blockCode = "";
		
		if(strPolicy.substring(0,1).equals("Y")){
			blockYn = "Y";
			blockCode = strPolicy.length() > 1 ? strPolicy.substring(2, strPolicy.length()) : "" ;
			
			if(blockCode.contains(value)) {
				result = blockYn + "," + getStrPolicySubData(value, blockCode);
			} else {
				result = strPolicy;
			}

		}else{
			blockYn = "N";
			blockCode = strPolicy.length() > 1 ? strPolicy.substring(2, strPolicy.length()) : "" ;
			
			if(blockCode.contains(value)) {
				result = strPolicy;
			} else {
				result = blockYn + "," + getStrPolicyAddData(value, blockCode);
			}
		}
		
		return result;
	}
	
	// ���� �ڵ� N�� ��� �ش� �ڵ尡 ���Ե����� ���� ��� �ڵ� �߰�
	private static String getStrPolicyAddData(String value, String blockCode) {
		String result = "";
		
		if("".equals(blockCode)){
			result = value;
		} else {
			result = blockCode + "," +  value;
		}
		
		return result;
	}

	// ���� �ڵ� Y�� ��� �ش� �ڵ尡 ���ԵǾ��� ��� �ش� �ڵ� ����
	private static String getStrPolicySubData(String value, String blockCode) {
		String codeArray[] = blockCode.split(",");
		ArrayList<String> resultArray = new ArrayList<String>();
		
		 for(int i = 0 ; i < codeArray.length; i ++) {
         	if(!value.equals(codeArray[i])){
         		resultArray.add(codeArray[i]);
         	}
         }
		 
		 String result = resultArray.toString();
		 result = result.replace("[", "");
		 result = result.replace("]", "");
		 result = result.replace(" ", "");
		 
		return result;
	}
	
	public static String getPolicyIcon(MemberPolicyModel data){
		String icon = "";
		if(data.getIsUninstall()){
			icon += "<i class=\"fa fa-trash policy_icon\" style=\"color:#7ed67e \" title=\"������Ʈ��������\"></i>";
		}else{
			icon += "<i class=\"fa fa-trash policy_icon\" style=\"color:#ea6a66\" title=\"������Ʈ�����Ұ���\"></i>";
		}

		if(data.getIsFileEncryption()){
			icon += "<i class=\"fa fa-file policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"���Ͼ�ȣȭ���\"></i>";
		}else{
			icon += "<i class=\"fa fa-file policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"���Ͼ�ȣȭ�̻��\"></i>";
		}

		if(data.getIsCdEncryption()){
			icon += "<i class=\"fa fa-get-pocket policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"CD��ȣȭ���\"></i>";
		}else{
			icon += "<i class=\"fa fa-get-pocket policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"CD��ȣȭ�̻��\"></i>";
		}
		
		if(data.getIsPrint()){
			icon += "<i class=\"fa fa-print policy_icon\" style=\"color:#7ed67e\" title=\"�����ͻ�밡��\"></i>";
		}else{
			icon += "<i class=\"fa fa-print policy_icon\" style=\"color:#ea6a66\" title=\"�����ͻ��Ұ���\"></i>";
		}
		
		if(data.getIsCdEnabled()){
			icon += "<i class=\"fa fa-database policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"CD���\"></i>";
		}else{
			icon += "<i class=\"fa fa-database policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"CD�̻��\"></i>";
		}
		
		if(data.getIsCdExport()){
			icon += "<i class=\"fa fa-minus-circle policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"CD���Ⱑ��\"></i>";
		}else{
			icon += "<i class=\"fa fa-minus-circle policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"CD����Ұ���\"></i>";
		}
		
		if(data.getIsWlan()){
			icon += "<i class=\"fa fa-wifi policy_icon\" style=\"color:#7ed67e\" title=\"���������\"></i>";
		}else{
			icon += "<i class=\"fa fa-wifi policy_icon\" style=\"color:#ea6a66\" title=\"�������̻��\"></i>";
		}
		
		if(data.getIsNetShare()){
			icon += "<i class=\"fa fa-share-alt policy_icon\" style=\"color:#7ed67e\" title=\"�����������\"></i>";
		}else{
			icon += "<i class=\"fa fa-share-alt policy_icon\" style=\"color:#ea6a66\" title=\"���������̻��\"></i>";
		}
		
		if(data.getIsWebExport()){
			icon += "<i class=\"fa fa-envelope policy_icon\" style=\"color:#7ed67e\" title=\"���Ϲ�����\"></i>";	
		}else{
			icon += "<i class=\"fa fa-envelope policy_icon\" style=\"color:#ea6a66\" title=\"���Ϲ���̻��\"></i>";
		}
		
		if(data.getIsSensitiveDirEnabled()){
			icon += "<i class=\"fa fa-folder-open policy_icon\"  style=\"color:#7ed67e\" title=\"��ȣ�������ٰ���\"></i>";	
		}else{
			icon += "<i class=\"fa fa-folder-open policy_icon\"  style=\"color:#ea6a66\" title=\"��ȣ�������ٺҰ���\"></i>";		
		}
		
		if(data.getIsSensitiveFileAccess()){
			icon += "<i class=\"fa fa-file-archive-o policy_icon\" style=\"color:#7ed67e\" title=\"�ΰ��������ٽû���\"></i>";		
		}else{
			icon += "<i class=\"fa fa-file-archive-o policy_icon\"  style=\"color:#ea6a66\" title=\"�ΰ��������ٽú�ȣ�������̵�\"></i>";	
		}
		
		if(data.getIsStorageExport()){
			icon += "<i class=\"fa fa-archive policy_icon\"  style=\"color:#7ed67e\" title=\"��ũ���Ⱑ��\"></i>";	
		}else{
			icon += "<i class=\"fa fa-archive policy_icon\"  style=\"color:#ea6a66\" title=\"��ũ����Ұ���\"></i>";		
		}
		
		if(data.getIsStorageAdmin()){
			icon += "<i class=\"fa fa-id-card policy_icon\" style=\"color:#7ed67e\" title=\"��ũ��������\"></i>";		
		}else{
			icon += "<i class=\"fa fa-id-card policy_icon\" style=\"color:#ea6a66\" title=\"��ũ�����Ұ�\"></i>";	
		}
		
		if(data.getIsUsbControlEnabled()){
			icon += "<i class=\"fa fa-cogs policy_icon\"  style=\"color:#7ed67e\" title=\"USB������ɻ��\"></i>";	
		}else{
			icon += "<i class=\"fa fa-cogs policy_icon\"  style=\"color:#ea6a66\" title=\"USB������ɹ̻��\"></i>";		
		}
		
		if(data.getPatternFileControl() == 1){
			icon += "<i class=\"fa fa-file-powerpoint-o policy_icon\"  style=\"color:#7ed67e\" title=\"������������ϻ���\"></i>";		
		}else{
			icon += "<i class=\"fa fa-file-powerpoint-o policy_icon\"  style=\"color:#ea6a66\" title=\"������������ϰݸ�\"></i>";	
		}
		
		if(data.getIsUsbBlock()){
			icon += "<i class=\"fab fa-usb policy_icon\" style=\"color:#ea6a66\" title=\"USB��Ʈ����\"></i>";
		}else{
			icon += "<i class=\"fab fa-usb policy_icon\" style=\"color:#7ed67e\" title=\"USB��Ʈ���\"></i>";
		}
		
		if(data.getIsComPortBlock()){
			icon += "<i class=\"fa fa-plug policy_icon\" style=\"color:#ea6a66\" title=\"�ø�����Ʈ����\"></i>";
		}else{
			icon += "<i class=\"fa fa-plug policy_icon\" style=\"color:#7ed67e\" title=\"�ø�����Ʈ���\"></i>";
		}
		
		if(data.getIsNetPortBlock()){
			icon += "<i class=\"fa fa-sitemap policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"��Ʈ��ũ��Ʈ����\"></i>";	
		}else{
			icon += "<i class=\"fa fa-sitemap policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"��Ʈ��ũ��Ʈ���\"></i>";
		}
		
		if(data.getIsProcessList()){ 
			icon += "<i class=\"fa fa-desktop policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"���μ����������\"></i>";
		}else{
			icon += "<i class=\"fa fa-desktop policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"���μ���������\"></i>";	
		}
		
		if(data.getIsFilePattern()){
			icon += "<i class=\"fa fa-clone policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"�ΰ�������������\"></i>";
		}else{
			icon += "<i class=\"fa fa-clone policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"�ΰ������������\"></i>";
		}
		
		if(data.getIsWebAddr()){
			icon += "<i class=\"fab fa-internet-explorer policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"����Ʈ����\"></i>";
		}else{
			icon += "<i class=\"fab fa-internet-explorer policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"����Ʈ���\"></i>";	
		}
		
		if(data.getIsMsgBlock()){
			icon += "<i class=\"fa fa-commenting policy_icon\" aria-hidden=\"true\" style=\"color:#ea6a66\" title=\"�޽�������\"></i>";
		}else{
			icon += "<i class=\"fa fa-commenting policy_icon\" aria-hidden=\"true\" style=\"color:#7ed67e\" title=\"�޽������\"></i>";
		}
		
		if(data.getIsWatermark()){
			icon += "<i class=\"fa fa-tint policy_icon\" style=\"color:#7ed67e\" title=\"���͸�ũ���\"></i>";
		}else{
			icon += "<i class=\"fa fa-tint policy_icon\" style=\"color:#ea6a66\" title=\"���͸�ũ�����\"></i>";
		}
		
		return icon;
	}

	public static String getFomatLimitTime(String data) {
		String result = "";
		
		if("".equals(data) || "0".equals(data)) {
			result = data;
		} else {
			String[] temp =  data.split(">");
			
			String from = temp[0];
			if(temp.length > 1) {
				String limitType = temp[1].substring(0, 1);
				String limitTime = temp[1].substring(1, temp[1].length());
				
				Calendar cal = new GregorianCalendar(Locale.KOREA);
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				try {
					Date date = transFormat.parse(from);
					cal.setTime(date);
					
					if ("H".equals(limitType)) {
						cal.add(Calendar.HOUR, Integer.parseInt(limitTime)); // �ð��� ���Ѵ�
					} else if ("D".equals(limitType)) {
						cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(limitTime)); // ���� ���Ѵ�
					} else if ("M".equals(limitType)) {
						cal.add(Calendar.MONTH, Integer.parseInt(limitTime)); // ���� ���Ѵ�
					}
					
					SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    String strDate = fm.format(cal.getTime());
					result = strDate;
			
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	public static String getReplaceHtmlChar(String str) {
		String data = "";
		StringBuffer sb = new StringBuffer();
		 
		for(int i=0; i < str.length(); i++) 
		{
		    char c = str.charAt(i);
		    switch (c) 
		   {
		      case '<' : 
		        sb.append("&lt;");
		        break;
		     case '>' : 
		        sb.append("&gt;");
		        break;
		     case '&' :
		        sb.append("&amp;");
		        break;
		     case '"' :
		        sb.append("&quot;");
		        break;
		     case '\\' : 
		        sb.append("&apos;");
		        break;
		     default:
		        sb.append(c);
		     } 
		 }
		data = sb.toString();
	
		return data;
	}
	
	public static String createQuarantinePathAccessCode() {
		String strAccessCode = null;
        char[] chAccessArray = new char[CHAR_ACCESS_ARRAY_SIZE];
        char ch;
		Pattern pAlpha = null;
		Pattern pNumber = null;
		Pattern pChar = null;
		
		pAlpha = Pattern.compile("[a-z]");
		pNumber = Pattern.compile("[0-9]");
		pChar = Pattern.compile("\\p{Punct}");
		
		boolean completeFlag = true;

		while (completeFlag) {	
            for (int j = 0; j < chAccessArray.length; j++) {
            	
            	ch = (char)((Math.random() * 94) + 33);
            	
            	switch (ch) {
            	case '\'':
            	case '{':
            	case '}':
            	case '[':
            	case ']':
            	case '(':
            	case ')':
            	case ':':
            	case ';':
            	case '.':
            	case ',':
            	case '/':
            	case '\\':
            	case '<':
            	case '>':
            		ch = '!';
            		break;
            	}
            	
                chAccessArray[j] = ch;
            }		            
            strAccessCode = new String(chAccessArray);		            
    		if (pAlpha.matcher(strAccessCode).find() && pNumber.matcher(strAccessCode).find() && pChar.matcher(strAccessCode).find()) {
				completeFlag = false;
    		}
		}
		
		return strAccessCode;
	}
	
}
