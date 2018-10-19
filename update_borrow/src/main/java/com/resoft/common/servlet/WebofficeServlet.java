package com.resoft.common.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.resoft.common.utils.DateUtils;
import com.resoft.credit.contract.entity.ContractTemplate;
import com.resoft.credit.contract.entity.ContractUploadFiles;
import com.resoft.credit.contract.service.ContractTemplateService;
import com.resoft.credit.contract.service.ContractUploadFilesService;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;

import DBstep.iMsgServer2000;

public class WebofficeServlet extends HttpServlet {

	/**
	 * 序列化Id
	 */
	private static final long serialVersionUID = 2628337112049285872L;

	/**
	 * 接收WebOffice客户端请求，调用Office Server类进行处理
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 创建OfficeServer实例
		WebOfficeServer officeServer = new WebOfficeServer();
		// 处理请求
		officeServer.service(request, response);
	}
}

/**
 * WebOfficeServer：金格WebOffice控件服务端业务处理类
 */
class WebOfficeServer {

	private static final Log LOG = LogFactory.getLog(WebOfficeServer.class);
	// 属性
	private int mFileSize;
	private byte[] mFileBody;
	private String mFileName;
	private String mFileType;
	private String mRecordID;
	private String mTemplate;
	private String mOption;
	private String mBookmark;
	private String mDescript;
	private String mCommand;
	private String mContent;
	private String mHtmlName;
	private String mDirectory;
	private String mFilePath;
	private String mUserName;
	private int mColumns;
	private int mCells;
	private String mLocalFile;
	private String mRemoteFile;
	private String mLabelName;
	private String mImageName;
	private String mTableContent;
	private String mOfficePrints;
	private int mCopies;
	private String mDocType;
	/*
	 * @desc: 添加当前用户所属域属性
	 */
	// private String mDomainId;
	/* @end */

	// 金格WebOffice服务端
	private iMsgServer2000 MsgObj;

	private ContractTemplateService contractTemplateService = SpringContextHolder.getBean("contractTemplateService");

	private ContractUploadFilesService contractUploadFilesService = SpringContextHolder.getBean("conUploadFilesService");

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 创建金格WebOffice服务端实例
		MsgObj = new iMsgServer2000();

		mOption = "";
		mRecordID = "";
		mTemplate = "";
		mFileBody = null;
		mFileName = "";
		mFileType = "";
		mFileSize = 0;
		mBookmark = "";
		mDescript = "";
		mCommand = "";
		mContent = "";
		mLabelName = "";
		mImageName = "";
		mTableContent = "";
		mDocType = "";
		mOfficePrints = "0";
		/* @end */

		// 服务器存放上传文件的绝对路径
		String server_path = Global.getUserfilesBaseDir();
		String weboffice_save_path = Global.getUserfilesBaseDir();
		// 根据文件系统存储的绝对路径设置一个公文上传的默认路径
		mFilePath = weboffice_save_path + "/default/" + DateUtils.formatDate(new Date(), "yyyy-MM-dd");

