package id.co.imastudio.bmkgapp22W.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class HariTanpaHujanItem{

	@SerializedName("pos_hujan")
	private List<PosHujanItem> posHujan;

	@SerializedName("last_update")
	private String lastUpdate;

	@SerializedName("desc")
	private String desc;

	public void setPosHujan(List<PosHujanItem> posHujan){
		this.posHujan = posHujan;
	}

	public List<PosHujanItem> getPosHujan(){
		return posHujan;
	}

	public void setLastUpdate(String lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public String getLastUpdate(){
		return lastUpdate;
	}

	public void setDesc(String desc){
		this.desc = desc;
	}

	public String getDesc(){
		return desc;
	}

	@Override
 	public String toString(){
		return 
			"HariTanpaHujanItem{" + 
			"pos_hujan = '" + posHujan + '\'' + 
			",last_update = '" + lastUpdate + '\'' + 
			",desc = '" + desc + '\'' + 
			"}";
		}
}