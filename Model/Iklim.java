package id.co.imastudio.bmkgapp22W.Model;

public class Iklim {
	private String nama;
	private String lng;
	private String kriteria;
	private String hdt;
	private String id;
	private String prov;
	private String hdh;
	private String lat;



	public Iklim() {
	}

	private String idProv;



	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setKriteria(String kriteria){
		this.kriteria = kriteria;
	}

	public String getKriteria(){
		return kriteria;
	}

	public void setHdt(String hdt){
		this.hdt = hdt;
	}

	public String getHdt(){
		return hdt;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setProv(String prov){
		this.prov = prov;
	}

	public String getProv(){
		return prov;
	}

	public void setHdh(String hdh){
		this.hdh = hdh;
	}

	public String getHdh(){
		return hdh;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
	}

	public void setIdProv(String idProv){
		this.idProv = idProv;
	}

	public String getIdProv(){
		return idProv;
	}

	@Override
 	public String toString(){
		return 
			"Iklim{" +
			"nama = '" + nama + '\'' + 
			",lng = '" + lng + '\'' + 
			",kriteria = '" + kriteria + '\'' + 
			",hdt = '" + hdt + '\'' + 
			",id = '" + id + '\'' + 
			",prov = '" + prov + '\'' + 
			",hdh = '" + hdh + '\'' + 
			",lat = '" + lat + '\'' + 
			",id_prov = '" + idProv + '\'' + 
			"}";
		}
}