		try {
			// 不是Post请求直接返回
			if (!request.getMethod().equalsIgnoreCase("POST")) {
				MsgObj.MsgError("请使用Post方法");
				MsgObj.MsgTextClear();
				MsgObj.MsgFileClear();
				return;
			}

			// 解析请求
			MsgObj.Load(request);

			// 发送数据包错误直接返回
			if (!MsgObj.GetMsgByName("DBSTEP").equalsIgnoreCase("DBSTEP")) {
				MsgObj.MsgError("客户端发送数据包错误!");
				MsgObj.MsgTextClear();
				MsgObj.MsgFileClear();
				return;
			}

			mOption = MsgObj.GetMsgByName("OPTION"); // 取得操作信息
			mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得系统用户

			if (mOption.equalsIgnoreCase("LOADFILE")) {// 下面的代码为打开服务器数据库里的文件
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
				mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
				String version = "1";// 版本号
				ContractUploadFiles cuf = new ContractUploadFiles();
				cuf.setModuleId(mRecordID);
				List<ContractUploadFiles> listcuf = contractUploadFilesService.findList(cuf); // 获取上传记录
				if (listcuf != null && listcuf.size() > 0) {
					Integer in = new Integer(listcuf.get(0).getVersionNo());
					version = String.valueOf(in.intValue());
				}
				String path = Global.getUserfilesBaseDir() + "upload/contract/" + mRecordID;
				ContractTemplate ctl = contractTemplateService.get(mRecordID);
				String fileName = ctl.getTemplateType() + ctl.getProductType() + ".docx";
				if (!"1".equals(ctl.getIsuploadfile())) {
					MsgObj.MsgError("没有上传模板！"); // 设置错误信息
				} else {
					mFilePath = path + File.separator + fileName; // 取得文档存储路径
				}

				mDocType = MsgObj.GetMsgByName("DOC_TYPE");
				/**
				 * @reqno:H1503190045
				 * @date-designer:20150619-jiaoyang
				 * @date-author:20150619-jiaoyang:增加判断，当修改或查看附件中的文档时读取附件表中的文件信息，然后读取对应的文件。
				 */
				// if ("baseinfo".equals(mDocType)) {
				// FileInfoService fileInfoService = (FileInfoService)
				// ServiceContext.findBean(FileInfoService.BEAN_PORXY_ID);
				// FileInfo fileInfo = fileInfoService.loadFileInfo(mRecordID);
				// if (fileInfo != null) {
				// mFilePath = fileInfo.getFilePath() + "/" +
				// fileInfo.getFileName() + "." + fileInfo.getFileExt();
				// if (MsgObj.MsgFileLoad(mFilePath)) // 调入文档内容
				// {
				// MsgObj.SetMsgByName("STATUS", "打开文件成功!"); // 设置状态信息
				// MsgObj.MsgError(""); // 清除错误信息
				// } else {
				// MsgObj.MsgError("打开文件失败!"); // 设置错误信息
				// }
				// } else {
				// mFilePath = mFilePath + "/" + mRecordID + mFileType; //
				// 如果保存为文件，则填写文件路径
				// }
				// } else {
				// DocumentFileService documentFileService =
				// (DocumentFileService)
				// ServiceContext.findBean(DocumentFileService.BEAN_PORXY_ID);
				// DocumentFile documentFile =
				// documentFileService.findDocumentFileByRecordID(mRecordID);
				// if (documentFile != null) {
				// mFilePath = server_path + "/" + documentFile.getFilepath();
				// if (MsgObj.MsgFileLoad(mFilePath)) // 调入文档内容
				// {
				// MsgObj.SetMsgByName("STATUS", "打开文件成功!"); // 设置状态信息
				// MsgObj.MsgError(""); // 清除错误信息
				// } else {
				// MsgObj.MsgError("打开文件失败!"); // 设置错误信息
				// }
				// } else {
				// mFilePath = server_path + "/" + mFilePath + "/" + mRecordID +
				// mFileType; // 如果保存为文件，则填写文件路径
				// }
				// }
				if (MsgObj.MsgFileLoad(mFilePath)) {
					// 调入文档内容
					MsgObj.SetMsgByName("STATUS", "打开文件成功!"); // 设置状态信息
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("打开文件失败!"); // 设置错误信息
				}
				// 如果保存为文件，则填写文件路径
				MsgObj.MsgTextClear(); // 清除文本信息
			} else if (mOption.equalsIgnoreCase("SAVEFILE")) {// 下面的代码为保存文件在服务器的数据库里
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
				mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
				mFileSize = MsgObj.MsgFileSize(); // 取得文档大小
				mFileBody = MsgObj.MsgFileBody(); // 取得文档内容
				String webFilePath = MsgObj.GetMsgByName("FILE_PATH");
				if (!StringUtils.isEmpty(webFilePath) || webFilePath.equals("/")) {
					// 非正确路径,采用默认路径
					// 临时添加文档保存时的调试语句
				} else {
					// 正确路径
					mFilePath = webFilePath;
				}
				mDocType = MsgObj.GetMsgByName("DOC_TYPE");
				/**
				 * @reqno:H1503190045
				 * @date-designer:20150619-jiaoyang
				 * @date-author:20150619-jiaoyang:增加判断，当修改附件中的文档时读取附件表中的文件信息，然后保存。
				 */
				// if ("baseinfo".equals(mDocType)) {
				// FileInfoService fileInfoService = (FileInfoService)
				// ServiceContext.findBean(FileInfoService.BEAN_PORXY_ID);
				// FileInfo fileInfo = fileInfoService.loadFileInfo(mRecordID);
				// if (fileInfo != null) {
				// mFilePath = fileInfo.getFilePath() + "/" +
				// fileInfo.getFileName() + "." + fileInfo.getFileExt();
				// } else {
				// // 文件夹不存在就自动创建
				// if (!new File(mFilePath).isDirectory()) {
				// new File(mFilePath).mkdirs();
				// }
				// mFilePath = mFilePath + "/" + mRecordID + mFileType; //
				// 如果保存为文件，则填写文件路径
				// }
				//
				// mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得处理人名称;
				// mDescript = MsgObj.GetMsgByName("DESCRIPT"); // 版本说明
				// MsgObj.MsgTextClear(); // 清除文本信息
				// if (SaveBaseInfoFile()) // 保存文档内容
				// {
				// MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
				// MsgObj.MsgError(""); // 清除错误信息
				// } else {
				// MsgObj.MsgError("保存失败!"); // 设置错误信息
				// }
				// if (MsgObj.MsgFileSave(mFilePath)) // 保存文件
				// {
				// MsgObj.SetMsgByName("STATUS", "保存文件成功!"); // 设置状态信息
				// MsgObj.MsgError(""); // 清除错误信息
				// } else {
				// MsgObj.MsgError("保存文件失败!"); // 设置错误信息
				// }
				// } else {
				// DocumentFileService documentFileService =
				// (DocumentFileService)
				// ServiceContext.findBean(DocumentFileService.BEAN_PORXY_ID);
				// DocumentFile documentFile =
				// documentFileService.findDocumentFileByRecordID(mRecordID);
				// if (documentFile != null) {
				// mFilePath = documentFile.getFilepath();
				// } else {
				// // 文件夹不存在就自动创建
				// if (!new File(server_path + "/" + mFilePath).isDirectory()) {
				// new File(server_path + "/" + mFilePath).mkdirs();
				// }
				// mFilePath = mFilePath + "/" + mRecordID + mFileType; //
				// 如果保存为文件，则填写文件路径
				// }
				//
				// mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得处理人名称;
				// mDescript = MsgObj.GetMsgByName("DESCRIPT"); // 版本说明
				// MsgObj.MsgTextClear(); // 清除文本信息
				// if (SaveFile()) // 保存文档内容
				// {
				// MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
				// MsgObj.MsgError(""); // 清除错误信息
				// } else {
				// MsgObj.MsgError("保存失败!"); // 设置错误信息
				// }
				// if (MsgObj.MsgFileSave(server_path + "/" + mFilePath)) //
				// 保存文件
				// {
				// MsgObj.SetMsgByName("STATUS", "保存文件成功!"); // 设置状态信息
				// MsgObj.MsgError(""); // 清除错误信息
				// } else {
				// MsgObj.MsgError("保存文件失败!"); // 设置错误信息
				// }
				// }
				MsgObj.MsgFileClear(); // 清除文档内容
			} else if (mOption.equalsIgnoreCase("LOADTEMPLATE")) {// 下面的代码为打开服务器数据库里的模板文件
				mTemplate = MsgObj.GetMsgByName("TEMPLATE"); // 取得模板文档类型
				// 本段处理是否调用文档时打开模版，
				// 还是套用模版时打开模版。
				mCommand = MsgObj.GetMsgByName("COMMAND"); // 取得客户端定义的变量COMMAND值
				if (mCommand.equalsIgnoreCase("INSERTFILE")) {
					if (MsgObj.MsgFileLoad(server_path + "/" + mFilePath + "/base/weboffice/Document/" + mTemplate))// 调入模板文档
					{
						MsgObj.SetMsgByName("STATUS", "打开模板成功!"); // 设置状态信息
						MsgObj.MsgError(""); // 清除错误信息
					} else {
						MsgObj.MsgError("打开模板失败!"); // 设置错误信息
					}
				} else {
					MsgObj.MsgTextClear(); // 清除文本信息
					if (LoadTemplate()) // 调入模板文档
					{
						MsgObj.MsgFileBody(mFileBody); // 将文件信息打包
						MsgObj.SetMsgByName("STATUS", "打开模板成功!"); // 设置状态信息
						MsgObj.MsgError(""); // 清除错误信息
					} else {
						MsgObj.MsgError("打开模板失败!"); // 设置错误信息
					}
				}
			} else if (mOption.equalsIgnoreCase("SAVETEMPLATE")) {// 下面的代码为保存模板文件在服务器的数据库里
				mTemplate = MsgObj.GetMsgByName("TEMPLATE"); // 取得文档编号
				mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
				mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
				mFileSize = MsgObj.MsgFileSize(); // 取得文档大小
				mFileBody = MsgObj.MsgFileBody(); // 取得文档内容
				String version = "1";// 版本号
				ContractUploadFiles cuf = new ContractUploadFiles();
				cuf.setModuleId(mTemplate);
				List<ContractUploadFiles> listcuf = contractUploadFilesService.findList(cuf); // 获取上传记录
				if (listcuf != null && listcuf.size() > 0) {
					Integer in = new Integer(listcuf.get(0).getVersionNo());
					version = String.valueOf(in.intValue());
				}
				String path = Global.getUserfilesBaseDir() + "upload/contract/" + mTemplate;
				ContractTemplate ctl = contractTemplateService.get(mTemplate);
				String fileName = ctl.getTemplateType() + ctl.getProductType() + ".docx";
				if (!"1".equals(ctl.getIsuploadfile())) {
					MsgObj.MsgError("没有上传模板！"); // 设置错误信息
				} else {
					mFilePath = path + File.separator + fileName; // 取得文档存储路径
				}
				// mFilePath = "c:/mcp/111.doc"; // 如果保存为文件，则填写文件路径
				MsgObj.MsgFileSave(mFilePath);
				// mDescript = "OA系统公文模板"; // 版本说明
				MsgObj.MsgTextClear();
				if (SaveTemplate()) // 保存模板文档内容
				{
					MsgObj.SetMsgByName("STATUS", "保存模板成功!"); // 设置状态信息
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("保存模板失败!"); // 设置错误信息
				}
				MsgObj.MsgFileClear();
			} else if (mOption.equalsIgnoreCase("LOADBOOKMARKS")) {// 下面的代码为取得文档标签
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				mTemplate = MsgObj.GetMsgByName("TEMPLATE"); // 取得模板编号
				mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
				mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
				MsgObj.MsgTextClear();
				if (LoadBookMarks()) {
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("装入标签信息失败!"); // 设置错误信息
				}
			} else if (mOption.equalsIgnoreCase("SAVEBOOKMARKS")) {// 下面的代码为取得标签文档内容

				mTemplate = MsgObj.GetMsgByName("TEMPLATE"); // 取得模板编号
				if (SaveBookMarks()) {
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("保存标签信息失败!"); // 设置错误信息
				}
				MsgObj.MsgTextClear(); // 清除文本信息
			} else if (mOption.equalsIgnoreCase("LISTBOOKMARKS")) {// 下面的代码为显示标签列表
				/*
				 * @date-author: 20140827-zhanzhengqiang
				 * 
				 * @desc: 获取当前用户所属域
				 */
				// mDomainId = MsgObj.GetMsgByName("domainId");
				// /* @end */
				// MsgObj.MsgTextClear(); // 清除文本信息
				// if (ListBookmarks(request, response)) {
				// MsgObj.SetMsgByName("BOOKMARK", mBookmark); // 将用户名列表打包
				// MsgObj.SetMsgByName("DESCRIPT", mDescript); // 将说明信息列表打包
				// /**
				// * @reqno:H1501090015
				// * @date-designer:20150213-yuanshisong
				// *
				// * @date-author:20150213-yuanshisong:书签分域混乱问题,
				// 查出信息后应将domainId再次传回到页面,以便下次查询
				// */
				// MsgObj.SetMsgByName("domainId", mDomainId); //
				// 将domainId再次传回到页面
				// MsgObj.MsgError(""); // 清除错误信息
				// } else {
				// MsgObj.MsgError("调入标签失败!"); // 设置错误信息
				// }
			} else if (mOption.equalsIgnoreCase("SAVEASHTML")) {// 下面的代码为存为HTML页面
				mHtmlName = MsgObj.GetMsgByName("HTMLNAME"); // 取得文件名称
				mDirectory = MsgObj.GetMsgByName("DIRECTORY"); // 取得目录名称
				MsgObj.MsgTextClear();
				if (mDirectory.trim().equalsIgnoreCase("")) {
					mFilePath = mFilePath + "/base/weboffice/HTML";
				} else {
					mFilePath = mFilePath + "/base/weboffice/HTML/" + mDirectory;
				}
				MsgObj.MakeDirectory(mFilePath); // 创建路径
				if (MsgObj.MsgFileSave(mFilePath + "/" + mHtmlName)) // 保存HTML文件
				{
					MsgObj.MsgError(""); // 清除错误信息
					MsgObj.SetMsgByName("STATUS", "保存HTML成功!"); // 设置状态信息
				} else {
					MsgObj.MsgError("保存HTML失败!"); // 设置错误信息
				}
				MsgObj.MsgFileClear();
			} else if (mOption.equalsIgnoreCase("SAVEIMAGE")) {// 下面的代码为存为HTML图片页面
				mHtmlName = MsgObj.GetMsgByName("HTMLNAME"); // 取得文件名称
				mDirectory = MsgObj.GetMsgByName("DIRECTORY"); // 取得目录名称
				MsgObj.MsgTextClear();
				if (mDirectory.trim().equalsIgnoreCase("")) {
					mFilePath = mFilePath + "/base/weboffice/HTMLIMAGE";
				} else {
					mFilePath = mFilePath + "/base/weboffice/HTMLIMAGE/" + mDirectory;
				}
				MsgObj.MakeDirectory(mFilePath); // 创建路径
				if (MsgObj.MsgFileSave(mFilePath + "/" + mHtmlName)) // 保存HTML文件
				{
					MsgObj.MsgError(""); // 清除错误信息
					MsgObj.SetMsgByName("STATUS", "保存HTML图片成功!"); // 设置状态信息
				} else {
					MsgObj.MsgError("保存HTML图片失败!"); // 设置错误信息
				}
				MsgObj.MsgFileClear();
			} else if (mOption.equalsIgnoreCase("SAVEASPAGE")) {// 下面的代码为将手写批注存为HTML图片页面
				mHtmlName = MsgObj.GetMsgByName("HTMLNAME"); // 取得文件名称
				mDirectory = MsgObj.GetMsgByName("DIRECTORY"); // 取得目录名称
				MsgObj.MsgTextClear();
				if (mDirectory.trim().equalsIgnoreCase("")) {
					mFilePath = mFilePath + "\\weboffice\\HTML";
				} else {
					mFilePath = mFilePath + "\\weboffice\\HTML\\" + mDirectory;
				}
				MsgObj.MakeDirectory(mFilePath); // 创建路径
				if (MsgObj.MsgFileSave(mFilePath + "\\" + mHtmlName)) // 保存HTML文件
				{
					MsgObj.MsgError(""); // 清除错误信息
					MsgObj.SetMsgByName("STATUS", "保存批注HTML图片成功!"); // 设置状态信息
				} else {
					MsgObj.MsgError("保存批注HTML图片失败!"); // 设置错误信息
				}
				MsgObj.MsgFileClear();
			} else if (mOption.equalsIgnoreCase("INSERTFILE")) {// 下面的代码为插入文件
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
				mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
				// 提供远程插入对象功能
				String mObjectId = MsgObj.GetMsgByName("OBJECT_ID"); // 取得插入对象对应id
				String mObjectBookmark = MsgObj.GetMsgByName("OBJECT_BOOKMARK"); // 取得插入对象对应id
				MsgObj.MsgTextClear();
				if (mObjectId != null && !mObjectId.equals("")) {
					// DocumentFileService documentFileService =
					// (DocumentFileService)
					// ServiceContext.findBean(DocumentFileService.BEAN_PORXY_ID);
					// DocumentFile documentFile =
					// documentFileService.findDocumentFileByRecordID(mObjectId);
					// if (documentFile != null) {
					// // 插入正文内容
					// if (MsgObj.MsgFileLoad(server_path + File.separator +
					// documentFile.getFilepath())) {// 从服务器文件夹中调入文档
					// MsgObj.SetMsgByName("POSITION", mObjectBookmark); //
					// 设置插入的位置[书签]，在模板中定义好标签位置
					// MsgObj.SetMsgByName("STATUS", "插入文件成功!"); // 设置状态信息
					// MsgObj.MsgError(""); // 清除错误信息
					// } else {
					// MsgObj.MsgError("插入文件失败!"); // 设置错误信息
					// }
					// } else {
					// MsgObj.MsgError("插入文件失败!"); // 设置错误信息
					// }
				}
			} else if (mOption.equalsIgnoreCase("INSERTIMAGE")) {// 下面的代码为插入服务器图片
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				mLabelName = MsgObj.GetMsgByName("LABELNAME"); // 标签名
				mImageName = MsgObj.GetMsgByName("IMAGENAME"); // 图片名
				mFilePath = mFilePath + "/base/weboffice/Document/" + mImageName; // 图片在服务器的完整路径
				mFileType = mImageName.substring(mImageName.length() - 4).toLowerCase(); // 取得文件的类型
				MsgObj.MsgTextClear();
				if (MsgObj.MsgFileLoad(mFilePath)) // 调入图片
				{
					MsgObj.SetMsgByName("IMAGETYPE", mFileType); // 指定图片的类型
					MsgObj.SetMsgByName("POSITION", mLabelName); // 设置插入的位置[书签对象名]
					MsgObj.SetMsgByName("STATUS", "插入图片成功!"); // 设置状态信息
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("插入图片失败!"); // 设置错误信息
				}
			} else if (mOption.equalsIgnoreCase("PUTFILE")) {// 下面的代码为请求上传文件操作
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				mFileBody = MsgObj.MsgFileBody(); // 取得文档内容
				mLocalFile = MsgObj.GetMsgByName("LOCALFILE"); // 取得本地文件名称
				mRemoteFile = MsgObj.GetMsgByName("REMOTEFILE"); // 取得远程文件名称
				MsgObj.MsgTextClear(); // 清除文本信息
				mFilePath = mFilePath + "/base/weboffice/Document/" + mRemoteFile;
				if (MsgObj.MsgFileSave(mFilePath)) // 调入文档
				{
					MsgObj.SetMsgByName("STATUS", "保存上传文件成功!"); // 设置状态信息
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("上传文件失败!"); // 设置错误信息
				}
			} else if (mOption.equalsIgnoreCase("GETFILE")) {// 下面的代码为请求下载文件操作
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				mLocalFile = MsgObj.GetMsgByName("LOCALFILE"); // 取得本地文件名称
				mRemoteFile = MsgObj.GetMsgByName("REMOTEFILE"); // 取得远程文件名称
				MsgObj.MsgTextClear(); // 清除文本信息
				mFilePath = mFilePath + "/base/weboffice/Document/" + mRemoteFile;
				if (MsgObj.MsgFileLoad(mFilePath)) // 调入文档内容
				{
					MsgObj.SetMsgByName("STATUS", "保存下载文件成功!"); // 设置状态信息
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("下载文件失败!"); // 设置错误信息
				}
			} else if (mOption.equalsIgnoreCase("DATETIME")) {// 下面的代码为请求取得服务器时间
				MsgObj.MsgTextClear(); // 清除文本信息
				// 标准日期格式字串，如2005-8-16 10:20:35
				MsgObj.SetMsgByName("DATETIME", "2006-01-01 10:24:24");
			} else if (mOption.equalsIgnoreCase("SENDMESSAGE")) {// 下面的代码为Web页面请求信息[扩展接口]
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				mFileName = MsgObj.GetMsgByName("FILENAME"); // 取得文档名称
				mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
				mCommand = MsgObj.GetMsgByName("COMMAND"); // 取得操作类型InportText/ExportText
				mContent = MsgObj.GetMsgByName("CONTENT"); // 取得文本信息Content
				mOfficePrints = MsgObj.GetMsgByName("OFFICEPRINTS");
				/**
				 * @reqno:H1508070010
				 * @date-designer:20150814-jiaoyang
				 * @date-author:20150814-jiaoyang:从前台获取formid，用于打印份数控制时获取剩余打印份数。
				 */
				String mFormId = MsgObj.GetMsgByName("MFORMID");
				MsgObj.MsgTextClear();
				MsgObj.MsgFileClear();
				if (mCommand.equalsIgnoreCase("INPORTTEXT")) {
					if (LoadContent()) {
						MsgObj.SetMsgByName("CONTENT", mContent);
						MsgObj.SetMsgByName("STATUS", "导入成功!"); // 设置状态信息
						MsgObj.MsgError(""); // 清除错误信息
					} else {
						MsgObj.MsgError("导入失败!"); // 设置错误信息
					}
				} else if (mCommand.equalsIgnoreCase("EXPORTTEXT")) {
					if (SaveContent()) {
						MsgObj.SetMsgByName("STATUS", "导出成功!"); // 设置状态信息
						MsgObj.MsgError(""); // 清除错误信息
					} else {
						MsgObj.MsgError("导出失败!"); // 设置错误信息
					}
				} else if (mCommand.equalsIgnoreCase("WORDTABLE")) {
					if (GetWordTable()) {
						MsgObj.SetMsgByName("COLUMNS", String.valueOf(mColumns));// 列
						MsgObj.SetMsgByName("CELLS", String.valueOf(mCells)); // 行
						MsgObj.SetMsgByName("WORDCONTENT", mTableContent); // 表格内容
						MsgObj.SetMsgByName("STATUS", "增加和填充成功成功!"); // 设置状态信息
						MsgObj.MsgError(""); // 清除错误信息
					} else {
						MsgObj.MsgError("增加表格行失败!"); // 设置错误信息
					}
				} else if (mCommand.equalsIgnoreCase("COPIES")) { // 打印限制

					/**
					 * @reqno:H1504220035
					 * @date-designer:20150519-jiaoyang
					 * @date-author:20150519-jiaoyang:控制打印份数，当份数超过可打份数时返回状态为剩余份数 
					 *                                                           *
					 *                                                           10
					 *                                                           ，
					 *                                                           当未超过时返回1
					 *                                                           ，
					 *                                                           当出现异常未取到限制份数时返回000
					 */
					/**
					 * @reqno:H1503190046
					 * @date-designer:20150519-jiaoyang
					 * @date-author:20150519-jiaoyang:控制打印份数，当份数超过可打份数时返回状态为剩余份数 
					 *                                                           *
					 *                                                           10
					 *                                                           ，
					 *                                                           当未超过时返回1
					 *                                                           ，
					 *                                                           当出现异常未取到限制份数时返回000
					 */
					// WorkitemRunService workitemRunService =
					// (WorkitemRunService)
					// ServiceContext.findBean(WorkitemRunService.BEAN_PORXY_ID);
					mCopies = Integer.parseInt(mOfficePrints);
					/**
					 * @reqno:H1508070010
					 * @date-designer:20150814-jiaoyang
					 * @date-author:20150814-jiaoyang:获取剩余份数，如果formid不为空则用formid取，如果formid为空用recordid取。
					 */
					int shengyu = 0;
					// if (StringUtils.isNotEmpty(mFormId)) {
					// shengyu = workitemRunService.getCtrlPrint(mFormId);
					// } else {
					// shengyu = workitemRunService.getCtrlPrint(mRecordID);
					// }
					if (shengyu < 0) {
						MsgObj.SetMsgByName("STATUS", "000");
						MsgObj.MsgError("");
					} else {
						if (mCopies <= shengyu) {
							if (UpdateCopies(shengyu - mCopies, mFormId)) {
								MsgObj.SetMsgByName("STATUS", "1");
								MsgObj.MsgError("");
							}
						} else {
							MsgObj.SetMsgByName("STATUS", shengyu + "0");
							MsgObj.MsgError(shengyu + "0");
						}
					}
				} else {
					MsgObj.MsgError("客户端Web发送数据包命令没有合适的处理函数![" + mCommand + "]");
					MsgObj.MsgTextClear();
					MsgObj.MsgFileClear();
				}
			} else if (mOption.equalsIgnoreCase("SAVEPAGE")) {// 下面的代码为保存为全文批注格式文件

				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				MsgObj.MsgTextClear(); // 清除文本信息
				mFilePath = mFilePath + "/base/weboffice/Document/" + mRecordID + ".pgf"; // 全文批注文件的完整路径
				if (MsgObj.MsgFileSave(mFilePath)) // 保存全文批注文件
				{
					MsgObj.SetMsgByName("STATUS", "保存全文批注成功!"); // 设置状态信息
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("保存全文批注失败!"); // 设置错误信息
				}
			} else if (mOption.equalsIgnoreCase("LOADPAGE")) {// 下面的代码为调入全文批注格式文件
				mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
				MsgObj.MsgTextClear(); // 清除文本信息
				mFilePath = mFilePath + "/base/weboffice/Document/" + mRecordID + ".pgf"; // 全文批注文件的完整路径
				if (MsgObj.MsgFileLoad(mFilePath)) // 调入文档内容
				{
					MsgObj.SetMsgByName("STATUS", "打开全文批注成功!"); // 设置状态信息
					MsgObj.MsgError(""); // 清除错误信息
				} else {
					MsgObj.MsgError("打开全文批注失败!"); // 设置错误信息
				}
			}
			MsgObj.Send(response);
		} catch (Exception e) {
			try {
				MsgObj.MsgTextClear();
				MsgObj.MsgFileClear();
			} catch (Exception e1) {
				LOG.error(e1.getMessage(), e1);
			}
		} finally {
			ServletOutputStream out = response.getOutputStream();
			if (out != null) {
				out.close();
			}
		}
	}

