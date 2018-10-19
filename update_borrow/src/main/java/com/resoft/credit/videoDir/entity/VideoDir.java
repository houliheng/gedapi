package com.resoft.credit.videoDir.entity;


import com.thinkgem.jeesite.common.persistence.TreeEntity;

/**
 * 影像目录管理Entity
 * @author wanghong
 * @version 2016-11-01
 */
public class VideoDir extends TreeEntity<VideoDir> {
	
	private static final long serialVersionUID = 1L;
	//private VideoDir parent;		// 父级编号
	//private String parentIds;		// 所有父级编号
	//private String name;		// 节点名称
	//private Integer sort;		// 排序
	private String applyNo;
	private String fileDir;		// 目录
	private String type;		// 其他用途
	private String haveChildNode; //是否存在子节点
	
	public VideoDir() {
		super();
	}

	public VideoDir(String id){
		super(id);
	}
	
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public String getFileDir() {
		return fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public VideoDir getParent() {
		return parent;
	}

	@Override
	public void setParent(VideoDir parent) {
		this.parent = parent;
	}

	public String getHaveChildNode() {
		return haveChildNode;
	}

	public void setHaveChildNode(String haveChildNode) {
		this.haveChildNode = haveChildNode;
	}
	
}
