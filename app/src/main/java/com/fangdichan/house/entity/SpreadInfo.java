package com.fangdichan.house.entity;

public class SpreadInfo
{
	String id,name,phone,head;
	int level,fatherNode,childNUm,childSum;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public int getFatherNode() {
		return fatherNode;
	}

	public void setFatherNode(int fatherNode) {
		this.fatherNode = fatherNode;
	}

	public int getChildNUm() {
		return childNUm;
	}

	public void setChildNUm(int childNUm) {
		this.childNUm = childNUm;
	}

	public int getChildSum() {
		return childSum;
	}

	public void setChildSum(int childSum) {
		this.childSum = childSum;
	}
}