	// ************* 文档、模板管理代码 开始 *******************************
	// 保存文档，如果文档存在，则覆盖，不存在，则添加
	// private boolean SaveFile() {
	// boolean mResult = false;
	// DocumentFileService documentFileService = (DocumentFileService)
	// ServiceContext.findBean(DocumentFileService.BEAN_PORXY_ID);
	// DocumentFile documentFile =
	// documentFileService.findDocumentFileByRecordID(mRecordID);
	// if (documentFile != null) {
	// documentFile.setFilename(mFileName);
	// documentFile.setFiletype(mFileType);
	// documentFile.setFilesize(new Long(mFileSize));
	// documentFile.setFiledate(new Date());
	// documentFile.setFilebody(null);
	// documentFile.setFilepath(mFilePath);
	// documentFile.setUsername(mUserName);
	// documentFile.setDescript(mDescript);
	// documentFileService.saveDocumentFile(documentFile);
	// mResult = true;
	// } else {
	// documentFile = new DocumentFile();
	// documentFile.setRecordid(mRecordID);
	// documentFile.setFilename(mFileName);
	// documentFile.setFiletype(mFileType);
	// documentFile.setFilesize(new Long(mFileSize));
	// documentFile.setFiledate(new Date());
	// documentFile.setFilebody(null);
	// documentFile.setFilepath(mFilePath);
	// documentFile.setUsername(mUserName);
	// documentFile.setDescript(mDescript);
	// documentFileService.saveDocumentFile(documentFile);
	// mResult = true;
	// }
	// mFileBody = null;
	// return (mResult);
	// }

