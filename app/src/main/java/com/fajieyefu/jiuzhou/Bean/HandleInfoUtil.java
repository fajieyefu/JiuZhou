package com.fajieyefu.jiuzhou.Bean;

import android.util.Log;

import com.fajieyefu.jiuzhou.Audit.ContractInputActivity2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiancheng on 2016/10/26.
 */
public class HandleInfoUtil {
	private JSONObject jsonObject;
	private JSONArray dataJsonArray;
	private JSONObject dataJsonObject;
	private JSONArray basicModelJsonArray;
	private int code;
	private String msg;
	public List<ProcessInfo> list_processInfo = new ArrayList<>();

	public HandleInfoUtil(String info) {
		try {
			jsonObject = new JSONObject(info);
			code = jsonObject.optInt("code");
			msg = jsonObject.optString("msg");
			dataJsonArray = jsonObject.getJSONArray("data");
			basicModelJsonArray = jsonObject.getJSONArray("basic_model");
			int len = dataJsonArray.length();
			int position = 0;
			if (code == 0) {
				while (position < len) {
					dataJsonObject = dataJsonArray.getJSONObject(position);
					String process_code = dataJsonObject.getString("process_code");
					String process_name = dataJsonObject.getString("process_name");
					JSONArray classDataJsonArray = dataJsonObject.getJSONArray("class_data");
					List<ClassInfo> list_classInfo = new ArrayList<>();
					int len_classData = classDataJsonArray.length();
					int position_classData = 0;
					while (position_classData < len_classData) {
						JSONObject classDataJsonObject = classDataJsonArray.getJSONObject(position_classData);
						String class_code = classDataJsonObject.getString("class_code");
						ContractInputActivity2.process_class.put(class_code, process_code);
						String class_name = classDataJsonObject.getString("class_name");
						ContractInputActivity2.map_class.put(class_code, class_name);
						JSONArray modelDataJsonArray = classDataJsonObject.getJSONArray("model_data");
						int len_modelData = modelDataJsonArray.length();
						int position_modelData = 0;
						List<ModelInfo> list_modleInfo = new ArrayList<>();
						while (position_modelData < len_modelData) {
							JSONObject modelDataJsonObject = modelDataJsonArray.getJSONObject(position_modelData);
							String model_code = modelDataJsonObject.getString("model_code");
							String model_name = modelDataJsonObject.getString("model_name");
							ContractInputActivity2.map_model.put(model_code, model_name);
							Log.i(model_name, model_code);
							ModelInfo modelInfo = new ModelInfo(model_code, model_name);
							list_modleInfo.add(modelInfo);
							position_modelData++;

						}
						ClassInfo classInfo = new ClassInfo(class_code, class_name, list_modleInfo);
						list_classInfo.add(classInfo);
						position_classData++;
					}
					ContractInputActivity2.map_process.put(process_code, process_name);
					ProcessInfo processInfo = new ProcessInfo(process_code, process_name, list_classInfo);
					list_processInfo.add(processInfo);
					position++;

				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println(ContractInputActivity2.map_process.toString());
	}

	public List<ProcessInfo> getProcessInfoList() {
		if (code == 0) {
			return list_processInfo;
		} else {
			return null;
		}
	}
	public JSONArray getBasicModelJsonArray() {
		return basicModelJsonArray;
	}
}
