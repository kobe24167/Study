package com.aa.test;

public class ss {
	/**
	 * Ìí¼Ó²Ù×÷¼ÇÂ¼
	 * @return
	 */
	@RequestMapping(value="/addRecord")
	@ResponseBody
	public int addRecord(HistoryRecordModel historyRecordModel, HttpServletRequest request) {
		String ip = IpAdrressUtil.getIpAddr(request);
		historyRecordModel.setIp(ip);
		try {
			historyRecordModel.setAddress(IpAdrressUtil.getAddresses(ip));
		} catch (UnsupportedEncodingException e) {
			historyRecordModel.setAddress("NO Address");
		}
		int s = historyRecordService.addRecord(historyRecordModel);
		return s;
		
		
		<dependency>
		<groupId>org.apache.httpcomponents</groupId>
		<artifactId>httpclient</artifactId>
	</dependency>
	
	<dependency>
	<groupId>org.apache.commons</groupId>
	<artifactId>commons-lang3</artifactId>
</dependency>
	}
}