	// private boolean SaveBaseInfoFile() {
	// boolean mResult = false;
	// FileInfoService fileInfoService = (FileInfoService)
	// ServiceContext.findBean(FileInfoService.BEAN_PORXY_ID);
	// FileInfo fileInfo = fileInfoService.loadFileInfo(mRecordID);
	// if (fileInfo != null) {
	// fileInfo.setFileSize(new Long(mFileSize));
	// fileInfo.setUploadDate(new Date());
	// fileInfoService.saveFileInfo(fileInfo);
	// mResult = true;
	// }
	// mFileBody = null;
	// return (mResult);
	// }

	// 调出模板文档，将模板文档内容保存在mFileBody里，以便进行打包
	private boolean LoadTemplate() {
		// boolean mResult = false;
		// TemplateFileService templateFileService = (TemplateFileService)
		// ServiceContext.findBean(TemplateFileService.BEAN_PORXY_ID);
		// TemplateFile templateFile =
		// templateFileService.findTemplateFileByRecordID(mTemplate);
		// if (templateFile != null) {
		// String strSql = "SELECT FileBody,FileSize" +
		// " FROM Weboffice_Template_File WHERE RecordID='" + mTemplate + "'";
		// LookUp lookUp = (LookUp)
		// ServiceContext.findBean(LookUp.BEAN_PORXY_ID);
		// mFileBody = lookUp.getBytes(strSql, "FileBody");
		// mResult = true;
		// }
		// return (mResult);
		return true;
	}

