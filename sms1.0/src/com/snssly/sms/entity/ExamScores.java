package com.snssly.sms.entity;

public class ExamScores implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer uid;
	private Double scores;
	private String remark;
	private Integer examSubId;// 考试的每一个科目

	private String eanme;// 考试名称
	private String sname;// 科目名称

	private Double avg;// 平均值
	private Integer perfect;// 成绩为优的人数
	private Integer good;// 成绩为良的人数
	private Integer inter;// 成绩为中的人数
	private Integer bad;// 成绩为差的人数
	private Integer count;// 总分段统计
	private Integer sum;// 总分分析的总分字段
	private Integer rank;// 总分分析的名次字段

	public ExamScores() {
	}

	public ExamScores(Integer uid, Double scores, String remark) {
		this.uid = uid;
		this.scores = scores;
		this.remark = remark;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Double getScores() {
		return this.scores;
	}

	public void setScores(Double scores) {
		this.scores = scores;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getExamSubId() {
		return examSubId;
	}

	public void setExamSubId(Integer examSubId) {
		this.examSubId = examSubId;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Integer getPerfect() {
		return perfect;
	}

	public void setPerfect(Integer perfect) {
		this.perfect = perfect;
	}

	public Integer getGood() {
		return good;
	}

	public void setGood(Integer good) {
		this.good = good;
	}

	public Integer getInter() {
		return inter;
	}

	public void setInter(Integer inter) {
		this.inter = inter;
	}

	public Integer getBad() {
		return bad;
	}

	public void setBad(Integer bad) {
		this.bad = bad;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getEanme() {
		return eanme;
	}

	public void setEanme(String eanme) {
		this.eanme = eanme;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

}
