package gcom.controller.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gcom.Model.statistic.AuditClientSimpleModel;
import gcom.Model.statistic.UserAgentStatisticModel;
import gcom.service.Policy.IPolicyService;
import gcom.service.Policy.PolicyServiceImpl;
import gcom.service.Request.IRequestService;
import gcom.service.Request.RequestServiceImpl;
import gcom.service.Statistic.IStatisticService;
import gcom.service.Statistic.StatisticServiceImpl;

public class getStatisticAction {
	public Map<String, Object> getFlotChartData(Map<String, Object> map){
		IStatisticService as = new StatisticServiceImpl();
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(map.get("setType").toString().equals("DAY")){
			result.put("USB", as.getUSBChartDayData(map));			
			result.put("EXPORT", as.getExportChartDayData(map));			
			result.put("PRINT", as.getPrintChartDayData(map));			
			result.put("PATTERN", as.getPatternChartDayData(map));			

			
		}else if(map.get("setType").toString().equals("MONTH")){
			result.put("USB", as.getUSBChartMonthData(map));			
			result.put("EXPORT", as.getExportChartMonthData(map));			
			result.put("PRINT", as.getPrintChartMonthData(map));			
			result.put("PATTERN", as.getPatternChartMonthData(map));			

		}
		
		return result;						
	}
	
	public UserAgentStatisticModel getUserAgentStatisticData(Map<String, Object> map){
		IStatisticService as = new StatisticServiceImpl();
		return as.getUserAgentStatisticData(map);	
	}
	
	public Map<String, Object> getSimpleContactList(Map<String, Object> map){
		IRequestService as = new RequestServiceImpl();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", as.getSimpleContactList(map));			

		return result;
	}

	public Map<String, Object> getSimpleRequestList(Map<String, Object> map){
		IRequestService as = new RequestServiceImpl();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", as.getSimpleRequestList(map));			

		return result;
		
	}
	public Map<String, Object> getAuditClientSimpleLogList(Map<String, Object> map){
		IPolicyService as = new PolicyServiceImpl();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("data", as.getAuditClientSimpleLogList(map));			

		return result;
		
	}
	
	public List<AuditClientSimpleModel> getSimpleAuditList(Map<String, Object> map){
		IPolicyService as = new PolicyServiceImpl();
		return as.getAuditClientSimpleLogList(map);
		
	}
	
	
}