	// 保存模板文档，如果模板文档存在，则覆盖，不存在，则添加
	private boolean SaveTemplate() {
		// boolean mResult = false;
		// TemplateFileService templateFileService = (TemplateFileService)
		// ServiceContext.findBean(TemplateFileService.BEAN_PORXY_ID);
		// TemplateFile templateFile =
		// templateFileService.findTemplateFileByRecordID(mTemplate);
		// if (templateFile != null) {
		// templateFile.setFiletype(mFileType);
		// templateFile.setFilesize(new Long(mFileSize));
		// templateFile.setFiledate(new Date());
		// templateFile.setFilebody(Hibernate.createBlob(mFileBody));
		// templateFile.setFilepath(mFilePath);
		// templateFile.setUsername(mUserName);
		// templateFileService.saveTemplateFile(templateFile);
		// mResult = true;
		// } else {
		// templateFile = new TemplateFile();
		// templateFile.setRecordid(mTemplate);
		// templateFile.setFilename(mFileName);
		// templateFile.setFiletype(mFileType);
		// templateFile.setFilesize(new Long(mFileSize));
		// templateFile.setFiledate(new Date());
		// templateFile.setFilebody(Hibernate.createBlob(mFileBody));
		// templateFile.setFilepath(mFilePath);
		// templateFile.setUsername(mUserName);
		// templateFile.setDescript(mDescript);
		// templateFileService.saveTemplateFile(templateFile);
		// mResult = true;
		// }
		// mFileBody = null;
		// return (mResult);
		return true;
	}

