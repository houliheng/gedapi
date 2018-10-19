package com.gq.ged.upload.controller;

import com.gq.ged.common.Errors;
import com.gq.ged.common.exception.business.BusinessException;
import com.gq.ged.common.resp.ResponseEntity;
import com.gq.ged.common.resp.ResponseEntityUtil;
import com.gqhmt.fs.FileService;
import com.gqhmt.fs.fdfs.FastDfsGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by wyq_tomorrow on 2017/8/24.
 */
@Api(value = "fast上传文件", description = "fast上传文件")
@RestController
@RequestMapping(value = "/fast", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileFastController {

  @Resource
  private FileService fileService;

  @ApiOperation(value = "上传接口", notes = "上传接口")
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public ResponseEntity<String> uploadToFast(MultipartHttpServletRequest reques)
      throws IOException {
    Map<String, MultipartFile> fileMap = reques.getFileMap();
    String fileId = "";
    if (fileMap.size() == 0) {
      throw new BusinessException(Errors.UPLOAD_FILE_NOT_EXISTS_ERROR);
    }
    for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
      MultipartFile file = entry.getValue();
      String extName = "";
      String[] pathArray = file.getOriginalFilename().split("\\.");
      if (pathArray.length > 1) {
        extName = pathArray[1];
      }
      /*
       * fileId = fileService.uploadWithGroup(file.getBytes(), FastDfsGroup.PRI_FILE, new
       * FileMetadata() .withMeta("author", "wyq").withMeta("company", "gq").withExtName(extName));
       * FileCopyUtils.copyToByteArray((File) file
       */

      fileId = fileService.uploadWithGroup(file.getBytes(),
          FastDfsGroup.PRI_FILE, "jpg");
      System.out.println(fileId);
    }
    return ResponseEntityUtil.success(fileId);
  }

  @RequestMapping(value = "/getDownloadFileByte/{fileId}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> getDownloadFileByte(@PathVariable("fileId") String fileId)
      throws IOException {
    byte[] byteIo = fileService.download(fileId);
    return ResponseEntityUtil.success(byteIo);
  }
}
