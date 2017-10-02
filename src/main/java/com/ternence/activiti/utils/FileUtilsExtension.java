package com.ternence.activiti.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtilsExtension extends FileUtils {
	//日志，这样做是否线程安全？
	private static Logger logger = LoggerFactory.getLogger(FileUtilsExtension.class);

	/**
	 * 保存文件到规定的WEB-RES目录
	 * 
	 * @param multipart 上传的文件的包装对象
	 * @param request 请求对象
	 * @return 上传之后文件的对象,{@code null}如果文件上传失败
	 * @throws IOException 操作IO的过程中发生错误
	 */
	public static File saveUploadFile(MultipartFile multipart, HttpServletRequest request) throws IOException {
		if (multipart == null || multipart.getInputStream().available() == 0) {
			return null;
		}
		//保存文件，然后开始部署流程
		//这种方式得到的是WEB-RES在物理磁盘上的路径
		//在Linux上会获取到项目部署路径的WEB-RES文件夹
		//好奇怪，在Windows上不会获取tomcat部署项目的路劲
		//项目中已经保证WEB-RES目录存在，所以这里不用判断为null的情况
		String savePath = request.getSession().getServletContext().getRealPath("/WEB-RES");
		logger.info("保存路径:" + savePath);
		File uploadDir = new File(savePath + "/process");

		if (!uploadDir.exists()) {
			if (uploadDir.mkdirs()) {
				logger.info(uploadDir.getPath() + "目录创建成功");
			} else {
				logger.info(uploadDir.getPath() + "目录创建失败");
				return null;
			}
		}

		File targetFile = null;

		String originalFileName = multipart.getOriginalFilename();
		//得到文件名的后缀
		String suffix = FilenameUtils.getExtension(originalFileName);
		if (suffix != null && suffix.trim().length() > 0) {
			suffix = "." + suffix;
			logger.info("后缀" + suffix);
		} else {
			logger.info("无后缀");
		}
		//随机文件名
		String randomFileName = UUID.randomUUID().toString().replace("-", "");
		//创建临时文件保存内容
		targetFile = new File(uploadDir.getPath() + "/" + randomFileName + suffix);
		if (targetFile.createNewFile()) {
			logger.info("上传文件创建成功");
		} else {
			logger.info("上传文件创建失败");
			return null;
		}
		InputStream in = multipart.getInputStream();

		int len = 0;
		byte[] cache = new byte[1024];
		//只适合文本文件的保存
		while ((len = in.read(cache)) != -1) {

			logger.info("len=" + len);

			//写文件(注意后面的append要等于true)
			//注意这里一定要是[0-len]，因为默认的cache是1024字节，最后一次读取不一定有1024字节
			FileUtils.writeByteArrayToFile(targetFile, cache, 0, len, true);
		}
		in.close();
		//上传成功
		return targetFile;
	}
}