	// ************* 文档、模板管理代码 结束 *******************************

	// ************* 版本管理代码 开始 *******************************
	// 列出所有版本信息
	// 调入选中版本，通过文件号调用mFileID,并把文件放入mFileBody里，以便进行打包
	// 保存版本，将该版本文件存盘，并将说明信息也保存起来
	// ************* 版本管理代码 结束 *******************************

	// ************ 标签管理代码 开始 *******************************
	// 取得书签列表
	// private boolean ListBookmarks(HttpServletRequest request,
	// HttpServletResponse response) {
	// boolean mResult = false;
	// mBookmark = "";
	// mDescript = "";
	// BookmarksService bookmarksService = (BookmarksService)
	// ServiceContext.findBean(BookmarksService.BEAN_PORXY_ID);
	// /*
	// * @date-author: 20140828-zhanzhengqiang
	// *
	// * @desc: 分域进行查询
	// */
	// // List list = bookmarksService.findAllBookmarks();
	// List list = bookmarksService.findAllBookmarks(mDomainId);
	// /* @end */
	// for (int i = 0; i < list.size(); i++) {
	// Bookmarks bookmarks = (Bookmarks) list.get(i);
	// mBookmark += bookmarks.getBookmarkname() + "\r\n"; // 用户名列表
	// mDescript += bookmarks.getBookmarkdesc() + "\r\n"; //
	// 如果说明信息里有回车，则将回车变成>符号
	// mResult = true;
	// }
	//
	// try {
	// // 获取当前页面修要的编码类型
	// InputStream stringBufferInputStream = new
	// StringBufferInputStream(mDescript);
	// InputStreamReader inputStreamReader = new
	// InputStreamReader(stringBufferInputStream);
	// String encoding = inputStreamReader.getEncoding();
	// mDescript = new String(mDescript.getBytes("GBK"), encoding);
	// mBookmark = new String(mBookmark.getBytes("GBK"), encoding);
	// } catch (Exception e) {
	// // e.printStack Trace();
	// LOG.error(e.getMessage(), e);
	// }
	//
	// return (mResult);
	// }

