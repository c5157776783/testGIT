package cn.standard.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.mysql.cj.util.StringUtils;

import cn.standard.pojo.Standard;
import cn.standard.service.StandardService;
import cn.standard.utils.PageSupport;

@Controller
@RequestMapping("/standard")
public class StandardController {
	private Logger logger = Logger.getLogger(StandardController.class);
	@Resource
	private StandardService standardService;
	
	@Resource
	private CommonsMultipartResolver multipartResolver;

	@RequestMapping("/standardlist.html")
	public String standardList(Model model, ServletRequest request,
			@RequestParam(value = "keys", required = false) String keys,
			@RequestParam(value = "_pageIndex", required = false) String _pageIndex) {
		logger.debug("INTO StandardController.standardList()-------------------");
		logger.debug("queryUserName：" + keys);
		List<Standard> standardList = null;
		// 设置页面容量
		int pageSize = 2;
		// 当前页码
		int pageIndex = 1;
		
		if (keys == null) {
			keys = "";
		}
		
		if (_pageIndex != null) {
			try {
				pageIndex = Integer.valueOf(_pageIndex);
			} catch (NumberFormatException e) {
				return "redirect:/standard/syserror.html";
			}
		}
		// 总数量（表）
		int totalCount = standardService.findTotalCount(keys);
		// 总页数
		PageSupport pages = new PageSupport();
		pages.setPageIndex(pageIndex);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);

		int totalPage = pages.getTotalPage();

		// 控制首页和尾页
		if (pageIndex < 1) {
			pageIndex = 1;
		} else if (pageIndex > totalPage) {
			pageIndex = totalPage;
		}

		standardList = standardService.findStandardList(null, keys, null, (pageIndex - 1) * pageSize, pageSize);
		model.addAttribute("standardList", standardList);
		model.addAttribute("keys", keys);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageIndex", pageIndex);

