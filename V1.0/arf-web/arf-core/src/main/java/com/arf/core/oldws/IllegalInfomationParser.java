package com.arf.core.oldws;

import java.util.ArrayList;
import java.util.List;

import com.arf.core.oldws.IllegalInformation.ViolationRecord;
import com.arf.core.util.MStringUntils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class IllegalInfomationParser {

	public  static IllegalInformation parse(String jsonStr) {
		if (MStringUntils.isEmptyOrNull(jsonStr))
			return null;
		try {
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			IllegalInformation illegalInformation = new IllegalInformation();
			illegalInformation.setJsonStr(jsonStr);
			illegalInformation.setHasData(jsonObject.getBoolean("HasData"));
			illegalInformation.setErrorCode(jsonObject.getInt("ErrorCode"));
			illegalInformation.setSuccess(jsonObject.getBoolean("Success"));
			illegalInformation.setErrMessage(jsonObject.getString("ErrMessage"));
			illegalInformation.setLastSearchTime(jsonObject.getString("LastSearchTime"));

			// 解析record
			JSONArray jsonRecords = jsonObject.getJSONArray("Records");
			if (null != jsonRecords && jsonRecords.size() > 0) {
				int size = jsonRecords.size();
				List<ViolationRecord> records = new ArrayList<ViolationRecord>();
				JSONObject jsonRecord = null;
				for (int i = 0; i < size; i++) {
					jsonRecord = jsonRecords.getJSONObject(i);
					ViolationRecord record = IllegalInformation.getNewViolationRecord();
					record.setTime(jsonRecord.getString("Time"));
					record.setLocation(jsonRecord.getString("Location"));
					record.setReason(jsonRecord.getString("Reason"));
					record.setCount(jsonRecord.getString("count"));
					record.setStatus(jsonRecord.getString("status"));
					record.setDepartment(jsonRecord.getString("department"));
					record.setDegree(jsonRecord.getString("Degree"));
					records.add(record);
				}
				illegalInformation.setRecords(records);
			}
			return illegalInformation;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
