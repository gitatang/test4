package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.entity.Merch;
import com.entity.PageBean;
import com.service.MerchService;
import com.util.ExcelDemo;
import com.util.Responseutil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
@RequestMapping(value="/merch")
public class MerchController {
	
	
	
	@Autowired
	private MerchService service;
	
	

	@RequestMapping("/main")
	 public String  main(
			 HttpServletRequest resquest,HttpServletResponse response,
			 @RequestParam(value="page",required=false) String page,
			 @RequestParam(value="rows",required=false) String rows){
		int page1 = Integer.parseInt(page);
		int size = Integer.parseInt(rows);
		PageBean<Merch> merchList = service.findByPage(page1,size);
 //      service.json(resquest, response,currentPage);
        int total =service.getProductsCount();
               
        Map<String ,Object> map = new HashMap<String, Object>();
        map.put("rows", merchList.getLists());
        map.put("total", total);
        
        StringBuilder json = new StringBuilder();
        json.append( "{"+ "\"total\" :"+total );
        json.append(" ,\"rows\" :"+merchList.getLists()+"}");
        System.out.println(json);
        
        try {
			Responseutil.write( response,json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "shouye";
		
	}
	
	@RequestMapping("/merchAdd")
	public String add(HttpServletRequest request,Merch merch,HttpServletResponse response) throws IOException{
		
	
		
		 String name = request.getParameter("name");
		 String cade = request.getParameter("cade");
		
		String factory = request.getParameter("factory");
		String packages = request.getParameter("packages");
		Double price =Double.valueOf(request.getParameter("price")) ;
		
		
		merch.setName(name);
		merch.setCade(cade);
		merch.setFactory(factory);
		
		merch.setPackages(packages);
		merch.setPrice(price);
	   
		System.out.println(merch.toString());
		service.add(merch);
		/*Map<String,Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("message", "添加成功！");
		Responseutil.write(response, map);*/
		return null;
		
	}
	
	@RequestMapping("/del")
	public String del(HttpServletRequest request, Model model){
		 
		 String id = request.getParameter("id");
		 
		 service.del(id);
		 
		 return "redirect:main";
		 
		 
		 
	 }
	
	@RequestMapping("/merchShow")
	public String show(HttpServletRequest request, Model model){
		String id = request.getParameter("id");
		 Merch merch = service.find(id);
		 model.addAttribute("merch", merch);
		
		return "show";
	}
	
@RequestMapping("/findAll")
public String up(Model model){
		
		List<Merch>  list = service.findAll();
		
		model.addAttribute("list", list);
		
		
		return "update";
		
	}

@RequestMapping("/manyDel")
public String manyDel(Model model){
		
		List<Merch>  list = service.findAll();
		
		model.addAttribute("list", list);
		
		
		return "manyDel";
		
	}

@RequestMapping("delmany")
public String delmany(HttpServletRequest request){
	
	String[] ids=  request.getParameterValues("id");
	System.out.println(ids.toString());
	for (String string : ids) {
		service.del(string);
	}
	
	
	
	return null;
	
	
	
}

 @RequestMapping("find")
  public String up1(HttpServletRequest request, Model model){
	String id = request.getParameter("id");
	System.out.println(id);
	 Merch merch = service.find(id);
	 model.addAttribute("merch", merch);
	
	return "upMerch";
}
	
	
	@RequestMapping("update")
	public String update(HttpServletRequest request,Merch merch,HttpServletResponse response) throws IOException{
		
		
	
		Integer id = Integer.parseInt(request.getParameter("id"));
		 String name = request.getParameter("name");
		 String cade = request.getParameter("cade");
		 
		String factory = request.getParameter("factory");
		String packages = request.getParameter("packages");
		Double price =Double.valueOf(request.getParameter("price")) ;
		
		merch.setId(id);
		merch.setName(name);
		merch.setCade(cade);
		merch.setFactory(factory);
		
		merch.setPackages(packages);
		merch.setPrice(price);
		
		service.update(merch);
		
		/*Map<String,Object> map = new HashMap<String, Object>();
		map.put("success", true);
		Responseutil.write(response, map);*/
		return null;
		
	}	
		
	
	@RequestMapping("excel")
	public String excel(HttpServletRequest request,HttpServletResponse response){
		
		service.excel(response, request);
		
		
		return "shouye";
		
		
		
	}
	
	@RequestMapping("upload")
	public String addList( @RequestParam("myfile") MultipartFile myfile, HttpServletRequest request) throws IOException{
		
		InputStream fis = myfile.getInputStream();
		
		List<Merch> merchList = ExcelDemo.importEmployeeByPoi(fis);
		
		for (Merch merch : merchList) {
			
			service.add(merch);
		}
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "shouye";
		
		
		
		
	}
	

	
}

