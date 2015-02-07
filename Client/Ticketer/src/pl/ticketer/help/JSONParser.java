package pl.ticketer.help;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
	public static String getAttr(String source, String key){
		JSONObject jsonObject = null;
		
		try {
			jsonObject = new JSONObject(source);
		} catch (JSONException jsonException) {
			jsonException.printStackTrace();
		}
		
		String result = "";
		
		String keys[] = key.split(";");
		
		for(int i=0; i<keys.length; i++){
			if(keys[i].endsWith("}")){
				String name = keys[i].substring(0, keys[i].length() - 1);
				try {
					jsonObject = jsonObject.getJSONObject(name);
				} catch (JSONException jsonException) {
					jsonException.printStackTrace();
				} 
			}
			else if(keys[i].endsWith("]")){
				String name = keys[i].split("]")[0];
				int id = 0;
				try {
					id = Integer.parseInt(keys[i].split("]")[1]);
					jsonObject = jsonObject.getJSONArray(name).getJSONObject(id);
				} catch (JSONException jsonException) {
					jsonException.printStackTrace();
				}
			}
			else{
				String name = keys[i];
				try {
					return jsonObject.getString(name);
				} catch (JSONException jsonException) {
					jsonException.printStackTrace();
				}
			}
		}
		
		return result;
	}

}