	// 装入书签
	private boolean LoadBookMarks() {
		// boolean mResult = false;
		// BookmarksService bookmarksService = (BookmarksService)
		// ServiceContext.findBean(BookmarksService.BEAN_PORXY_ID);
		// List list = bookmarksService.loadBookmarksList(mTemplate);
		// for (int i = 0; i < list.size(); i++) {
		// Bookmarks bookmarks = (Bookmarks) list.get(i);
		// String mBookMarkName = bookmarks.getBookmarkname();
		// String mBookMarkValue = bookmarks.getBookmarktext();
		// MsgObj.SetMsgByName(mBookMarkName, mBookMarkValue);
		// mResult = true;
		// }
		// return (mResult);
		return true;
	}

	// 保存书签
	private boolean SaveBookMarks() {
		// boolean mResult = false;
		// if (mTemplate != null) {
		// TemplateBookmarksService templateBookmarksService =
		// (TemplateBookmarksService)
		// ServiceContext.findBean(TemplateBookmarksService.BEAN_PORXY_ID);
		// templateBookmarksService.deleteTemplateBookmarks(mTemplate);
		// for (int mIndex = 7; mIndex <= MsgObj.GetFieldCount() - 1; mIndex++)
		// {
		// TemplateBookmarks templateBookmarks = new TemplateBookmarks();
		// String mBookMarkName = MsgObj.GetFieldName(mIndex);
		// templateBookmarks.setRecordid(mTemplate);
		// templateBookmarks.setBookmarkname(mBookMarkName);
		// templateBookmarksService.saveTemplateBookmarks(templateBookmarks);
		// }
		// mResult = true;
		// }
		// return (mResult);
		return true;
	}

