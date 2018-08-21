package cn.standard.pojo;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;

public class Standard {
	private Integer id;
	@NotEmpty(message = "标准号不能为空")
	private String stdNum;
	@NotEmpty(message = "中文名称不能为空")
	private String zhname;
	@NotEmpty(message = "版本不能为空")
	private String version;
	@NotEmpty(message = "关键字/词不能为空")
	private String keys;
//	@Past(message="必须是过去的时间")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date releaseDate;
//	@Past(message="必须是过去的时间")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date implDate;
	private String packagePath;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStdNum() {
		return stdNum;
	}
	public void setStdNum(String stdNum) {
		this.stdNum = stdNum;
	}
	public String getZhname() {
		return zhname;
	}
	public void setZhname(String zhname) {
		this.zhname = zhname;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getImplDate() {
		return implDate;
	}
	public void setImplDate(Date implDate) {
		this.implDate = implDate;
	}
	public String getPackagePath() {
		return packagePath;
	}
	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

}