		return "standardlist";
	}

	@RequestMapping(value = "/syserror.html")
	public String sysError() {
		logger.debug("INTO StandardController.sysError()-------------------");
		return "syserror";
	}

	@RequestMapping(value = "/delete.html")
	@ResponseBody
	public boolean alreadyDelete(@RequestParam(value = "id") String[] id, HttpSession session) {
		logger.debug("INTO StandardController.alreadyDelete()-------------------");
		boolean flag = false;
		Integer[] _id = new Integer[id.length];
		for (int i = 0; i < id.length; i++) {
			_id[i] = Integer.valueOf(id[i]);
			List<Standard> standardList = standardService.findStandardList(_id[i], null, null, 0, 0);
			Integer count = standardService.deleteStandardById(_id[i]);
			if (count > 0) {
				for (Standard standard : standardList) {
					String path = session.getServletContext().getRealPath(
							"statics" + File.separator + "uploadfiles" + File.separator + standard.getPackagePath());
					File file = new File(path);
					if (file.exists() && file.isFile()) {
						file.delete();
					}
				}
				flag = true;
			}
		}
		return flag;
	}

	@RequestMapping(value = "/update/{id}.html", method = RequestMethod.GET)
	public String update(@PathVariable String id, Model model) {
		logger.debug("INTO StandardController.update()-------------------");
		List<Standard> standardList = standardService.findStandardList(Integer.valueOf(id), null, null, 0, 0);
		for (Standard standard : standardList) {
			model.addAttribute(standard);
		}
		return "update";
	}

	@RequestMapping(value = "/update.html", method = RequestMethod.POST)
	public String alreadyUpdate(@Valid Standard standard, BindingResult bindingResult, HttpServletRequest request,
			@RequestParam(value = "packagePath_", required = false) MultipartFile packagePath) {
//		 System.out.println(standard.getStdNum());
//		 System.out.println(standard.getZhname());
//		 System.out.println(standard.getKeys());
//		 System.out.println(standard.getVersion());
//		 System.out.println(standard.getReleaseDate());
//		 System.out.println(standard.getImplDate());
//		 System.out.println(standard.getPackagePath());

		if (bindingResult.hasErrors()) {
			List<ObjectError> ls = bindingResult.getAllErrors();
			for (int i = 0; i < ls.size(); i++) {
				System.out.println("error:" + ls.get(i));
			}
			logger.debug("update user validated has error=============================");
			return "update";
		}
		String flag = this.commons(standard, request, packagePath);
		Integer count = null;
		if ("empty".equals(flag)) {
			count = standardService.updateStandardById(standard);
		} else if (!"false".equals(flag)) {
			standard.setPackagePath(flag);
			count = standardService.updateStandardById(standard);
		}
		if (count > 0) {
			return "redirect:/standard/standardlist.html";
		}
		return "update";
	}

	@RequestMapping(value = "/insert.html", method = RequestMethod.GET)
	public String insert(@ModelAttribute Standard standard) {
		logger.debug("INTO StandardController.insert()-------------------");
		return "insert";
	}

	// 使用spring标签
	@RequestMapping(value = "/insert.html", method = RequestMethod.POST)
	public String insert1(@Valid Standard standard, BindingResult bindingResult, HttpServletRequest request,
			@RequestParam(value = "packagePath_", required = false) MultipartFile packagePath) {
		if (bindingResult.hasErrors()) {
			logger.debug("insert user validated has error=============================");
			return "insert";
		}
		String flag = this.commons(standard, request, packagePath);
		if (!"false".equals(flag) && !"empty".equals(flag)) {
			standard.setPackagePath(flag);
			Integer count = standardService.insertStandard(standard);
			if (count > 0) {
				return "redirect:/standard/standardlist.html";
			}
		}
		return "insert";
	}

	@RequestMapping(value = "/stdNumexist.html")
	@ResponseBody
	public String stdNumIsExit(@RequestParam String stdNum) {
		logger.debug("stdNumIsExit stdNum===================== " + stdNum);
//		HashMap<String, String> resultMap = new HashMap<String, String>();
//		if (StringUtils.isNullOrEmpty(stdNum)) {
//			resultMap.put("stdNum", "empty");
//		} else {
//			List<Standard> standardList = standardService.findStandardList(null, null, stdNum, 0, 0);
//			if (standardList.size() > 0){
//				resultMap.put("stdNum", "exist");
//			}else{
//				resultMap.put("stdNum", "noexist");
//			}
//		}
//		return JSONArray.toJSONString(resultMap);
		if (StringUtils.isNullOrEmpty(stdNum)) {
			return "exist";
		} else {
			List<Standard> standardList = standardService.findStandardList(null, null, stdNum, 0, 0);
			if (standardList.size() > 0)
				return "exist";
			else
				return "noexist";
		}
	}

	public String commons(Standard standard, HttpServletRequest request, MultipartFile _packagePath) {
		String packagePath = null;
		if (!_packagePath.isEmpty()) {
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			logger.info("uploadFile path ============== > " + path);
			String oldFileName = _packagePath.getOriginalFilename();// 原文件名
			logger.info("uploadFile oldFileName ============== > " + oldFileName);
			String suffix = FilenameUtils.getExtension(oldFileName);// 原文件后缀
			logger.debug("uploadFile prefix============> " + suffix);
			int filesize = 5000000;
			logger.debug("uploadFile size============> " + _packagePath.getSize());
			if (_packagePath.getSize() > filesize) {// 上传大小不得超过 5M
				request.setAttribute("uploadFileError", " * 上传大小不得超过 5M");
				return "false";
			} else if (suffix.equalsIgnoreCase("jpg") || suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")) {// 上传图片格式不正确
																							// 新文件名
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
				logger.debug("new fileName======== " + _packagePath.getName());
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 保存
				try {
					_packagePath.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("uploadFileError", " * 上传失败！");
					return "false";
				}
				// packagePath = path+File.separator+fileName;
				packagePath = fileName;
			} else {
				request.setAttribute("uploadFileError", " * 上传图片格式不正确");
				return "false";
			}
		} else {
			return "empty";
		}
		return packagePath;
	}

	@RequestMapping(value = "/download.html")
	@ResponseBody
	public void download(@RequestParam(value = "packagePath") String packagePath, HttpServletRequest request,
			HttpServletResponse response) {
		if (packagePath != null) {
			// 文件所在路径
			// String path=packagePath;
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles" + File.separator + packagePath);
			// 获取输入流、输出流
			BufferedInputStream bis = null;
			BufferedOutputStream out = null;
			try {
				bis = new BufferedInputStream(new FileInputStream(path));
				// 转码，免得文件名中文乱码
				packagePath = URLEncoder.encode(packagePath, "UTF-8");
				// 设置文件下载头
				response.addHeader("Content-Disposition", "attachment;filename=" + packagePath);
				// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
				response.setContentType("multipart/form-data");
				out = new BufferedOutputStream(response.getOutputStream());
				int len = 0;
				while ((len = bis.read()) != -1) {
					out.write(len);
					out.flush();
				}
				out.close();
				bis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