	// ************ 标签管理代码 结束 *******************************

	// ************ 签章管理代码 开始 *******************************
	// 取得签名列表
	// 调入签名纪录
	// 保存签名
	// 列出所有签名
	// ************ 签章管理代码 结束 *******************************

	// ************ 扩展功能代码 开始 *******************************
	// 调出所对应的文本
	private boolean LoadContent() {
		boolean mResult = false;
		// 打开数据库
		// 根据 mRecordID 或 mFileName 等信息
		// 提取文本信息付给mContent即可。
		// 本演示假设取得的文本信息如下：
		mContent = "";
		mContent += "本文的纪录号：" + mRecordID + "\n";
		mContent += "本文的文件名：" + mFileName + "\n";
		mContent += "这个部分请自己加入，和你们的数据库结合起来就可以了\n";
		return mResult;
	}

	// 保存所对应的文本
	private boolean SaveContent() {
		boolean mResult = false;
		// 打开数据库
		// 根据 mRecordID 或 mFileName 等信息
		// 插入文本信息 mContent里的文本到数据库中即可。
		mResult = true;
		return (mResult);
	}

	// 增加行并填充表格内容
	private boolean GetWordTable() {
		mColumns = 3;
		mCells = 8;
		MsgObj.MsgTextClear();
		MsgObj.SetMsgByName("COLUMNS", String.valueOf(mColumns)); // 设置表格行
		MsgObj.SetMsgByName("CELLS", String.valueOf(mCells)); // 设置表格列
		// 该部分内容可以从数据库中读取
		boolean mResult = true;
		try {
			for (int i = 1; i <= mColumns; i++) {
				for (int n = 1; n <= mCells; n++) {
					MsgObj.SetMsgByName(String.valueOf(i) + String.valueOf(n), "内容" + new Date());
				}
			}
		} catch (Exception e) {
			mResult = false;
		}
		return mResult;
	}

	/**
	 * @reqno:H1504220035
	 * @date-designer:20150519-jiaoyang
	 * @date-author:20150519-jiaoyang:更新打印份数
	 */
	/**
	 * @reqno:H1503190046
	 * @date-designer:20150519-jiaoyang
	 * @date-author:20150519-jiaoyang:更新打印份数
	 */
	// 更新打印份数
	private boolean UpdateCopies(int mLeftCopies, String mRecordID) {
		// boolean mResult = true;
		// WorkitemRunService workitemRunService = (WorkitemRunService)
		// ServiceContext.findBean(WorkitemRunService.BEAN_PORXY_ID);
		// CtrlPrint ctrlP = new CtrlPrint();
		// ctrlP.setRecordId(mRecordID);
		// ctrlP.setCopiesNum(mLeftCopies);
		// workitemRunService.saveOrUpdateCopies(ctrlP);
		// return mResult;
		return true;
	}
	// ************ 扩展功能代码 结束 *******************************
}
