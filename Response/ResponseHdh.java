package id.co.imastudio.bmkgapp22W.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseHdh{

	@SerializedName("HariTanpaHujan")
	private List<HariTanpaHujanItem> hariTanpaHujan;

	public void setHariTanpaHujan(List<HariTanpaHujanItem> hariTanpaHujan){
		this.hariTanpaHujan = hariTanpaHujan;
	}

	public List<HariTanpaHujanItem> getHariTanpaHujan(){
		return hariTanpaHujan;
	}

	@Override
 	public String toString(){
		return 
			"ResponseHdh{" + 
			"hariTanpaHujan = '" + hariTanpaHujan + '\'' + 
			"}";
		}
}