package gcom.service.Statistic;

import java.util.Map;
import gcom.Model.statistic.FlotChartDataModel;
import gcom.Model.statistic.UserAgentStatisticModel;


public interface IStatisticService {
	public FlotChartDataModel getUSBChartDayData(Map<String, Object> map);	
	public FlotChartDataModel getUSBChartMonthData(Map<String, Object> map);
	public FlotChartDataModel getExportChartDayData(Map<String, Object> map);	
	public FlotChartDataModel getExportChartMonthData(Map<String, Object> map);
	public FlotChartDataModel getPrintChartDayData(Map<String, Object> map);
	public FlotChartDataModel getPrintChartMonthData(Map<String, Object> map);
	public FlotChartDataModel getPatternChartDayData(Map<String, Object> map);	
	public FlotChartDataModel getPatternChartMonthData(Map<String, Object> map);
	
	public UserAgentStatisticModel getUserAgentStatisticData(Map<String, Object> map);
	
}
